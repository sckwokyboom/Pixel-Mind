package ru.nsu.fit.pixelmind.game;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.StepInfo;
import ru.nsu.fit.pixelmind.characters.StepType;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.characters.hero.HeroController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.*;

public class GameController {
    private final GameViewBuilder gameView;
//    private HeroController gameModel.gameSession().hero();
    private final CameraController cameraController;
    //    private final GameFieldController gameModel.gameSession().gameField();
    private final GameModel gameModel;
    private final SceneManager sceneManager;

    public GameController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        gameModel = new GameModel();
        cameraController = new CameraController(gameModel, this::handleTileClicked);
        gameView = new GameViewBuilder(cameraController, gameModel);
    }

    public Region getView() {
        return gameView.build();
    }

    public void launchGameSession() {
        GameFieldController gameFieldController = new GameFieldController();
        //TODO: how to load?
        gameFieldController.loadTileMap();

        HeroController hero = new HeroController();
        TileIndexCoordinates heroPos = gameFieldController.getRandomFreeTile();
        gameFieldController.captureTile(heroPos);
        hero.setCurrentPosition(heroPos);

        List<EnemyController> enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EnemyController enemy = new EnemyController(Assets.FIRE_WOMAN_SPRITES);
            TileIndexCoordinates enemyPos = gameFieldController.getRandomFreeTile();
            gameFieldController.captureTile(enemyPos);
            enemies.add(enemy);
        }
        gameModel.setGameSession(new GameSession(gameFieldController, hero, enemies));
    }

    public void handleTileClicked(TileIndexCoordinates tile) {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        if (!gameModel.gameSession().gameField().isTileAccessible(tile)) {
            return;
        }
        if (tile.equals(gameModel.gameSession().hero().currentPosition())) {
            return;
        }
        if (gameModel.gameSession().gameField().isThereEnemyOnThisTile(tile)) {
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
        EnemyController huntedEnemy = gameModel.gameSession().hero().getHuntedEnemy();
        if (huntedEnemy == null) {
            return;
        }
        TileIndexCoordinates currentHuntedEnemyPosition = huntedEnemy.currentPosition();
        var route = buildRoute(gameModel.gameSession().hero().currentPosition(), currentHuntedEnemyPosition, getAllCapturedTilesExcept(huntedEnemy));
        if (route.isEmpty()) {

            return;
        }
        if (route.size() == 1) {
            huntedEnemy.hit(gameModel.gameSession().hero().getDamageValue());
            StepInfo heroStepInfo = new StepInfo(StepType.ATTACK, gameModel.gameSession().hero().currentPosition(), currentHuntedEnemyPosition);
            var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(gameModel.gameSession().hero().currentPosition());
            Runnable callback = () -> {
            };
            if (huntedEnemy.currentHealth() == 0) {
                gameModel.getEnemies().remove(huntedEnemy);
            }
            if (gameModel.getEnemies().isEmpty()) {
                callback = this::playerWon;
            }
            gameView.animateNextStep(gameModel.gameSession().hero(), heroStepInfo, enemiesStepAnimationInfo, callback);
        }
        TileIndexCoordinates nextTileToHuntEnemy = route.getFirst();
        StepInfo heroStepInfo = new StepInfo(StepType.MOVE, gameModel.gameSession().hero().currentPosition(), nextTileToHuntEnemy);
        var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(nextTileToHuntEnemy);
        gameView.animateNextStep(gameModel.gameSession().hero(), heroStepInfo, enemiesStepAnimationInfo, this::huntEnemy);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentPosition());
        gameModel.gameSession().gameField().captureTile(nextTileToHuntEnemy);
        gameModel.gameSession().hero().setCurrentPosition(nextTileToHuntEnemy);
    }

    private void moveHeroToNextTile() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        var route = buildRoute(gameModel.gameSession().hero().currentPosition(), gameModel.gameSession().hero().targetTile(), getAllCapturedTilesExcept());
        if (route.isEmpty()) {
            System.out.println("Here!");
            return;
        }
        TileIndexCoordinates nextTileInRoute = route.getFirst();
        StepInfo heroStepInfo = new StepInfo(StepType.MOVE, gameModel.gameSession().hero().currentPosition(), nextTileInRoute);
        gameModel.gameSession().gameField().releaseTile(gameModel.gameSession().hero().currentPosition());
        gameModel.gameSession().gameField().captureTile(nextTileInRoute);
        gameModel.gameSession().hero().setCurrentPosition(nextTileInRoute);
        var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(nextTileInRoute);
        gameView.animateNextStep(gameModel.gameSession().hero(), heroStepInfo, enemiesStepAnimationInfo, this::moveHeroToNextTile);
    }

    private EnemyController getEnemyOnTile(TileIndexCoordinates tile) {
        for (EnemyController enemy : gameModel.getEnemies()) {
            if (enemy.currentPosition().equals(tile)) {
                return enemy;
            }
        }
        return null;
    }

    private HashMap<EnemyController, StepInfo> doAndGatherEnemiesSteps(TileIndexCoordinates targetPosition) {
        HashMap<EnemyController, StepInfo> enemiesAnimationInfo = new HashMap<>();
        for (EnemyController enemy : gameModel.getEnemies()) {
            var route = buildRoute(enemy.currentPosition(), targetPosition, getAllCapturedTilesExcept(enemy));
            if (route.isEmpty()) {
                continue;
            }
            if (route.size() == 1) {
                assert (targetPosition.equals(route.getFirst()));
                StepInfo enemyStepInfo = new StepInfo(StepType.ATTACK, enemy.currentPosition(), targetPosition);
                gameModel.gameSession().hero().hit(enemy.getDamageValue());
                Runnable callback = () -> {
                };
                if (gameModel.gameSession().hero().currentHealth() == 0) {
                    finishGameSession("You lose :(", gameModel.getScore());
                }
                enemiesAnimationInfo.put(enemy, enemyStepInfo);
                continue;
            }
            StepInfo enemyStepInfo = new StepInfo(StepType.MOVE, enemy.currentPosition(), route.getFirst());
            enemiesAnimationInfo.put(enemy, enemyStepInfo);
            gameModel.gameSession().gameField().releaseTile(enemy.currentPosition());
            gameModel.gameSession().gameField().captureTile(route.getFirst());
            enemy.setCurrentPosition(route.getFirst());
        }
        return enemiesAnimationInfo;
    }

    private List<TileIndexCoordinates> getAllCapturedTilesExcept(CharacterController... availableCharacters) {
        List<TileIndexCoordinates> capturedTiles = new ArrayList<>();
        for (EnemyController enemy : gameModel.getEnemies()) {
            if (!Arrays.stream(availableCharacters).toList().contains(enemy)) {
                capturedTiles.add(enemy.currentPosition());
            }
        }
        return capturedTiles;
    }

    private Deque<TileIndexCoordinates> buildRoute(TileIndexCoordinates exclusiveFrom, TileIndexCoordinates inclusiveTo, List<TileIndexCoordinates> additionalBarriers) {
        var route = gameModel.gameSession().gameField().buildRoute(exclusiveFrom, inclusiveTo, additionalBarriers);
        if (route != null) {
            return route;
        }
        return new ArrayDeque<>();
    }

//    private boolean checkAreEnemiesDead() {
//        for (EnemyController enemy : gameModel.getEnemies()) {
//            if (enemy.currentHealth() != 0) {
//                return false;
//            }
//        }
//        return true;
//    }


    private void finishGameSession(String gameResult, int gameScore) {
        sceneManager.switchToGameEndScene(gameResult, gameScore);
        gameModel.setScore(0);
        gameModel.getEnemies().clear();
        gameModel.setHero(null);
    }

    private void playerWon() {
        finishGameSession("You won!", gameModel.getScore());
    }

    private void playerLose() {
        finishGameSession("You lose :(", gameModel.getScore());
    }
}
