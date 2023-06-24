package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameEndScreenModel {
    private final StringProperty gameResult = new SimpleStringProperty();

    private final IntegerProperty gameScore = new SimpleIntegerProperty();

    public StringProperty gameResultProperty() {
        return gameResult;
    }

    public IntegerProperty gameScoreProperty() {
        return gameScore;
    }
}
