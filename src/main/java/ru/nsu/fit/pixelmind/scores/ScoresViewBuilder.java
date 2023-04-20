package ru.nsu.fit.pixelmind.scores;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.widgets.Labels;

public class ScoresViewBuilder implements Builder<Region> {
    @Override
    public Region build() {
        VBox results = new VBox(6, Labels.h4("Scores"));
        results.setPadding(new Insets(40));
        return results;
    }
}
