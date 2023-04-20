package ru.nsu.fit.pixelmind.newGameScreen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

public class NewGameScreenModel {
    private final StringProperty heroType;
    private final BooleanProperty startGameSelected;

    public BooleanProperty startGameSelectedProperty() {
        return startGameSelected;
    }

    public StringProperty getHeroType() {
        return heroType;
    }

    public NewGameScreenModel(StringProperty heroType, BooleanProperty newGameStarted) {
        this.heroType = heroType;
        this.startGameSelected = newGameStarted;
    }
}
