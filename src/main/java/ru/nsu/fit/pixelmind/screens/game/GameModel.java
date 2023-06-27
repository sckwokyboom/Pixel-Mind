package ru.nsu.fit.pixelmind.screens.game;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private int score;
    private GameSession gameSession;
    private GameSessionConfig gameSessionConfig;
    private final List<SavedSessionEntry> newSaves = new ArrayList<>();

    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public GameSession gameSession() {
        return gameSession;
    }

    public void setGameSession(@NotNull GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public List<SavedSessionEntry> newSaves() {
        return newSaves;
    }

    public GameSessionConfig gameSessionConfig() {
        return gameSessionConfig;
    }

    public void setGameSessionConfig(GameSessionConfig gameSessionConfig) {
        this.gameSessionConfig = gameSessionConfig;
    }
}
