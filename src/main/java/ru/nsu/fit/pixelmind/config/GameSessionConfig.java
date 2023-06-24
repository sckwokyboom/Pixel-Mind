package ru.nsu.fit.pixelmind.config;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.game_field.TileSetType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapSize;

import java.util.List;

public record GameSessionConfig(@NotNull TileSetType tileSetType,
                                @NotNull TileType[][] tileMap,
                                @NotNull TileMapSize tileMapSize,
                                @NotNull CharacterType heroType,
                                @NotNull List<@NotNull CharacterType> enemiesTypes) {
}
