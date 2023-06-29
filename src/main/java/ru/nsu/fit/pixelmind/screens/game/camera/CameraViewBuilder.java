package ru.nsu.fit.pixelmind.screens.game.camera;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.GameModel;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;

import java.util.function.Consumer;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class CameraViewBuilder implements Builder<Region> {
    private Canvas gameField;
    @NotNull
    private final GameModel gameModel;
    private double scale = 1.0;
    //    private double centerX;
    //    private double centerY;
    //    private double topLeftCornerOffsetX;
    //    private double topLeftCornerOffsetY;
    @NotNull
    private final Consumer<TileIndexCoordinates> tileClickedHandler;


    CameraViewBuilder(@NotNull GameModel gameModel, @NotNull Consumer<TileIndexCoordinates> tileClickedHandler) {
        this.gameModel = gameModel;
        this.tileClickedHandler = tileClickedHandler;
    }

    @Override
    @NotNull
    public Region build() {
        gameField = gameModel.gameSession().gameField().getView();
        Pane gamePane = new Pane();
        gameField.layoutXProperty().bind(gamePane.widthProperty().subtract(gameField.widthProperty()).divide(2));
        gameField.layoutYProperty().bind(gamePane.heightProperty().subtract(gameField.heightProperty()).divide(2));
        gameField.setOnMouseReleased(event -> {
            if (event.isStillSincePress()) {
                int curX = (int) ((event.getX() + gamePane.getTranslateX()) / scale);
                int curY = (int) ((event.getY() + gamePane.getTranslateY()) / scale);
                int tileCurX = curX / TILE_SIZE;
                int tileCurY = curY / TILE_SIZE;
                TileIndexCoordinates tile = new TileIndexCoordinates(tileCurX, tileCurY);
                tileClickedHandler.accept(tile);
                System.out.println("User clicked tile: " + tileCurX + " " + tileCurY);
            }
        });
        gamePane.getChildren().add(gameField);
        gamePane.setFocusTraversable(true);
        gamePane.setOnKeyPressed(event -> {
            System.out.println(event.getCode());
            switch (event.getCode()) {
                case UP -> {
                    int tileCurX = gameModel.gameSession().hero().currentTile().x();
                    int tileCurY = gameModel.gameSession().hero().currentTile().y() - 1;
                    TileIndexCoordinates tile = new TileIndexCoordinates(tileCurX, tileCurY);
                    tileClickedHandler.accept(tile);
                    System.out.println("User clicked tile: " + tileCurX + " " + tileCurY);
                }
                case DOWN -> {
                    int tileCurX = gameModel.gameSession().hero().currentTile().x();
                    int tileCurY = gameModel.gameSession().hero().currentTile().y() + 1;
                    TileIndexCoordinates tile = new TileIndexCoordinates(tileCurX, tileCurY);
                    tileClickedHandler.accept(tile);
                    System.out.println("User clicked tile: " + tileCurX + " " + tileCurY);
                }
                case LEFT -> {
                    int tileCurX = gameModel.gameSession().hero().currentTile().x() - 1;
                    int tileCurY = gameModel.gameSession().hero().currentTile().y();
                    TileIndexCoordinates tile = new TileIndexCoordinates(tileCurX, tileCurY);
                    tileClickedHandler.accept(tile);
                    System.out.println("User clicked tile: " + tileCurX + " " + tileCurY);

                }
                case RIGHT -> {
                    int tileCurX = gameModel.gameSession().hero().currentTile().x() + 1;
                    int tileCurY = gameModel.gameSession().hero().currentTile().y();
                    TileIndexCoordinates tile = new TileIndexCoordinates(tileCurX, tileCurY);
                    tileClickedHandler.accept(tile);
                    System.out.println("User clicked tile: " + tileCurX + " " + tileCurY);

                }
            }
        });
        return gamePane;
    }
}