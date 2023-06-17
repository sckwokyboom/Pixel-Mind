package ru.nsu.fit.pixelmind.game_field;

import ru.nsu.fit.pixelmind.game_field.tile.TileController;

public class GameFieldModel {
    private final TileController[][] tileMap;
    private int tileMapHeight;
    private int tileMapWidth;

    public GameFieldModel(TileController[][] tileMap) {
        this.tileMap = tileMap;
    }

    public TileController[][] tileMap() {
        return tileMap;
    }

    public int width() {
        return tileMapWidth;
    }

    public int height() {
        return tileMapHeight;
    }

    public void setHeight(int tileMapHeight) {
        this.tileMapHeight = tileMapHeight;
    }

    public void setWidth(int tileMapWidth) {
        this.tileMapWidth = tileMapWidth;
    }
}
