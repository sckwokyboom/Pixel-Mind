package ru.nsu.fit.pixelmind.config;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.game_field.tile_map.TileMapSize;

import java.util.List;

public record GameSessionConfig(@NotNull TileSetType tileSetType,
                                @NotNull TileType[][] tileMap,
                                @NotNull TileMapSize tileMapSize,
                                @NotNull CharacterType heroType,
                                @NotNull List<CharacterType> enemiesTypes) {
}
