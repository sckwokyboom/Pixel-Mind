package ru.nsu.fit.pixelmind.screens.scores_screen;

import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

public record HighScoreEntry(CharacterType heroType, int score) {
}
