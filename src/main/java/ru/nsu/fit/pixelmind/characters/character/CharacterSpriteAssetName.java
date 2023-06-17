package ru.nsu.fit.pixelmind.characters.character;

import org.jetbrains.annotations.NotNull;

public record CharacterSpriteAssetName(@NotNull CharacterType characterType, @NotNull String spritesAsset) {
}
