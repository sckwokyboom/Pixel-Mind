package ru.nsu.fit.pixelmind.screens.scores_screen;

import ru.nsu.fit.pixelmind.characters.character.CharacterType;

public record HighScoreEntry(CharacterType heroType, int score) {
}
