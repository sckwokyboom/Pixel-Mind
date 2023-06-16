package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.widgets.Buttons;

public class ScoresViewBuilder implements Builder<Region> {
    private final Runnable backToMainMenuButtonHandler;

    public ScoresViewBuilder(Runnable backToMainMenuButtonHandler) {
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
    }

    @Override
    public Region build() {
        Label label = new Label("Scores");
        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);
        VBox results = new VBox(20, label, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}
