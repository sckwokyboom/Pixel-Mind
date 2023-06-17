package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class GameFieldViewBuilder implements Builder<Canvas> {
    private final GameFieldModel model;
    private final @NotNull Map<TileType, Image> tileTypeImageMap;
    private final Canvas gameFieldCanvas;


    public GameFieldViewBuilder(@NotNull GameFieldModel model, @NotNull Map<TileType, Image> tileTypeImageMap) {
        this.model = model;
        this.tileTypeImageMap = tileTypeImageMap;
        gameFieldCanvas = new Canvas(model.width() * TILE_SIZE, model.height() * TILE_SIZE);
        gameFieldCanvas.getGraphicsContext2D().setImageSmoothing(false);
        gameFieldCanvas.setCache(true);
        gameFieldCanvas.setCacheHint(CacheHint.SPEED);
    }

    @Override
    public Canvas build() {
        System.out.println("Redraw tile map in GameField");
        drawTileMap();
        return gameFieldCanvas;
    }

    public void drawTileMap() {
        for (int i = 0; i < model.height(); i++) {
            for (int j = 0; j < model.width(); j++) {
                TileType tileType = model.tileMap()[i][j].getType();
                model.tileMap()[i][j].setTexture(tileTypeImageMap.get(tileType));
                Image tileTexture = model.tileMap()[i][j].getView();
                gameFieldCanvas.getGraphicsContext2D().drawImage(tileTexture, j * TILE_SIZE, i * TILE_SIZE);
            }
        }
    }
}
