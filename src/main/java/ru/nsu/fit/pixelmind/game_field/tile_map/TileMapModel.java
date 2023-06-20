package ru.nsu.fit.pixelmind.game_field.tile_map;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.game_field.tile.TileController;

public class TileMapModel {
    private final @NotNull TileController[][] tileMap;
    private int tileMapHeight;
    private int tileMapWidth;

    public TileMapModel(@NotNull TileController[][] tileMap) {
        this.tileMap = tileMap;
    }

    @NotNull
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
