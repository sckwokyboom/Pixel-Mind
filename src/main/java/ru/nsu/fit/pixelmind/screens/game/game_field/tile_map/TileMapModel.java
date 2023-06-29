package ru.nsu.fit.pixelmind.screens.game.game_field.tile_map;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileController;

public record TileMapModel(@NotNull TileController[][] tileMap,
                           @NotNull TileMapSize tileMapSize) {
}
