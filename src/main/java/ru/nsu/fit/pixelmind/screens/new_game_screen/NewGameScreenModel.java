package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class NewGameScreenModel {
    private StringProperty heroType;
    private BooleanProperty startGameSelected;

    public BooleanProperty startGameSelectedProperty() {
        return startGameSelected;
    }

    public StringProperty getHeroType() {
        return heroType;
    }

    public NewGameScreenModel() {

    }

    public NewGameScreenModel(StringProperty heroType, BooleanProperty newGameStarted) {
        this.heroType = heroType;
        this.startGameSelected = newGameStarted;
    }
}
