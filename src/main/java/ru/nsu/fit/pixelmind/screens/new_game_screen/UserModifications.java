package ru.nsu.fit.pixelmind.screens.new_game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

public record UserModifications(@NotNull CharacterType heroType) {
}
