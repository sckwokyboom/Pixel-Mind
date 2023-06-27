package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;

public class GameEndScreenViewBuilder implements Builder<Region> {
    private final Runnable backToMainMenuButtonHandler;
    private final IntegerProperty gameScore;
    private final StringProperty gameResult;

    GameEndScreenViewBuilder(Runnable backToMainMenu, IntegerProperty gameScore, StringProperty gameResult) {
        this.backToMainMenuButtonHandler = backToMainMenu;
        this.gameScore = gameScore;
        this.gameResult = gameResult;
    }

    @Override
    public Region build() {
        Label label = new Label(gameResult.get());
        Label score = new Label("Your score: " + gameScore.get());
        Button backToMainMenuButton = Buttons.button("To main menu", backToMainMenuButtonHandler);
        VBox results = new VBox(20, label, score, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}
