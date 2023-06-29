package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.MenuListener;
import ru.nsu.fit.pixelmind.screens.ScreenController;


public class MainMenuController implements ScreenController, MenuListener {
    @NotNull
    private final MainController sceneManager;
    @NotNull
    private final MainMenuViewBuilder mainMenuViewBuilder;

    public MainMenuController(@NotNull MainController sceneManager) {
        mainMenuViewBuilder = new MainMenuViewBuilder(this::handleNewGameButtonClicked, this::handleLoadGameButtonClicked, this::handleHighScoresButtonClicked, this::handleExitButtonClicked);
        this.sceneManager = sceneManager;
    }

    @Override
    public void handleNewGameButtonClicked() {
        sceneManager.switchToNewGameScene();
    }

    @Override
    public void handleLoadGameButtonClicked() {
        sceneManager.switchToLoadGameScene();
    }

    @Override
    public void handleHighScoresButtonClicked() {
        sceneManager.switchToHighScoresScene();
    }

    @Override
    public void handleExitButtonClicked() {
        sceneManager.exit();
    }

    @Override
    @NotNull
    public Region getView() {
        return mainMenuViewBuilder.build();
    }
}
