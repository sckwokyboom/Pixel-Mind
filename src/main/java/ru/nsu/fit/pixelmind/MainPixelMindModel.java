package ru.nsu.fit.pixelmind;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainPixelMindModel {
    private final BooleanProperty newGameScreenSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty loadGameScreenSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty scoresScreenSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty gameScreenSelected = new SimpleBooleanProperty(false);
    private final BooleanProperty mainMenuScreenSelected = new SimpleBooleanProperty(true);
    private final StringProperty heroTypeSelected = new SimpleStringProperty("");
    private final BooleanProperty newGameStarted = new SimpleBooleanProperty(false);
    private final BooleanProperty loadGameStarted = new SimpleBooleanProperty(false);


    {
        mainMenuScreenSelected.bind(
                newGameScreenSelected.not()
                        .and(loadGameScreenSelected.not())
                        .and(scoresScreenSelected.not())
                        .and(gameScreenSelected.not()));
        gameScreenSelected.bind(newGameStarted.or(loadGameStarted));
    }

    public BooleanProperty newGameScreenSelectedProperty() {
        return newGameScreenSelected;
    }

    public BooleanProperty loadGameScreenSelectedProperty() {
        return loadGameScreenSelected;
    }

    public BooleanProperty scoresScreenSelectedProperty() {
        return scoresScreenSelected;
    }

    public BooleanProperty mainMenuScreenSelectedProperty() {
        return mainMenuScreenSelected;
    }

    public BooleanProperty gameScreenSelectedProperty() {
        return gameScreenSelected;
    }

    public StringProperty getHeroTypeSelected() {
        return heroTypeSelected;
    }

    public BooleanProperty getNewGameStarted() {
        return newGameStarted;
    }


}
