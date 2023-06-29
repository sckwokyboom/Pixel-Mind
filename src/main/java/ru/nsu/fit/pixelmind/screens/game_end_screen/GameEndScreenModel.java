package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jetbrains.annotations.NotNull;

public class GameEndScreenModel {
    @NotNull
    private final StringProperty gameResult = new SimpleStringProperty();
    @NotNull
    private final IntegerProperty gameScore = new SimpleIntegerProperty();

    @NotNull
    public StringProperty gameResultProperty() {
        return gameResult;
    }

    @NotNull
    public IntegerProperty gameScoreProperty() {
        return gameScore;
    }
}
