package ru.nsu.fit.pixelmind.screens.game.game_field.tile_map;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileController;

public class TileMapModel {
    private final @NotNull TileController[][] tileMap;

    private final TileMapSize tileMapSize;

    public TileMapModel(@NotNull TileController[][] tileMap, TileMapSize tileMapSize) {
        this.tileMap = tileMap;
        this.tileMapSize = tileMapSize;
    }

    @NotNull
    public TileController[][] tileMap() {
        return tileMap;
    }

    public TileMapSize tileMapSize() {
        return tileMapSize;
    }
}
