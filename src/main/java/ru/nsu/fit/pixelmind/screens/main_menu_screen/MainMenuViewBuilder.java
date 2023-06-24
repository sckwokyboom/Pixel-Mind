package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;
import ru.nsu.fit.pixelmind.utils.widgets.Labels;

public class MainMenuViewBuilder implements Builder<Region> {

    private final Runnable newGameButtonHandler;
    private final Runnable loadGameButtonHandler;
    private final Runnable scoresButtonHandler;
    private final Runnable exitButtonHandler;

    public MainMenuViewBuilder(Runnable handleNewGameButton, Runnable handleLoadGameButton, Runnable handleScoresButton, Runnable handleExitButton) {
        this.newGameButtonHandler = handleNewGameButton;
        this.loadGameButtonHandler = handleLoadGameButton;
        this.scoresButtonHandler = handleScoresButton;
        this.exitButtonHandler = handleExitButton;
    }

    @Override
    public Region build() {
        Button newGameButton = Buttons.button("New Game", newGameButtonHandler);
        Button loadGameButton = Buttons.button("Load Game", loadGameButtonHandler);
        Button scoresButton = Buttons.button("Scores", scoresButtonHandler);
        Button exitButton = Buttons.button("Exit", exitButtonHandler);

        VBox results = new VBox(20, Labels.h4("Pixel Mind"), newGameButton, loadGameButton, scoresButton, exitButton);
        results.setPadding(new Insets(14));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}
