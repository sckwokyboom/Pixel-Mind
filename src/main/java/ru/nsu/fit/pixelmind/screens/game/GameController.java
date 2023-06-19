package ru.nsu.fit.pixelmind.screens.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.characters.character.CharacterView;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.game_field.tile_map.TileMapController;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;

import java.util.*;

import static ru.nsu.fit.pixelmind.characters.ActionType.*;

public class GameController implements ScreenController {
    private final GameViewBuilder gameView;
    private final GameModel gameModel;
    private final SceneManager.GameEndSceneHandler gameEndSceneHandler;

    public GameController(SceneManager.GameEndSceneHandler gameEndSceneHandler) {
        this.gameEndSceneHandler = gameEndSceneHandler;
        gameModel = new GameModel();
        CameraController cameraController = new CameraController(gameModel, this::handleTileClicked);
        gameView = new GameViewBuilder(cameraController, gameModel);
    }

    @Override
    public Region getView() {
        return gameView.build();
    }

    public void launchGameSession(@NotNull Resources resources, @NotNull GameSessionConfig gameSessionConfig) {
        System.out.println("Launch game session with");
        System.out.println("Resources: " + resources);
        System.out.println("Hero type: " + gameSessionConfig.heroType());
        System.out.println("Enemies: " + gameSessionConfig.enemiesTypes());
        System.out.println("Tile map size: " + gameSessionConfig.tileMapSize());
        Map<TileType, Image> tileTypeImageResources = resources.tileSets().get(gameSessionConfig.tileSetType());
        System.out.println("TileMapType" + resources.tileSets().get(TileSetType.REGULAR));

        TileMapController tileMapController = new TileMapController(gameSessionConfig.tileMap(), gameSessionConfig.tileMapSize(), tileTypeImageResources);

        CharacterController hero = new CharacterController(gameSessionConfig.heroType(), resources.sprites().get(gameSessionConfig.heroType()));
        TileIndexCoordinates heroPos = tileMapController.getRandomFreeTile();
        assert (heroPos != null);
        tileMapController.captureTile(heroPos);
        hero.setCurrentPosition(heroPos);
        hero.setAnimationInfoOnThisStep(heroPos, heroPos, WAIT);
        hero.setDamageValue(50);
        hero.setCurrentHealth(1000);

        List<CharacterType> enemiesTypes = gameSessionConfig.enemiesTypes();
        List<CharacterController> enemies = new ArrayList<>(enemiesTypes.size());
        for (CharacterType enemyType : enemiesTypes) {
            CharacterController enemy = new CharacterController(enemyType, resources.sprites().get(enemyType));
            TileIndexCoordinates enemyPos = tileMapController.getRandomFreeTile();
            assert (enemyPos != null);
            tileMapController.captureTile(enemyPos);
            enemy.setCurrentPosition(enemyPos);
            enemy.setAnimationInfoOnThisStep(enemyPos, enemyPos, WAIT);
            enemies.add(enemy);
        }
        gameModel.setGameSession(new GameSession(tileMapController, hero, enemies));
    }

    public void handleTileClicked(TileIndexCoordinates tile) {
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
        if (gameModel.gameSession().gameField().isThereEnemyOnThisTile(tile)) {
            System.out.println("You started hunt");
            gameModel.gameSession().hero().huntEnemy(getEnemyOnTile(tile));
            huntEnemy();
            return;
        }
        var route = buildRoute(gameModel.gameSession().hero().currentTile(), tile, getAllCapturedTilesExcept());
        if (route.isEmpty()) {
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
        CharacterController huntedEnemy = hero.getHuntedEnemy();
        if (huntedEnemy == null) {
            return;
        }
        TileIndexCoordinates currentHuntedEnemyPosition = huntedEnemy.currentTile();
        var route = buildRoute(hero.currentTile(), currentHuntedEnemyPosition, getAllCapturedTilesExcept(huntedEnemy));
        if (route.isEmpty()) {
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
            List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(hero.currentTile());
            gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, callback);
            return;
        }
        TileIndexCoordinates nextTileToHuntEnemy = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentTile(), nextTileToHuntEnemy, MOVE);
        List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(nextTileToHuntEnemy);
        gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, this::huntEnemy);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentTile());
        gameModel.gameSession().gameField().captureTile(nextTileToHuntEnemy);
        gameModel.gameSession().hero().setCurrentPosition(nextTileToHuntEnemy);
    }

    private void moveHeroToNextTile() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        CharacterController hero = gameModel.gameSession().hero();
        var route = buildRoute(hero.currentTile(), hero.targetTile(), getAllCapturedTilesExcept());
        if (route.isEmpty()) {
            return;
        }
        TileIndexCoordinates nextTileInRoute = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentTile(), nextTileInRoute, MOVE);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentTile());
        gameModel.gameSession().gameField().captureTile(nextTileInRoute);
        hero.setCurrentPosition(nextTileInRoute);
        List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(nextTileInRoute);
        gameView.animateHeroAndEnemiesSteps(hero.getView(), enemiesViews, this::moveHeroToNextTile);
    }

    private CharacterController getEnemyOnTile(@NotNull TileIndexCoordinates tile) {
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (enemy.currentTile().equals(tile)) {
                return enemy;
            }
        }
        return null;
    }

    @NotNull
    private List<CharacterView> doAndGatherEnemiesSteps(TileIndexCoordinates targetPosition) {
        List<CharacterView> enemiesViews = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            var route = buildRoute(enemy.currentTile(), targetPosition, getAllCapturedTilesExcept(enemy));
            if (route.isEmpty()) {
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

    private List<TileIndexCoordinates> getAllCapturedTilesExcept(CharacterController... availableCharacters) {
        List<TileIndexCoordinates> capturedTiles = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (!Arrays.stream(availableCharacters).toList().contains(enemy)) {
                capturedTiles.add(enemy.currentTile());
            }
        }
        return capturedTiles;
    }

    @NotNull
    private Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        return gameModel.gameSession().gameField().buildRoute(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

    private void finishGameSession(String gameResult, int gameScore) {
        gameEndSceneHandler.switchToGameEndScene(gameResult, gameScore);
        gameModel.setScore(0);
    }

    private void playerWon() {
        finishGameSession("You won!", gameModel.getScore());
    }

    private void playerLose() {
        finishGameSession("You lose :(", gameModel.getScore());
    }
}
