package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameEndScreenModel {
    private final StringProperty gameResult = new SimpleStringProperty();

    private final IntegerProperty gameScore = new SimpleIntegerProperty();
    public String getGameResult() {
        return gameResult.get();
    }

    public StringProperty gameResultProperty() {
        return gameResult;
    }

    public int getGameScore() {
        return gameScore.get();
    }

    public IntegerProperty gameScoreProperty() {
        return gameScore;
    }
}
