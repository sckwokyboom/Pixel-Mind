package ru.nsu.fit.pixelmind.screens.game.game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.GameSession;

public class GameModel {
    private int score;
    private GameSession gameSession;

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
}
