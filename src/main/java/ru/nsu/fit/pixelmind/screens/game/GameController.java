package ru.nsu.fit.pixelmind.screens.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.characters.character.CharacterView;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.TileType;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;

import java.util.*;

import static ru.nsu.fit.pixelmind.characters.ActionType.*;

public class GameController implements ScreenController {
    private final GameViewBuilder gameView;
    private final GameModel gameModel;
    private final SceneManager sceneManager;

    public GameController(@NotNull SceneManager sceneManager) {
        this.sceneManager = sceneManager;
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

        GameFieldController gameFieldController = new GameFieldController(gameSessionConfig.tileMap(), gameSessionConfig.tileMapSize(), tileTypeImageResources);

        CharacterController hero = new CharacterController(gameSessionConfig.heroType(), resources.sprites().get(gameSessionConfig.heroType()));
        TileIndexCoordinates heroPos = gameFieldController.getRandomFreeTile();
        assert (heroPos != null);
        gameFieldController.captureTile(heroPos);
        hero.setCurrentPosition(heroPos);
        hero.setAnimationInfoOnThisStep(heroPos, heroPos, WAIT);
        hero.setDamageValue(50);
        hero.setCurrentHealth(1000);

        List<CharacterType> enemiesTypes = gameSessionConfig.enemiesTypes();
        List<CharacterController> enemies = new ArrayList<>(enemiesTypes.size());
        for (CharacterType enemyType : enemiesTypes) {
            CharacterController enemy = new CharacterController(enemyType, resources.sprites().get(enemyType));
            TileIndexCoordinates enemyPos = gameFieldController.getRandomFreeTile();
            assert (enemyPos != null);
            gameFieldController.captureTile(enemyPos);
            enemy.setCurrentPosition(enemyPos);
            enemy.setAnimationInfoOnThisStep(enemyPos, enemyPos, WAIT);
            enemies.add(enemy);
        }
        gameModel.setGameSession(new GameSession(gameFieldController, hero, enemies));
    }

