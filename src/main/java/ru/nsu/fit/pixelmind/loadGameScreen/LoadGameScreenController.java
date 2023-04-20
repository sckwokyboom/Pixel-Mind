package ru.nsu.fit.pixelmind.loadGameScreen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.scores.ScoresViewBuilder;

public class LoadGameScreenController {
    Builder<Region> viewBuilder;

    public LoadGameScreenController() {
        viewBuilder = new LoadGameScreenViewBuilder();
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
