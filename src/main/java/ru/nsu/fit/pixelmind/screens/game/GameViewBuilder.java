package ru.nsu.fit.pixelmind.screens.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.StepInfo;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.characters.character.CharacterView;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;


public class GameViewBuilder implements Builder<Region> {
    private Canvas gameField;
    private GameFieldController gameFieldController;
    private final CameraController cameraController;
    private final GameModel gameModel;
    private boolean isAnimatingRightNow;


    public GameViewBuilder(CameraController cameraController, GameModel gameModel) {
        this.cameraController = cameraController;
        this.gameModel = gameModel;
    }

    @Override
    public Region build() {
        gameFieldController = gameModel.gameSession().gameField();
        gameField = gameFieldController.getView();

        StackPane gameScreen = new StackPane();
        gameScreen.getChildren().add(cameraController.getView());
        gameScreen.setBackground(Background.fill(Color.BLACK));
//        redrawHero(gameModel.gameSession().hero(), gameModel.gameSession().hero().currentPosition());
//        redrawEnemies(gameModel.getEnemies());
        return gameScreen;
    }

    public void animateNextStep(CharacterController hero, StepInfo heroStepInfo, HashMap<CharacterController, StepInfo> enemiesAnimationInfo, Runnable callback) {
        switch (heroStepInfo.stepType()) {
            case ATTACK -> {
//                animateHeroAttack(hero, heroStepInfo, enemiesAnimationInfo, callback);
            }
            case MOVE -> {
//                animateHeroMove(hero, heroStepInfo, enemiesAnimationInfo, callback);
            }
            case DIE -> {
                //TODO: animate die
            }
        }
    }

    public void animateHeroAttack(CharacterView hero, StepInfo heroStepInfo, HashMap<CharacterController, StepInfo> enemiesAnimationInfo, Runnable callback) {
        var animation = new AnimationTimer() {
            final double duration = 500;
            final double fps = 20;
            final int numFrames = (int) Math.ceil((duration / 1000) * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
//                    animateEnemies(hero, heroStepInfo, enemiesAnimationInfo, callback);
                    return;
                }
                isAnimatingRightNow = true;
                if (frameCount % 5 == 0) {
                    gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                    gameFieldController.redrawTileMap();
//                    redrawEnemies(gameModel.getEnemies());
                    hero.nextAttackSprite();
                    redrawHero(hero, heroStepInfo.currentTile());
                }
                frameCount++;
            }

        };
        animation.start();
    }

    public void animateHeroMove(CharacterView hero, StepInfo heroStepInfo, HashMap<CharacterView, StepInfo> enemiesAnimationInfo, Runnable callback) {
        var animation = new AnimationTimer() {
            final double duration = 250;
            final double fps = 60;
            final int numFrames = (int) Math.ceil(duration / 1000 * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    animateEnemies(hero, heroStepInfo, enemiesAnimationInfo, callback);
                    return;
                }
                gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                gameFieldController.redrawTileMap();
                List<CharacterController> enemiesForRedraw = new ArrayList<>(gameModel.getEnemies());
                for (Map.Entry<CharacterView, StepInfo> entry : enemiesAnimationInfo.entrySet()) {
                    CharacterView enemy = entry.getKey();
                    StepInfo enemyStepInfo = entry.getValue();
                    switch (enemyStepInfo.stepType()) {
                        case MOVE -> {
                            enemiesForRedraw.remove(enemy);
                            gameField.getGraphicsContext2D().drawImage(enemy.getView(), enemyStepInfo.currentTile().x() * TILE_SIZE, enemyStepInfo.currentTile().y() * TILE_SIZE);
                        }
                        default -> {
                        }
                    }
                }
//                redrawEnemies(enemiesForRedraw);
                isAnimatingRightNow = true;
                TileIndexCoordinates heroCurrentTile = heroStepInfo.currentTile();
                TileIndexCoordinates heroTargetTile = heroStepInfo.targetTile();
                double dx = (double) ((heroTargetTile.x() - heroCurrentTile.x()) * TILE_SIZE) / numFrames;
                double dy = (double) ((heroTargetTile.y() - heroCurrentTile.y()) * TILE_SIZE) / numFrames;
                double x = heroCurrentTile.x() * TILE_SIZE + dx * frameCount;
                double y = heroCurrentTile.y() * TILE_SIZE + dy * frameCount;
                gameField.getGraphicsContext2D().drawImage(hero.getView(), x, y);
                frameCount++;
            }
        };
        animation.start();
    }

    private void animateEnemies(CharacterView hero, StepInfo heroStepInfo, HashMap<CharacterView, StepInfo> enemiesAnimationStepInfo, Runnable callbackToNextHeroStep) {
        var animation = new AnimationTimer() {
            final double duration = 250;
            final double fps = 60;
            final int numFrames = (int) Math.ceil(duration / 1000 * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFrames) {
                    stop();
                    isAnimatingRightNow = false;
                    callbackToNextHeroStep.run();
                    return;
                }
                isAnimatingRightNow = true;
                gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                gameFieldController.redrawTileMap();
                redrawHero(hero, heroStepInfo.targetTile());

//                List<CharacterView> enemiesForRedraw = new ArrayList<>(gameModel.getEnemies());
//                for (Map.Entry<CharacterController, StepInfo> entry : enemiesAnimationStepInfo.entrySet()) {
//                    CharacterController enemy = entry.getKey();
//                    StepInfo enemyStepInfo = entry.getValue();
//                    switch (enemyStepInfo.stepType()) {
//                        case ATTACK -> enemy.nextAttackSprite();
//                        case MOVE -> {
//                            enemiesForRedraw.remove(enemy);
//                            TileIndexCoordinates enemyCurrentTile = enemyStepInfo.currentTile();
//                            TileIndexCoordinates enemyTargetTile = enemyStepInfo.targetTile();
//                            double dx = (double) ((enemyTargetTile.x() - enemyCurrentTile.x()) * TILE_SIZE) / numFrames;
//                            double dy = (double) ((enemyTargetTile.y() - enemyCurrentTile.y()) * TILE_SIZE) / numFrames;
//                            double x = enemyCurrentTile.x() * TILE_SIZE + dx * frameCount;
//                            double y = enemyCurrentTile.y() * TILE_SIZE + dy * frameCount;
//                            gameField.getGraphicsContext2D().drawImage(enemy.getView(), x, y);
//                        }
//                        case DIE -> {
//                            //TODO: animate die
//                        }
//                    }
//                }
//                redrawEnemies(enemiesForRedraw);
//                frameCount++;
            }
        };
        animation.start();
    }


    private void redrawHero(CharacterView hero, TileIndexCoordinates position) {
        gameField.getGraphicsContext2D().drawImage(hero.getView(), position.x() * TILE_SIZE, position.y() * TILE_SIZE);
    }

    private void redrawEnemies(List<CharacterView> enemiesForRedraw) {
        for (CharacterView enemy : enemiesForRedraw) {
            gameField.getGraphicsContext2D().drawImage(enemy.getView(), enemy.currentPosition().x() * TILE_SIZE, enemy.currentPosition().y() * TILE_SIZE);
        }
    }

    public boolean isAnimatingRightNow() {
        return isAnimatingRightNow;
    }
}
