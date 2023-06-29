package ru.nsu.fit.pixelmind.screens.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.game.camera.CameraController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterView;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;
import ru.nsu.fit.pixelmind.screens.new_game_screen.UserModifications;

import java.util.*;
import java.util.function.BiFunction;

import static ru.nsu.fit.pixelmind.Constants.GAME_LOSS_MESSAGE;
import static ru.nsu.fit.pixelmind.Constants.GAME_VICTORY_MESSAGE;
import static ru.nsu.fit.pixelmind.screens.game.character.ActionType.*;

public class GameController implements ScreenController {
    @NotNull
    private final GameView gameView;
    @NotNull
    private final GameModel gameModel;
    @NotNull
    private final MainController.GameEndSceneHandler gameEndSceneHandler;
    @NotNull
    private final GameInteractor gameInteractor;

    GameController(@NotNull MainController.GameEndSceneHandler gameEndSceneHandler,
                   @NotNull BiFunction<CameraController, GameModel, GameView> viewCreator) {
        this.gameEndSceneHandler = gameEndSceneHandler;
        gameModel = new GameModel();
        CameraController cameraController = new CameraController(gameModel, this::handleTileClicked);
        this.gameView = viewCreator.apply(cameraController, gameModel);
        gameInteractor = new GameInteractor(gameModel);
    }

    public GameController(@NotNull MainController.GameEndSceneHandler gameEndSceneHandler) {
        this(gameEndSceneHandler, GameView::new);
    }

    @Override
    @NotNull
    public Region getView() {
        return gameView.build();
    }

    public void createGameSession(@NotNull UserModifications userModifications, @NotNull GameSessionConfig gameSessionConfig) {
        System.out.println("Create game session");
        Map<TileType, Image> tileTypeImageResources = gameView.resources().tileSets().get(gameSessionConfig.tileSetType());
        TileMapController tileMapController = new TileMapController(gameSessionConfig.tileMap(), gameSessionConfig.tileMapSize());
        tileMapController.setResources(tileTypeImageResources);
        CharacterController hero = new CharacterController(userModifications.heroType());
        hero.setResources(gameView.resources().sprites().get(userModifications.heroType()));

        TileIndexCoordinates heroPos = tileMapController.getRandomFreeTile();
        assert (heroPos != null);
        tileMapController.captureTile(heroPos);
        hero.setCurrentPosition(heroPos);
        hero.setAnimationInfoOnThisStep(heroPos, heroPos, WAIT);
        hero.setDamageValue(50);
        hero.setCurrentHealth(200);

        List<CharacterType> enemiesTypes = gameSessionConfig.enemiesTypes();
        List<CharacterController> enemies = new ArrayList<>(enemiesTypes.size());
        for (CharacterType enemyType : enemiesTypes) {
            CharacterController enemy = new CharacterController(enemyType);
            enemy.setResources(gameView.resources().sprites().get(enemyType));
            TileIndexCoordinates enemyPos = tileMapController.getRandomFreeTile();
            assert (enemyPos != null);
            tileMapController.captureTile(enemyPos);
            enemy.setCurrentPosition(enemyPos);
            enemy.setAnimationInfoOnThisStep(enemyPos, enemyPos, WAIT);
            enemies.add(enemy);
        }
        gameModel.setGameSession(new GameSession(tileMapController, hero, enemies));
    }

    public void launchGameSession(@NotNull GameSession gameSession) {
        TileIndexCoordinates heroPos = gameSession.hero().currentTile();
        gameSession.gameField().captureTile(heroPos);
        gameSession.hero().setAnimationInfoOnThisStep(heroPos, heroPos, WAIT);
        for (CharacterController enemy : gameSession.enemies()) {
            TileIndexCoordinates enemyPos = enemy.currentTile();
            gameSession.gameField().captureTile(enemyPos);
            enemy.setAnimationInfoOnThisStep(enemyPos, enemyPos, WAIT);
        }
        gameModel.setGameSession(gameSession);
    }

    public void saveGameSession() {
        gameInteractor.saveCurrentGameSession();
    }

    public GameSession getGameSession() {
        return gameModel.gameSession();
    }

    public void clearGameSession() {
        gameModel.setGameSession(null);
    }

    public void setResources(@NotNull Resources resources) {
        gameView.setResources(resources);
    }

    public void dumpNewSaves() {
        gameInteractor.dumpNewSaves();
    }

    public void handleTileClicked(@NotNull TileIndexCoordinates tile) {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        if (!gameModel.gameSession().gameField().isTileAccessible(tile)) {
            System.out.println("This tile is not accessible");
            return;
        }
        if (tile.equals(gameModel.gameSession().hero().currentTile())) {
            System.out.println("You clicked on yourself");
            return;
        }
        if (gameModel.gameSession().gameField().isThereSomebodyOnThisTile(tile)) {
            System.out.println("You started hunt");
            gameModel.gameSession().hero().huntEnemy(getEnemyOnTile(tile));
            huntEnemy();
            return;
        }
        var route = buildRoute(gameModel.gameSession().hero().currentTile(), tile, getAllCapturedTilesExcept());
        if (route == null || route.isEmpty()) {
            return;
        }
        gameModel.gameSession().hero().setTargetTile(tile);
        moveHeroToNextTile();
    }

