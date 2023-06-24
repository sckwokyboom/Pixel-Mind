package ru.nsu.fit.pixelmind.screens.game.character;

import org.jetbrains.annotations.NotNull;

public record CharacterSpriteAssetName(@NotNull CharacterType characterType,
                                       @NotNull String spritesAsset) {
}
