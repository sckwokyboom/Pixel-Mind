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
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;

public class GameEndScreenView implements Builder<Region> {
    @NotNull
    private final Runnable backToMainMenuButtonHandler;
    @NotNull
    private final IntegerProperty gameScore;
    @NotNull
    private final StringProperty gameResult;

    GameEndScreenView(@NotNull Runnable backToMainMenu, @NotNull IntegerProperty gameScore, @NotNull StringProperty gameResult) {
        this.backToMainMenuButtonHandler = backToMainMenu;
        this.gameScore = gameScore;
        this.gameResult = gameResult;
    }

    @Override
    @NotNull
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
