package ru.nsu.fit.pixelmind.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import ru.nsu.fit.pixelmind.hero.HeroController;

public class GameModel {
    private final StringProperty heroType;
    private final BooleanProperty startGameSelected;
    private final HeroController hero;

    public BooleanProperty startGameSelectedProperty() {
        return startGameSelected;
    }

    public StringProperty getHeroType() {
        return heroType;
    }

    public HeroController getHeroController() {
        return hero;
    }

    public GameModel(StringProperty heroType, BooleanProperty newGameStarted, HeroController hero) {
        this.heroType = heroType;
        this.startGameSelected = newGameStarted;
        this.hero = hero;
    }
}
