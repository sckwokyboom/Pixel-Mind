package ru.nsu.fit.pixelmind.screens.load_game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.game_field.TileSetType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;

import java.util.List;

public record GameSessionForJson(@NotNull TileType[][] gameField,
                                 @NotNull CharacterForJson character,
                                 @NotNull List<CharacterForJson> enemies,
                                 @NotNull TileSetType tileSetType) {
}
