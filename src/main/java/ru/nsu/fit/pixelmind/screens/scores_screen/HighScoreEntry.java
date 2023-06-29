package ru.nsu.fit.pixelmind.screens.scores_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

public record HighScoreEntry(@NotNull CharacterType heroType, int score) {
}
