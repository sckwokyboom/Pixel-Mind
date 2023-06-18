package ru.nsu.fit.pixelmind.screens.game;

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

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}
