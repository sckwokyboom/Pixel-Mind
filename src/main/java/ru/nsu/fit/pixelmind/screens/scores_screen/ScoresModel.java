package ru.nsu.fit.pixelmind.screens.scores_screen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScoresModel {
    private List<HighScoreEntry> scores = new ArrayList<>();
    private final List<HighScoreEntry> newScores = new ArrayList<>();

    public List<HighScoreEntry> scores() {
        return scores;
    }

    public void setScores(@NotNull List<HighScoreEntry> scores) {
        this.scores = scores;
    }

    public List<HighScoreEntry> newScores() {
        return newScores;
    }
}
