package ru.nsu.fit.pixelmind.game;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.SceneManager;
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
    private HeroController heroController;
    private final CameraController cameraController;
    private final GameFieldController gameFieldController;
    private final GameModel gameModel;
    private final SceneManager sceneManager;

    public GameController(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        // TODO: Why does the game model depend on the hero controller?
        gameModel = new GameModel();
        gameFieldController = new GameFieldController();
        // TODO: gameModel to cameraController is a bad idea!!!
        cameraController = new CameraController(gameFieldController, gameModel, this::handleTileClicked);
        // TODO: easy GameView, but hard camera -- is a bad idea!!!
        gameView = new GameViewBuilder(gameFieldController, cameraController, gameModel);
    }

    public Region getView() {
        return gameView.build();
    }

    public void launchGameSession() {
        System.out.println("Launch game");
        gameModel.setHero(new HeroController());
        heroController = gameModel.getHeroController();
        gameFieldController.loadTileMap();

        TileIndexCoordinates heroPos = gameFieldController.getRandomFreeTile();
        gameFieldController.captureTile(heroPos);
        heroController.setCurrentPosition(heroPos);

        EnemyController enemy1 = new EnemyController(Assets.FIRE_WOMAN_SPRITES);
        EnemyController enemy2 = new EnemyController(Assets.FIRE_WOMAN_SPRITES);
        EnemyController enemy3 = new EnemyController(Assets.FIRE_WOMAN_SPRITES);
        TileIndexCoordinates enemy1Pos = gameFieldController.getRandomFreeTile();
        TileIndexCoordinates enemy2Pos = gameFieldController.getRandomFreeTile();
        TileIndexCoordinates enemy3Pos = gameFieldController.getRandomFreeTile();
        gameFieldController.captureTile(enemy1Pos);
        gameFieldController.captureTile(enemy2Pos);
        gameFieldController.captureTile(enemy3Pos);
        enemy1.setCurrentPosition(enemy1Pos);
        enemy2.setCurrentPosition(enemy2Pos);
        enemy3.setCurrentPosition(enemy3Pos);
        gameModel.getEnemies().add(enemy1);
        gameModel.getEnemies().add(enemy2);
        gameModel.getEnemies().add(enemy3);
    }

    public void handleTileClicked(TileIndexCoordinates tile) {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        if (!gameFieldController.isTileAccessible(tile)) {
            return;
        }
        if (tile.equals(heroController.currentPosition())) {
            return;
        }
        if (gameFieldController.isThereEnemyOnThisTile(tile)) {
            heroController.huntEnemy(getEnemyOnTile(tile));
            huntEnemy();
            return;
        }
        var route = buildRoute(heroController.currentPosition(), tile, getAllCapturedTilesExcept());
        if (route.isEmpty()) {
            return;
        }
        heroController.setTargetTile(tile);
        moveHeroToNextTile();
    }

    private void huntEnemy() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        EnemyController huntedEnemy = heroController.getHuntedEnemy();
        if (huntedEnemy == null) {
            return;
        }
        TileIndexCoordinates currentHuntedEnemyPosition = huntedEnemy.currentPosition();
        var route = buildRoute(heroController.currentPosition(), currentHuntedEnemyPosition, getAllCapturedTilesExcept(huntedEnemy));
        if (route.isEmpty()) {

            return;
        }
        if (route.size() == 1) {
            huntedEnemy.hit(heroController.getDamageValue());
            StepInfo heroStepInfo = new StepInfo(StepType.ATTACK, heroController.currentPosition(), currentHuntedEnemyPosition);
            var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(heroController.currentPosition());
            Runnable callback = () -> {};
            if (huntedEnemy.currentHealth() == 0) {
                gameModel.getEnemies().remove(huntedEnemy);
            }
            if (gameModel.getEnemies().isEmpty()) {
                callback = this::playerWon;
            }
            gameView.animateNextStep(heroController, heroStepInfo, enemiesStepAnimationInfo, callback);
        }
        TileIndexCoordinates nextTileToHuntEnemy = route.getFirst();
        StepInfo heroStepInfo = new StepInfo(StepType.MOVE, heroController.currentPosition(), nextTileToHuntEnemy);
        var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(nextTileToHuntEnemy);
        gameView.animateNextStep(heroController, heroStepInfo, enemiesStepAnimationInfo, this::huntEnemy);
        gameFieldController.releaseTile(heroController.currentPosition());
        gameFieldController.captureTile(nextTileToHuntEnemy);
        heroController.setCurrentPosition(nextTileToHuntEnemy);
    }

    private void moveHeroToNextTile() {
        if (gameView.isAnimatingRightNow()) {
            return;
        }
        var route = buildRoute(heroController.currentPosition(), heroController.targetTile(), getAllCapturedTilesExcept());
        if (route.isEmpty()) {
            System.out.println("Here!");
            return;
        }
        TileIndexCoordinates nextTileInRoute = route.getFirst();
        StepInfo heroStepInfo = new StepInfo(StepType.MOVE, heroController.currentPosition(), nextTileInRoute);
        gameFieldController.releaseTile(heroController.currentPosition());
        gameFieldController.captureTile(nextTileInRoute);
        heroController.setCurrentPosition(nextTileInRoute);
        var enemiesStepAnimationInfo = doAndGatherEnemiesSteps(nextTileInRoute);
        gameView.animateNextStep(heroController, heroStepInfo, enemiesStepAnimationInfo, this::moveHeroToNextTile);
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
                assert(targetPosition.equals(route.getFirst()));
                StepInfo enemyStepInfo = new StepInfo(StepType.ATTACK, enemy.currentPosition(), targetPosition);
                heroController.hit(enemy.getDamageValue());
                Runnable callback = () -> {};
                if (heroController.currentHealth() == 0) {
                    finishGameSession("You lose :(", gameModel.getScore());
                }
                enemiesAnimationInfo.put(enemy, enemyStepInfo);
                continue;
            }
            StepInfo enemyStepInfo = new StepInfo(StepType.MOVE, enemy.currentPosition(), route.getFirst());
            enemiesAnimationInfo.put(enemy, enemyStepInfo);
            gameFieldController.releaseTile(enemy.currentPosition());
            gameFieldController.captureTile(route.getFirst());
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
        var route = gameFieldController.buildRoute(exclusiveFrom, inclusiveTo, additionalBarriers);
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
