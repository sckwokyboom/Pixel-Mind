package ru.nsu.fit.pixelmind.camera;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Affine;
import javafx.util.Builder;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.game.GameModel;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.characters.hero.HeroController;
import ru.nsu.fit.pixelmind.characters.SpriteType;

import java.util.List;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class CameraViewBuilder implements Builder<Region> {
    private Canvas gameField;
    private final GameFieldController gameFieldController;
    private final GameModel gameModel;
    private HeroController heroController;
    private final List<EnemyController> enemies;
    private final CameraModel cameraModel;
    private AnimationTimer animation;
    private double scale = 1.0;
    private double centerX;
    private double centerY;
    private double topLeftCornerOffsetX;
    private double topLeftCornerOffsetY;


    CameraViewBuilder(GameFieldController gameFieldController, GameModel gameModel, CameraModel cameraModel) {
        this.gameFieldController = gameFieldController;
        this.cameraModel = cameraModel;
        this.enemies = gameModel.getEnemies();
        this.gameModel = gameModel;
    }

    @Override
    public Region build() {
        System.out.println("Build camera");
        this.heroController = gameModel.getHeroController();
        gameField = gameFieldController.getView();
        Pane gamePane = new Pane();
        gameField.layoutXProperty().bind(gamePane.widthProperty().subtract(gameField.widthProperty()).divide(2));
        gameField.layoutYProperty().bind(gamePane.heightProperty().subtract(gameField.heightProperty()).divide(2));
        redrawHero();
        redrawAllEnemies();
        gameField.setOnMouseReleased(event -> {
            if (event.isStillSincePress()) {
                int curX = (int) ((event.getX() + gamePane.getTranslateX()) / scale);
                int curY = (int) ((event.getY() + gamePane.getTranslateY()) / scale);
                int tileCurX = curX / TILE_SIZE;
                int tileCurY = curY / TILE_SIZE;
                if (gameFieldController.isTileAccessible(tileCurX, tileCurY)) {
                    System.out.println("User clicked tile: " + tileCurX + " " + tileCurY + ". Is accessible: " + gameFieldController.isTileAccessible(tileCurX, tileCurY));
                    heroController.getTargetTileIndexXProperty().set(tileCurX);
                    heroController.getTargetTileIndexYProperty().set(tileCurY);
                    heroController.getCoordinateChanged().set(true);
                }
                gameFieldController.redrawTileMap();
            }
        });
        gamePane.getChildren().add(gameField);
        return gamePane;
    }

    public void animateEnemiesAttack() {
        animation = new AnimationTimer() {
            final double duration = 500;
            final double fps = 20;
            final int numFrames = (int) Math.ceil((duration / 1000) * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    for (EnemyController enemy : enemies) {
                    }
                    cameraModel.isAnimationEndProperty().set(true);
                    return;
                }
                cameraModel.isAnimationEndProperty().set(false);
                for (EnemyController enemy : enemies) {
                }
                frameCount++;
            }
        };
        animation.start();
    }

    public void animateHeroAttack() {
        animation = new AnimationTimer() {
            final double duration = 500;
            final double fps = 20;
            final int numFrames = (int) Math.ceil((duration / 1000) * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    heroController.chooseSprite(SpriteType.REGULAR_SPRITE);
                    updateWorld(() -> {
                    });
                    return;
                }
                cameraModel.isAnimationEndProperty().set(false);
//                System.out.println("Attack!");
                if (frameCount % 5 == 0) {
                    heroController.nextAttackSprite();
                    gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                    gameFieldController.redrawTileMap();
                    redrawHero();
                    redrawAllEnemies();
                }
                frameCount++;
            }
        };
        animation.start();
    }

    public void animateNextStep(Pair<Integer, Integer> tileTarget, Runnable callbackToNextHeroStep) {
//        System.out.println("Animation " + characterController.getClass().getName() + " to target: " + tileTarget);
        animation = new AnimationTimer() {
            final double duration = 250;
            final double fps = 60;
            final int numFrames = (int) Math.ceil(duration / 1000 * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    redrawAllEnemies();
                    frameCount = 0;
                    heroController.getTileIndexXProperty().set(tileTarget.getKey());
                    heroController.getTileIndexYProperty().set(tileTarget.getValue());
                    gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                    gameFieldController.redrawTileMap();
                    gameField.getGraphicsContext2D().drawImage(heroController.getView(), tileTarget.getKey() * TILE_SIZE, tileTarget.getValue() * TILE_SIZE);
                    centerX = tileTarget.getKey() * TILE_SIZE;
                    centerY = tileTarget.getValue() * TILE_SIZE;
//                    System.out.println("Center: " + cameraModel.centerX().get() + " " + cameraModel.centerY().get());
//                    System.out.println("Animation done for hero");
                    updateWorld(callbackToNextHeroStep);
                    return;
                }
                cameraModel.isAnimationEndProperty().set(false);
                double dx = (double) ((-heroController.getTileIndexXProperty().get() + tileTarget.getKey()) * TILE_SIZE) / numFrames;
                double dy = (double) ((-heroController.getTileIndexYProperty().get() + tileTarget.getValue()) * TILE_SIZE) / numFrames;
                double x = heroController.getTileIndexXProperty().get() * TILE_SIZE + dx * frameCount;
                double y = heroController.getTileIndexYProperty().get() * TILE_SIZE + dy * frameCount;
                gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                gameFieldController.redrawTileMap();
//                System.out.println("Draw hero on: " + x + " " + y);
                redrawAllEnemies();
                heroController.chooseSprite(SpriteType.REGULAR_SPRITE);
                gameField.getGraphicsContext2D().drawImage(heroController.getView(), x, y);
//                System.out.println("Hero was drew on: " + x + " " + y);
                frameCount++;
            }
        };
        animation.start();
    }

    private void updateWorld(Runnable callbackToNextHeroStep) {
        animation = new AnimationTimer() {
            final double duration = 250;
            final double fps = 60;
            final int numFrames = (int) Math.ceil(duration / 1000 * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    frameCount = 0;
                    for (EnemyController enemy : enemies) {
                        Pair<Integer, Integer> previousTile = new Pair<>(enemy.getTileIndexXProperty().get(), enemy.getTileIndexYProperty().get());
                        gameFieldController.releaseTile(previousTile);
                        Pair<Integer, Integer> target = new Pair<>(enemy.getTargetTileIndexXProperty().get(), enemy.getTargetTileIndexYProperty().get());
                        enemy.getTileIndexXProperty().set(target.getKey());
                        enemy.getTileIndexYProperty().set(target.getValue());
                        gameFieldController.captureTile(target);
                    }
                    redrawHero();
                    redrawAllEnemies();
//                    System.out.println("Animation done for all enemies");
                    cameraModel.isAnimationEndProperty().set(true);
                    callbackToNextHeroStep.run();
                    return;
                }
                cameraModel.isAnimationEndProperty().set(false);
                gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                gameFieldController.redrawTileMap();
                redrawHero();
                for (EnemyController enemy : enemies) {
                    Pair<Integer, Integer> target = new Pair<>(enemy.getTargetTileIndexXProperty().get(), enemy.getTargetTileIndexYProperty().get());
//                    System.out.println("Enemy target: " + target);
                    double dx = (double) ((-enemy.getTileIndexXProperty().get() + target.getKey()) * TILE_SIZE) / numFrames;
                    double dy = (double) ((-enemy.getTileIndexYProperty().get() + target.getValue()) * TILE_SIZE) / numFrames;
                    double x = enemy.getTileIndexXProperty().get() * TILE_SIZE + dx * frameCount;
                    double y = enemy.getTileIndexYProperty().get() * TILE_SIZE + dy * frameCount;
//                    System.out.println("Draw enemy on: " + " " + x + " " + y);
                    gameField.getGraphicsContext2D().drawImage(enemy.getView(), x, y);
                }
                frameCount++;
            }
        };
        animation.start();
    }

    private void redrawGameFieldByCenterCoordinate(double x, double y) {
        Affine transform = new Affine();
        transform.appendTranslation(x, y);
        transform.appendScale(scale, scale);
        transform.appendTranslation(-x, -y);
        gameField.getGraphicsContext2D().setTransform(transform);
//        System.out.println("Redraw tile map");
        gameFieldController.redrawTileMap();
    }

    private void redrawAllEnemies() {
        for (EnemyController enemy : enemies) {
//            System.out.println("Redraw enemy on tile: " + enemy.getTileIndexXProperty().get() + " " + enemy.getTileIndexYProperty().get());
            gameField.getGraphicsContext2D().drawImage(enemy.getView(), enemy.getTileIndexXProperty().get() * TILE_SIZE, enemy.getTileIndexYProperty().get() * TILE_SIZE);
        }
    }

    private void redrawHero() {
//        System.out.println("Redraw hero on tile: " + heroController.getTileIndexXProperty().get() + " " + heroController.getTileIndexYProperty().get());
        gameField.getGraphicsContext2D().drawImage(heroController.getView(), heroController.getTileIndexXProperty().get() * TILE_SIZE, heroController.getTileIndexYProperty().get() * TILE_SIZE);
    }
}