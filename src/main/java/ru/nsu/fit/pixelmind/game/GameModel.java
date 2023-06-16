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
    private final IntegerProperty score = new SimpleIntegerProperty();
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
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public int getEnemiesCount() {
        return enemiesCount.get();
    }

    public IntegerProperty enemiesCountProperty() {
        return enemiesCount;
    }

    public void setHero(HeroController hero) {
        this.hero = hero;
    }
}
