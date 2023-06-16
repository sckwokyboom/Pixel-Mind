package ru.nsu.fit.pixelmind.game;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.SceneManager;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.characters.hero.HeroController;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GameController {
    private final Builder<Region> viewBuilder;
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
        cameraController = new CameraController(gameFieldController, gameModel);
        // TODO: easy GameView, but hard camera -- is a bad idea!!!
        viewBuilder = new GameViewBuilder(gameFieldController, cameraController, gameModel);
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public void launchGameSession() {
        System.out.println("Launch game");
        gameModel.setHero(new HeroController());
        heroController = gameModel.getHeroController();
        gameFieldController.loadTileMap();
        heroController.setHeroToPos(gameFieldController.getRandomFreeTile());
        gameModel.getEnemies().add(new EnemyController(Assets.FIRE_WOMAN_SPRITES, gameFieldController.getRandomFreeTile()));
        gameModel.getEnemies().add(new EnemyController(Assets.FIRE_WOMAN_SPRITES, gameFieldController.getRandomFreeTile()));
        gameModel.getEnemies().add(new EnemyController(Assets.FIRE_WOMAN_SPRITES, gameFieldController.getRandomFreeTile()));
        gameModel.enemiesCountProperty().set(gameModel.getEnemies().size());
        heroController.getCoordinateChanged().addListener((observable, oldValue, newValue) -> {
            if (newValue && cameraController.isAnimationEndProperty().get()) {
                System.out.println("New hero target: " + heroController.getTargetTileIndexXProperty().get() + " " + heroController.getTargetTileIndexYProperty().get());
                moveHeroToTarget();
            }
            heroController.getCoordinateChanged().set(false);
        });
        gameModel.enemiesCountProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Game over!");
            if (newValue.equals(0) && cameraController.isAnimationEndProperty().get()) {
                sceneManager.switchToGameEndScene("You win!", gameModel.getScore());
                finishGameSession();
            }
        });
        heroController.getCurrentHealth().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(0) && cameraController.isAnimationEndProperty().get()) {
                sceneManager.switchToGameEndScene("You lose :(", gameModel.getScore());
                finishGameSession();
            }
        });
    }

    public void moveHeroToTarget() {
        heroController.getRoute().clear();
        Pair<Integer, Integer> start = new Pair<>(heroController.getTileIndexXProperty().get(), heroController.getTileIndexYProperty().get());
        Pair<Integer, Integer> target = new Pair<>(heroController.getTargetTileIndexXProperty().get(), heroController.getTargetTileIndexYProperty().get());
        EnemyController huntedEnemy = heroController.getHuntedEnemy();
        if (huntedEnemy != null) {
            Pair<Integer, Integer> targetEnemy = new Pair<>(huntedEnemy.getTileIndexXProperty().get(), huntedEnemy.getTileIndexYProperty().get());
            heroController.getRoute().addAll(buildRoute(start, targetEnemy, findAllCurrentPositionsOfEnemiesExcept(targetEnemy)));
            if (heroController.getRoute().isEmpty()) {
                return;
            }
            heroController.getRoute().removeFirst();
            if (heroController.getRoute().size() == 1) {
                cameraController.animateHeroAttack();
                heroController.getRoute().clear();
                updateEnemiesRoutes();
                heroController.huntEnemy(null);
                return;
            }
            updateEnemiesRoutes();
            cameraController.animateNextStep(heroController.getRoute().getFirst(), this::moveHeroToTarget);
            heroController.getRoute().removeFirst();
            return;
        }


        if (gameFieldController.isThereEnemyOnThisTile(target)) {
            System.out.println("Enemy hunt!");
            EnemyController newHuntedEnemy = getEnemyOnThisTile(target);
            heroController.huntEnemy(newHuntedEnemy);
            heroController.getRoute().addAll(buildRoute(start, target, findAllCurrentPositionsOfEnemiesExcept(target)));

            if (heroController.getRoute().isEmpty()) {
                return;
            }
            heroController.getRoute().removeFirst();
            if (heroController.getRoute().size() == 1) {
                cameraController.animateHeroAttack();
                newHuntedEnemy.hit(heroController.getDamageValue());
                gameModel.scoreProperty().set(gameModel.getScore() + heroController.getDamageValue());
                System.out.println("Score: " + gameModel.scoreProperty().get());
                System.out.println("Hit enemy: " + "-" + heroController.getDamageValue() + ". Current health: " + newHuntedEnemy.getCurrentHealth());
                if (newHuntedEnemy.getCurrentHealth().get() == 0) {
                    gameModel.enemiesCountProperty().set(gameModel.getEnemies().size() - 1);
                    System.out.println(gameModel.enemiesCountProperty().get());
                    Pair<Integer, Integer> newHuntedEnemyTile = new Pair<>(newHuntedEnemy.getTargetTileIndexXProperty().get(), newHuntedEnemy.getTargetTileIndexYProperty().get());
                    gameFieldController.releaseTile(newHuntedEnemyTile);
                    gameModel.getEnemies().remove(newHuntedEnemy);
                    heroController.huntEnemy(null);
                }
                heroController.getRoute().clear();
                updateEnemiesRoutes();
                return;
            }
            updateEnemiesRoutes();
            cameraController.animateNextStep(heroController.getRoute().getFirst(), this::moveHeroToTarget);
            heroController.getRoute().removeFirst();
            return;
        }


        if (heroController.getHuntedEnemy() == null) {
            heroController.getRoute().addAll(buildRoute(start, target, findAllCurrentPositionsOfEnemiesExcept(null)));
            heroController.getRoute().removeFirst();
            System.out.println("Successfully build hero route " + heroController.getRoute().toString());
            if (!heroController.getRoute().isEmpty()) {
                updateEnemiesRoutes();
                cameraController.animateNextStep(heroController.getRoute().getFirst(), this::moveHeroToTarget);
                heroController.getRoute().removeFirst();
            }
        }
    }

    private void updateEnemiesRoutes() {
        for (EnemyController enemy : gameModel.getEnemies()) {
            Pair<Integer, Integer> startEnemyTile = new Pair<>(enemy.getTileIndexXProperty().get(), enemy.getTileIndexYProperty().get());
            Pair<Integer, Integer> endEnemyTile;
            if (heroController.getRoute().isEmpty()) {
                endEnemyTile = new Pair<>(heroController.getTileIndexXProperty().get(), heroController.getTileIndexYProperty().get());
            } else {
                endEnemyTile = new Pair<>(heroController.getRoute().getFirst().getKey(), heroController.getRoute().getFirst().getValue());
            }
            var badPositions = findAllCurrentPositionsOfEnemiesExcept(null);
            var enemyRoute = buildRoute(startEnemyTile, endEnemyTile, badPositions);
            if (enemyRoute.size() >= 2) {
                enemyRoute.removeFirst();
//                enemyRoute.removeLast();
                System.out.println("Enemy route after remove: " + enemyRoute);
                if (enemyRoute.isEmpty()) {
                    return;
                }
                if (enemyRoute.size() == 1) {
                    heroController.hit(enemy.getDamageValue());
                    if (heroController.getCurrentHealth().get() == 0) {
                        sceneManager.switchToGameEndScene("You lose :(", gameModel.getScore());
                        finishGameSession();
                        return;
                    }
                    System.out.println("Hit by enemy with damage: " + enemy.getDamageValue() + ". Current health: " + heroController.getCurrentHealth().get());
                }
                enemyRoute.removeLast();
                if (!enemyRoute.isEmpty()) {
                    enemy.getTargetTileIndexXProperty().set(enemyRoute.getFirst().getKey());
                    enemy.getTargetTileIndexYProperty().set(enemyRoute.getFirst().getValue());
                    System.out.println("Enemy target during route building: " + enemy.getTargetTileIndexXProperty().get() + " " + enemy.getTargetTileIndexYProperty().get());
                    System.out.println("Enemy route: " + enemyRoute);
                    enemyRoute.removeFirst();
                }
            }
        }
        cameraController.animateEnemiesAttack();
    }

