package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.tile.TileController;

import java.util.HashMap;

public class GameFieldModel {
    private final HashMap<TileType, Image> tileSet = new HashMap<>();
    private TileController[][] tileMap;
    private int tileMapHeight;
    private int tileMapWidth;

    public HashMap<TileType, Image> tileSet() {
        return tileSet;
    }

    public void updateLevelTileMap(TileController[][] tileMap) {
        this.tileMap = tileMap;
    }

    public TileController[][] tileMap() {
        return tileMap;
    }

    public int getTileMapWidth() {
        return tileMapWidth;
    }

    public int getTileMapHeight() {
        return tileMapHeight;
    }

    public void setTileMapHeight(int tileMapHeight) {
        this.tileMapHeight = tileMapHeight;
    }

    public void setTileMapWidth(int tileMapWidth) {
        this.tileMapWidth = tileMapWidth;
    }
}
