package ru.nsu.fit.pixelmind.screens.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.characters.character.CharacterView;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.List;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;
import static ru.nsu.fit.pixelmind.characters.ActionType.MOVE;


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
        redrawHeroOnPosition(gameModel.gameSession().hero().getView(), gameModel.gameSession().hero().currentPosition());
        System.out.println(gameModel.gameSession().enemies());
        redrawEnemiesCurrentPositions(gameModel.gameSession().enemies().stream().map(CharacterController::getView).toList());
        return gameScreen;
    }

    public void animateNextStep(CharacterView hero, List<CharacterView> enemies, Runnable callback) {
        animateStepBlock(hero, enemies, callback);
    }

    public void animateStepBlock(@NotNull CharacterView heroView, @NotNull List<CharacterView> enemiesViews, @NotNull Runnable callback) {
        var animation = new AnimationTimer() {
            final double duration = 150;
            final double fps = 60;
            final int numFramesOnHeroAnimation = (int) Math.ceil((duration / 1000) * fps);
            final int numFramesOnEnemiesAnimation = (int) Math.ceil((duration / 1000) * fps);
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount >= numFramesOnHeroAnimation + numFramesOnEnemiesAnimation) {
                    stop();
                    isAnimatingRightNow = false;
                    heroView.setCurrentSpriteType(SpriteType.REGULAR_SPRITE);
                    callback.run();
                    return;
                }
                isAnimatingRightNow = true;
                gameField.getGraphicsContext2D().clearRect(0, 0, gameField.getWidth(), gameField.getHeight());
                gameFieldController.redrawTileMap();
                if (frameCount >= numFramesOnHeroAnimation) {
                    if (heroView.actionTypeOnThisStep() == MOVE) {
                        heroView.setCurrentPositionOnThisStep(heroView.targetTile());
                    }
                }
                if (frameCount < numFramesOnHeroAnimation) {
                    switch (heroView.actionTypeOnThisStep()) {
                        case ATTACK -> {
                            if (frameCount % 5 == 0) {
                                heroView.nextAttackSprite();
                            }
                            redrawHeroOnPosition(heroView, heroView.currentTile());
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                        case MOVE -> {
                            TileIndexCoordinates heroCurrentTile = heroView.currentTile();
                            TileIndexCoordinates heroTargetTile = heroView.targetTile();
                            double dx = (double) ((heroTargetTile.x() - heroCurrentTile.x()) * TILE_SIZE) / numFramesOnHeroAnimation;
                            double dy = (double) ((heroTargetTile.y() - heroCurrentTile.y()) * TILE_SIZE) / numFramesOnHeroAnimation;
                            double x = heroCurrentTile.x() * TILE_SIZE + dx * frameCount;
                            double y = heroCurrentTile.y() * TILE_SIZE + dy * frameCount;
                            gameField.getGraphicsContext2D().drawImage(heroView.build(), x, y);
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                        case WAIT, DIE -> {
                            redrawHeroOnPosition(heroView, heroView.currentTile());
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                    }
                }
                if (frameCount >= numFramesOnHeroAnimation) {
                    for (CharacterView enemyView : enemiesViews) {
                        switch (enemyView.actionTypeOnThisStep()) {
                            case ATTACK, DIE, WAIT -> {
                                gameField.getGraphicsContext2D().drawImage(enemyView.build(), enemyView.currentTile().x() * TILE_SIZE, enemyView.currentTile().y() * TILE_SIZE);
                                redrawHeroOnPosition(heroView, heroView.currentTile());
                            }
                            case MOVE -> {
                                TileIndexCoordinates enemyCurrentTile = enemyView.currentTile();
                                TileIndexCoordinates enemyTargetTile = enemyView.targetTile();
                                double dx = (double) ((enemyTargetTile.x() - enemyCurrentTile.x()) * TILE_SIZE) / numFramesOnEnemiesAnimation;
                                double dy = (double) ((enemyTargetTile.y() - enemyCurrentTile.y()) * TILE_SIZE) / numFramesOnEnemiesAnimation;
                                double x = enemyCurrentTile.x() * TILE_SIZE + dx * (frameCount - numFramesOnHeroAnimation);
                                double y = enemyCurrentTile.y() * TILE_SIZE + dy * (frameCount - numFramesOnHeroAnimation);
                                gameField.getGraphicsContext2D().drawImage(enemyView.build(), x, y);
                                redrawHeroOnPosition(heroView, heroView.currentTile());
                            }
                        }
                    }
                }
                frameCount++;
            }

        };
        animation.start();
    }

    private void redrawHeroOnPosition(CharacterView heroView, TileIndexCoordinates position) {
        gameField.getGraphicsContext2D().drawImage(heroView.build(), position.x() * TILE_SIZE, position.y() * TILE_SIZE);
    }

    private void redrawEnemiesCurrentPositions(List<CharacterView> enemiesForRedraw) {
        for (CharacterView enemyView : enemiesForRedraw) {
            gameField.getGraphicsContext2D().drawImage(enemyView.build(), enemyView.currentTile().x() * TILE_SIZE, enemyView.currentTile().y() * TILE_SIZE);
        }
    }

    public boolean isAnimatingRightNow() {
        return isAnimatingRightNow;
    }
}