    public void handleTileClicked(TileIndexCoordinates tile) {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        if (!gameModel.gameSession().gameField().isTileAccessible(tile)) {
            System.out.println("This tile is not accessible");
            return;
        }
        if (tile.equals(gameModel.gameSession().hero().currentPosition())) {
            System.out.println("You clicked on yourself");
            return;
        }
        if (gameModel.gameSession().gameField().isThereEnemyOnThisTile(tile)) {
            System.out.println("You started hunt");
            gameModel.gameSession().hero().huntEnemy(getEnemyOnTile(tile));
            huntEnemy();
            return;
        }
        var route = buildRoute(gameModel.gameSession().hero().currentPosition(), tile, getAllCapturedTilesExcept());
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
        TileIndexCoordinates currentHuntedEnemyPosition = huntedEnemy.currentPosition();
        var route = buildRoute(hero.currentPosition(), currentHuntedEnemyPosition, getAllCapturedTilesExcept(huntedEnemy));
        if (route.isEmpty()) {
            System.out.println("Enemy cannot be accessible");
            return;
        }
        if (route.size() == 1) {
            System.out.println("You hit enemy");
            huntedEnemy.hit(hero.damageValue());
            List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(hero.currentPosition());
            Runnable callback = () -> {
            };
            if (huntedEnemy.currentHealth() == 0) {
                gameModel.gameSession().enemies().remove(huntedEnemy);
            }
            if (gameModel.gameSession().enemies().isEmpty()) {
                callback = this::playerWon;
            }
            hero.setAnimationInfoOnThisStep(
                    hero.currentPosition(),
                    currentHuntedEnemyPosition,
                    ATTACK
            );
            gameView.animateNextStep(hero.getView(), enemiesViews, callback);
            return;
        }
        TileIndexCoordinates nextTileToHuntEnemy = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentPosition(), nextTileToHuntEnemy, MOVE);
        List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(nextTileToHuntEnemy);
        gameView.animateNextStep(hero.getView(), enemiesViews, this::huntEnemy);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentPosition());
        gameModel.gameSession().gameField().captureTile(nextTileToHuntEnemy);
        gameModel.gameSession().hero().setCurrentPosition(nextTileToHuntEnemy);
    }

    private void moveHeroToNextTile() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        CharacterController hero = gameModel.gameSession().hero();
        var route = buildRoute(hero.currentPosition(), hero.targetTile(), getAllCapturedTilesExcept());
        if (route.isEmpty()) {
            System.out.println("This tile cannot be accessible");
            return;
        }
        TileIndexCoordinates nextTileInRoute = route.getFirst();
        hero.setAnimationInfoOnThisStep(hero.currentPosition(), nextTileInRoute, MOVE);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentPosition());
        gameModel.gameSession().gameField().captureTile(nextTileInRoute);
        hero.setCurrentPosition(nextTileInRoute);
        List<CharacterView> enemiesViews = doAndGatherEnemiesSteps(nextTileInRoute);
        gameView.animateNextStep(hero.getView(), enemiesViews, this::moveHeroToNextTile);
    }

    private CharacterController getEnemyOnTile(@NotNull TileIndexCoordinates tile) {
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (enemy.currentPosition().equals(tile)) {
                return enemy;
            }
        }
        return null;
    }

    @NotNull
    private List<CharacterView> doAndGatherEnemiesSteps(TileIndexCoordinates targetPosition) {
        List<CharacterView> enemiesViews = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            var route = buildRoute(enemy.currentPosition(), targetPosition, getAllCapturedTilesExcept(enemy));
            if (route.isEmpty()) {
                System.out.println("Enemy cannot accessible you");
                enemy.setAnimationInfoOnThisStep(enemy.currentPosition(), enemy.currentPosition(), WAIT);
                enemiesViews.add(enemy.getView());
                continue;
            }
            if (route.size() == 1) {
                System.out.println("Enemy hit you");
                assert (targetPosition.equals(route.getFirst()));
                enemy.setAnimationInfoOnThisStep(enemy.currentPosition(), targetPosition, ATTACK);
                gameModel.gameSession().hero().hit(enemy.damageValue());
                if (gameModel.gameSession().hero().currentHealth() == 0) {
                    finishGameSession("You lose :(", gameModel.getScore());
                }
                enemiesViews.add(enemy.getView());
                continue;
            }
            enemy.setAnimationInfoOnThisStep(enemy.currentPosition(), route.getFirst(), MOVE);
            enemiesViews.add(enemy.getView());
            gameModel.gameSession().gameField().releaseTile(enemy.currentPosition());
            gameModel.gameSession().gameField().captureTile(route.getFirst());
            enemy.setCurrentPosition(route.getFirst());
        }
        return enemiesViews;
    }

    private List<TileIndexCoordinates> getAllCapturedTilesExcept(CharacterController... availableCharacters) {
        List<TileIndexCoordinates> capturedTiles = new ArrayList<>();
        for (CharacterController enemy : gameModel.gameSession().enemies()) {
            if (!Arrays.stream(availableCharacters).toList().contains(enemy)) {
                capturedTiles.add(enemy.currentPosition());
            }
        }
        return capturedTiles;
    }

    @NotNull
    private Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        return gameModel.gameSession().gameField().buildRoute(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

//    private boolean checkAreEnemiesDead() {
//        for (CharacterController enemy : gameModel.getEnemies()) {
//            if (enemy.currentHealth() != 0) {
//                return false;
//            }
//        }
//        return true;
//    }


    private void finishGameSession(String gameResult, int gameScore) {
        sceneManager.switchToGameEndScene(gameResult, gameScore);
        gameModel.setScore(0);
    }

    private void playerWon() {
        finishGameSession("You won!", gameModel.getScore());
    }

    private void playerLose() {
        finishGameSession("You lose :(", gameModel.getScore());
    }
}
