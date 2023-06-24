package ru.nsu.fit.pixelmind.screens.game.game_field;

import org.jetbrains.annotations.NotNull;

public record TileSetAssetName(@NotNull TileSetType tileSetType,
                               @NotNull String tileSetAsset) {
}
