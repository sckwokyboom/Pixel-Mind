package ru.nsu.fit.pixelmind.config;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.game_field.TileSetAssetName;

public record ResourcesConfig(@NotNull String avatarsPath,
                              @NotNull CharacterType[] avatars,
                              @NotNull CharacterSpriteAssetName[] sprites,
                              @NotNull TileSetAssetName[] tileSets) {
}
