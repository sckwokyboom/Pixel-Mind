package ru.nsu.fit.pixelmind.screens.load_game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;

public record CharacterForJson(@NotNull TileIndexCoordinates currentPosition,
                               @NotNull CharacterType characterType,
                               int currentHealth,
                               int damageValue) {
}
