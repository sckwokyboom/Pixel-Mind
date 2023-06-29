package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;
import ru.nsu.fit.pixelmind.utils.widgets.Labels;

public class MainMenuViewBuilder implements Builder<Region> {
    @NotNull
    private final Runnable newGameButtonHandler;
    @NotNull
    private final Runnable loadGameButtonHandler;
    @NotNull
    private final Runnable scoresButtonHandler;
    @NotNull
    private final Runnable exitButtonHandler;

    MainMenuViewBuilder(@NotNull Runnable handleNewGameButton,
                        @NotNull Runnable handleLoadGameButton,
                        @NotNull Runnable handleScoresButton,
                        @NotNull Runnable handleExitButton) {
        this.newGameButtonHandler = handleNewGameButton;
        this.loadGameButtonHandler = handleLoadGameButton;
        this.scoresButtonHandler = handleScoresButton;
        this.exitButtonHandler = handleExitButton;
    }

    @Override
    @NotNull
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
