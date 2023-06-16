package ru.nsu.fit.pixelmind.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.characters.hero.HeroController;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private HeroController hero;
    private final List<EnemyController> enemies;
    private int score;
    private final IntegerProperty enemiesCount = new SimpleIntegerProperty();

    public HeroController getHeroController() {
        return hero;
    }


    public GameModel() {
        enemies = new ArrayList<>();
    }

    public List<EnemyController> getEnemies() {
        return enemies;
    }

    public int getScore() {
        return score;
    }

    public void setHero(HeroController hero) {
        this.hero = hero;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }
}
