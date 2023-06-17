package ru.nsu.fit.pixelmind.config;

import ru.nsu.fit.pixelmind.characters.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.game_field.TileSetAssetName;

public record ResourcesConfig(CharacterSpriteAssetName[] sprites, TileSetAssetName[] tileSets) {
}
