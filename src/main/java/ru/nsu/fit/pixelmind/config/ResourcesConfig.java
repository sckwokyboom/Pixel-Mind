package ru.nsu.fit.pixelmind.config;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.TileSetAssetName;

public record ResourcesConfig(@NotNull String avatarsPath,
                              @NotNull CharacterType[] avatars,
                              @NotNull CharacterSpriteAssetName[] sprites,
                              @NotNull TileSetAssetName[] tileSets) {
}
