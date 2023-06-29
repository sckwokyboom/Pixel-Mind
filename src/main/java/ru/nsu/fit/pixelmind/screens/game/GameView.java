package ru.nsu.fit.pixelmind.screens.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.camera.CameraController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterView;
import ru.nsu.fit.pixelmind.screens.game.character.SpriteType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;

import java.util.List;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;
import static ru.nsu.fit.pixelmind.screens.game.character.ActionType.MOVE;


public class GameView implements Builder<Region> {
    private Canvas tileMapView;
    private TileMapController tileMapController;
    private final CameraController cameraController;
    private final GameModel gameModel;
    private boolean isAnimatingRightNow;
    private Resources resources;

    protected GameView(@NotNull CameraController cameraController, @NotNull GameModel gameModel) {
        this.cameraController = cameraController;
        this.gameModel = gameModel;
    }

    @NotNull
    public Resources resources() {
        return resources;
    }

    public void setResources(@NotNull Resources resources) {
        this.resources = resources;
    }

    @Override
    @NotNull
    public Region build() {
        System.out.println("Game view built");
        tileMapController = gameModel.gameSession().gameField();
        tileMapView = tileMapController.getView();

        StackPane gameScreen = new StackPane();
        gameScreen.getChildren().add(cameraController.getView());
        gameScreen.setBackground(Background.fill(Color.BLACK));
        redrawCharacterOnPosition(gameModel.gameSession().hero().getView(), gameModel.gameSession().hero().currentTile());
        redrawEnemiesCurrentPositions(gameModel.gameSession().enemies().stream().map(CharacterController::getView).toList());
        return gameScreen;
    }

    public void animateHeroAndEnemiesSteps(@NotNull CharacterView hero, @NotNull List<CharacterView> enemies, @NotNull Runnable callback) {
        animateStepBlock(hero, enemies, callback);
    }

    public void animateStepBlock(@NotNull CharacterView heroView, @NotNull List<CharacterView> enemiesViews, @NotNull Runnable callback) {
        var animation = new AnimationTimer() {
            final double durationMs = 150;
            final double fps = 60;
            final int numFramesOnHeroAnimation = (int) Math.ceil((durationMs / 1000) * fps);
            final int numFramesOnEnemiesAnimation = (int) Math.ceil((durationMs / 1000) * fps);
            boolean isHeroAnimationFinished = false;
            int frameCount = 0;

            @Override
            public void handle(long now) {
                if (frameCount == numFramesOnHeroAnimation + numFramesOnEnemiesAnimation || heroView.characterSprites() == null) {
                    stop();
                    isAnimatingRightNow = false;
                    heroView.setCurrentSpriteType(SpriteType.REGULAR_SPRITE);
                    callback.run();
                    return;
                }
                isAnimatingRightNow = true;
                tileMapView.getGraphicsContext2D().clearRect(0, 0, tileMapView.getWidth(), tileMapView.getHeight());
                tileMapController.redrawTileMap();

                if (frameCount == numFramesOnHeroAnimation) {
                    isHeroAnimationFinished = true;
                    if (heroView.actionTypeOnThisStep() == MOVE) {
                        heroView.setCurrentPositionOnThisStep(heroView.targetTile());
                    }
                }

                if (!isHeroAnimationFinished) {
                    switch (heroView.actionTypeOnThisStep()) {
                        case ATTACK -> {
                            if (frameCount % 5 == 0) {
                                heroView.nextAttackSprite();
                            }
                            redrawCharacterOnPosition(heroView, heroView.currentTile());
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                        case MOVE -> {
                            drawMoveActionOfCharacter(heroView, numFramesOnHeroAnimation, frameCount);
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                        case WAIT, DIE -> {
                            redrawCharacterOnPosition(heroView, heroView.currentTile());
                            redrawEnemiesCurrentPositions(enemiesViews);
                        }
                    }
                }

                if (isHeroAnimationFinished) {
                    for (CharacterView enemyView : enemiesViews) {
                        switch (enemyView.actionTypeOnThisStep()) {
                            case ATTACK, DIE, WAIT -> {
                                redrawCharacterOnPosition(enemyView, enemyView.currentTile());
                                redrawCharacterOnPosition(heroView, heroView.currentTile());
                            }
                            case MOVE -> {
                                drawMoveActionOfCharacter(enemyView, numFramesOnEnemiesAnimation, frameCount - numFramesOnHeroAnimation);
                                redrawCharacterOnPosition(heroView, heroView.currentTile());
                            }
                        }
                    }
                }
                frameCount++;
            }

        };
        animation.start();
    }

    private void redrawCharacterOnPosition(@NotNull CharacterView heroView, @NotNull TileIndexCoordinates position) {
        tileMapView.getGraphicsContext2D().drawImage(heroView.build(), position.x() * TILE_SIZE, position.y() * TILE_SIZE);
    }

    private void redrawEnemiesCurrentPositions(@NotNull List<CharacterView> enemiesForRedraw) {
        for (CharacterView enemyView : enemiesForRedraw) {
            tileMapView.getGraphicsContext2D().drawImage(enemyView.build(), enemyView.currentTile().x() * TILE_SIZE, enemyView.currentTile().y() * TILE_SIZE);
        }
    }

    private void drawMoveActionOfCharacter(@NotNull CharacterView characterView, int numOfFrames, int currentFrameNum) {
        TileIndexCoordinates characterCurrentTile = characterView.currentTile();
        TileIndexCoordinates characterTargetTile = characterView.targetTile();
        double dx = (double) ((characterTargetTile.x() - characterCurrentTile.x()) * TILE_SIZE) / numOfFrames;
        double dy = (double) ((characterTargetTile.y() - characterCurrentTile.y()) * TILE_SIZE) / numOfFrames;
        double x = characterCurrentTile.x() * TILE_SIZE + dx * currentFrameNum;
        double y = characterCurrentTile.y() * TILE_SIZE + dy * currentFrameNum;
        tileMapView.getGraphicsContext2D().drawImage(characterView.build(), x, y);
    }

    public boolean isAnimatingRightNow() {
        return isAnimatingRightNow;
    }
}
