package ru.nsu.fit.pixelmind.config;

import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.TileMapSize;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.TileType;

import java.util.List;

public record GameSessionConfig(TileSetType tileSetType, TileType[][] tileMap, TileMapSize tileMapSize,
                                CharacterType heroType, List<CharacterType> enemiesTypes) {
}
