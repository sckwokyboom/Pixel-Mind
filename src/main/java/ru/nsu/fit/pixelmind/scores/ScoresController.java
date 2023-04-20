package ru.nsu.fit.pixelmind.scores;

import javafx.scene.layout.Region;
import javafx.util.Builder;

public class ScoresController {
    Builder<Region> viewBuilder;

    public ScoresController() {
        viewBuilder = new ScoresViewBuilder();
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
