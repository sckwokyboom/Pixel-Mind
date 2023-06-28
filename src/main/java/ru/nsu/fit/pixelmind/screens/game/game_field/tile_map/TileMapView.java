package ru.nsu.fit.pixelmind.screens.game.game_field.tile_map;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;

import java.util.Map;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class TileMapView implements Builder<Canvas> {
    private final @NotNull TileMapModel model;
    private Map<TileType, Image> tileTypeImageMap;
    private final @NotNull Canvas gameFieldCanvas;


    TileMapView(@NotNull TileMapModel model) {
        this.model = model;
        gameFieldCanvas = new Canvas(model.tileMapSize().width() * TILE_SIZE, model.tileMapSize().height() * TILE_SIZE);
        gameFieldCanvas.getGraphicsContext2D().setImageSmoothing(false);
        gameFieldCanvas.setCache(true);
        gameFieldCanvas.setCacheHint(CacheHint.SPEED);
    }

    public void setTileTypeImageMap(@NotNull Map<TileType, Image> tileTypeImageMap) {
        this.tileTypeImageMap = tileTypeImageMap;
    }

    @Override
    @NotNull
    public Canvas build() {
        System.out.println("Redraw tile map in GameField");
        drawTileMap();
        return gameFieldCanvas;
    }

    public void drawTileMap() {
        for (int i = 0; i < model.tileMapSize().height(); i++) {
            for (int j = 0; j < model.tileMapSize().width(); j++) {
                TileType tileType = model.tileMap()[i][j].getType();
                model.tileMap()[i][j].setTexture(tileTypeImageMap.get(tileType));
                Image tileTexture = model.tileMap()[i][j].getView();
                gameFieldCanvas.getGraphicsContext2D().drawImage(tileTexture, j * TILE_SIZE, i * TILE_SIZE);
            }
        }
    }

    @NotNull Map<TileType, Image> tileTypeImageMap() {
        return tileTypeImageMap;
    }
}
