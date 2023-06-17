package ru.nsu.fit.pixelmind.screens.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private CharacterController hero;
    private final List<CharacterController> enemies;
    private int score;
    private final IntegerProperty enemiesCount = new SimpleIntegerProperty();

    private GameSession gameSession;

    public CharacterController getCharacterController() {
        return hero;
    }


    public GameModel() {
        enemies = new ArrayList<>();
    }

    public List<CharacterController> getEnemies() {
        return enemies;
    }

    public int getScore() {
        return score;
    }

    public void setHero(CharacterController hero) {
        this.hero = hero;
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