//    public void enemiesAttack() {
//        for (EnemyController enemy : gameModel.getEnemies()) {
//            Pair<Integer, Integer> startEnemyTile = new Pair<>(enemy.getTileIndexXProperty().get(), enemy.getTileIndexYProperty().get());
//            Pair<Integer, Integer> endEnemyTile;
//            if (heroController.getRoute().isEmpty()) {
//                endEnemyTile = new Pair<>(heroController.getTileIndexXProperty().get(), heroController.getTileIndexYProperty().get());
//            } else {
//                endEnemyTile = new Pair<>(heroController.getRoute().getFirst().getKey(), heroController.getRoute().getFirst().getValue());
//            }
//            var badPositions = findAllCurrentPositionsOfEnemiesExcept(null);
//            var enemyRoute = buildRoute(startEnemyTile, endEnemyTile, badPositions);
//            if (enemyRoute.size() >= 2) {
//                enemyRoute.removeFirst();
//                enemyRoute.removeLast();
//                System.out.println("Enemy route after remove: " + enemyRoute);
//                if (enemyRoute.isEmpty()) {
//                    return;
//                }
//                if (enemyRoute.size() == 1) {
//
//                }
//                if (!enemyRoute.isEmpty()) {
//                    enemy.getTargetTileIndexXProperty().set(enemyRoute.getFirst().getKey());
//                    enemy.getTargetTileIndexYProperty().set(enemyRoute.getFirst().getValue());
//                    System.out.println("Enemy target during route building: " + enemy.getTargetTileIndexXProperty().get() + " " + enemy.getTargetTileIndexYProperty().get());
//                    System.out.println("Enemy route: " + enemyRoute);
//                    enemyRoute.removeFirst();
//                }
//            }
//        }
//    }

    private Deque<Pair<Integer, Integer>> buildRoute(Pair<Integer, Integer> start, Pair<Integer, Integer> end, List<Pair<Integer, Integer>> additionalBarriers) {
        var route = gameFieldController.buildRoute(start, end, additionalBarriers);
        if (route != null) {
            return route;
        }
        return new ArrayDeque<>();
    }

    private List<Pair<Integer, Integer>> findAllCurrentPositionsOfEnemiesExcept(Pair<Integer, Integer> availableTarget) {
        ArrayList<Pair<Integer, Integer>> positions = new ArrayList<>();
        for (EnemyController enemy : gameModel.getEnemies()) {
            Pair<Integer, Integer> currentEnemyTile = new Pair<>(enemy.getTileIndexXProperty().get(), enemy.getTileIndexYProperty().get());
            Pair<Integer, Integer> targetEnemyTile = new Pair<>(enemy.getTargetTileIndexXProperty().get(), enemy.getTargetTileIndexYProperty().get());
            if (!currentEnemyTile.equals(availableTarget) && !targetEnemyTile.equals(availableTarget)) {
                positions.add(currentEnemyTile);
                positions.add(targetEnemyTile);
            }
        }
//        System.out.println("Available positions: " + positions);
        return positions;
    }

    private EnemyController getEnemyOnThisTile(Pair<Integer, Integer> targetTile) {
        for (EnemyController enemy : gameModel.getEnemies()) {
            Pair<Integer, Integer> currentEnemyTile = new Pair<>(enemy.getTileIndexXProperty().get(), enemy.getTileIndexYProperty().get());
            if (currentEnemyTile.equals(targetTile)) {
                return enemy;
            }
        }
        return null;
    }

    private void finishGameSession() {
        gameModel.scoreProperty().set(0);
        gameModel.getEnemies().clear();
        gameModel.setHero(null);
    }
}