    private void huntEnemy() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        CharacterController hero = gameModel.gameSession().hero();
        CharacterController huntedEnemy = hero.huntingTarget();
        if (huntedEnemy == null) {
            return;
        }
        TileIndexCoordinates currentHuntedEnemyPosition = huntedEnemy.currentTile();
        var route = buildRoute(hero.currentTile(), currentHuntedEnemyPosition, getAllCapturedTilesExcept(huntedEnemy));
        if (route == null || route.isEmpty()) {
            System.out.println("Enemy cannot be accessible");
            return;
        }
        if (route.size() == 1) {
            System.out.println("You hit enemy");
            huntedEnemy.hit(hero.damageValue());
            gameModel.setScore(gameModel.getScore() + hero.damageValue());
            Runnable callback = () -> {
            };
            if (huntedEnemy.currentHealth() == 0) {
                gameModel.gameSession().gameField().releaseTile(huntedEnemy.currentTile());
                gameModel.gameSession().enemies().remove(huntedEnemy);
            }
            if (gameModel.gameSession().enemies().isEmpty()) {
                callback = this::playerWon;
            }
            hero.setAnimationInfoOnThisStep(
                    hero.currentTile(),
                    currentHuntedEnemyPosition,
                    ATTACK
            );
            List<CharacterView> enemiesViews = buildEnemiesSteps(hero.currentTile());

            gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, callback);
            return;
        }
        TileIndexCoordinates nextTileToHuntEnemy = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentTile(), nextTileToHuntEnemy, MOVE);
        List<CharacterView> enemiesViews = buildEnemiesSteps(nextTileToHuntEnemy);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentTile());
        gameModel.gameSession().gameField().captureTile(nextTileToHuntEnemy);
        gameModel.gameSession().hero().setCurrentPosition(nextTileToHuntEnemy);

        gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, this::huntEnemy);
    }

    private void moveHeroToNextTile() {
        // CR: separate recursion and action
        // TODO: Hard...it seems to be pointless. There is recursion calls only in the last line.
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        CharacterController hero = gameModel.gameSession().hero();
        var route = buildRoute(hero.currentTile(), hero.targetTile(), getAllCapturedTilesExcept());
        if (route == null || route.isEmpty()) {
            return;
        }
        TileIndexCoordinates nextTileInRoute = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentTile(), nextTileInRoute, MOVE);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentTile());
        gameModel.gameSession().gameField().captureTile(nextTileInRoute);
        hero.setCurrentPosition(nextTileInRoute);
        List<CharacterView> enemiesViews = buildEnemiesSteps(nextTileInRoute);

        gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, this::moveHeroToNextTile);
    }

    @Nullable
    private CharacterController getEnemyOnTile(@NotNull TileIndexCoordinates tile) {
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (enemy.currentTile().equals(tile)) {
                return enemy;
            }
        }
        return null;
    }

    @NotNull
    private List<CharacterView> buildEnemiesSteps(@NotNull TileIndexCoordinates targetPosition) {
        List<CharacterView> enemiesViews = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            var route = buildRoute(enemy.currentTile(), targetPosition, getAllCapturedTilesExcept(enemy));
            if (route == null || route.isEmpty()) {
                System.out.println("Enemy cannot accessible you");
                enemy.setAnimationInfoOnThisStep(enemy.currentTile(), enemy.currentTile(), WAIT);
                enemiesViews.add(enemy.getView());
                continue;
            }
            if (route.size() == 1) {
                System.out.println("Enemy hit you");
                assert (targetPosition.equals(route.getFirst()));
                enemy.setAnimationInfoOnThisStep(enemy.currentTile(), targetPosition, ATTACK);
                gameModel.gameSession().hero().hit(enemy.damageValue());
                if (gameModel.gameSession().hero().currentHealth() == 0) {
                    playerLose();
                }
                enemiesViews.add(enemy.getView());
                continue;
            }
            enemy.setAnimationInfoOnThisStep(enemy.currentTile(), route.getFirst(), MOVE);
            enemiesViews.add(enemy.getView());
            gameModel.gameSession().gameField().releaseTile(enemy.currentTile());
            gameModel.gameSession().gameField().captureTile(route.getFirst());
            enemy.setCurrentPosition(route.getFirst());
        }
        return enemiesViews;
    }

    @NotNull
    private List<TileIndexCoordinates> getAllCapturedTilesExcept(@NotNull CharacterController... availableCharacters) {
        List<TileIndexCoordinates> capturedTiles = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (!Arrays.stream(availableCharacters).toList().contains(enemy)) {
                capturedTiles.add(enemy.currentTile());
            }
        }
        return capturedTiles;
    }

    @Nullable
    private Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        return gameModel.gameSession().gameField().buildRoute(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

    private void finishGameSession(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
        gameEndSceneHandler.switchToGameEndScene(gameResult, heroType, gameScore);
        gameModel.setScore(0);
    }

    private void playerWon() {
        finishGameSession(GAME_VICTORY_MESSAGE, gameModel.gameSession().hero().characterType(), gameModel.getScore());
    }

    private void playerLose() {
        finishGameSession(GAME_LOSS_MESSAGE, gameModel.gameSession().hero().characterType(), gameModel.getScore());
    }
}
