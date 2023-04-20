package ru.nsu.fit.pixelmind.newGameScreen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class NewGameScreenController {
    Builder<Region> viewBuilder;

    public NewGameScreenController(StringProperty heroType, BooleanProperty newGameStarted) {
        NewGameScreenModel model = new NewGameScreenModel(heroType, newGameStarted);
        viewBuilder = new NewGameScreenViewBuilder(model);
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
