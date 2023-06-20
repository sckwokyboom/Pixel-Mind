package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.widgets.Buttons;

public class NewGameScreenViewBuilder implements Builder<Region> {
    private final Runnable backToMainMenuButtonHandler;
    private final Runnable startButtonHandler;

    public NewGameScreenViewBuilder(Runnable backToMainMenuButtonHandler, Runnable startButtonHandler) {
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
        this.startButtonHandler = startButtonHandler;
    }


    @Override
    public Region build() {
        Label label = new Label("Click to create a new game");
        Button startGameButton = Buttons.button("Start Game", startButtonHandler);
        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);
//        startGameButton.setOnAction(e -> System.out.println("New game started!"));

        VBox results = new VBox(20, label, startGameButton, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}