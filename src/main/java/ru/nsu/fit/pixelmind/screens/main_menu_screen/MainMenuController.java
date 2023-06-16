package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.SceneManager;

public class MainMenuController {
    private SceneManager sceneManager;
    private final MainMenuViewBuilder mainMenuViewBuilder;

    public MainMenuController(SceneManager sceneManager) {
        mainMenuViewBuilder = new MainMenuViewBuilder(this::handleNewGameButtonClicked, this::handleLoadGameButtonClicked, this::handleHighScoresButtonClicked, this::handleExitButtonClicked);
        this.sceneManager = sceneManager;
    }

    public void handleNewGameButtonClicked() {
        sceneManager.switchToNewGameScene();
    }

    public void handleLoadGameButtonClicked() {
        sceneManager.switchToLoadGameScene();
    }

    public void handleHighScoresButtonClicked() {
        sceneManager.switchToHighScoresScene();
    }

    public void handleExitButtonClicked() {
        sceneManager.exit();
    }

    public Region getView() {
        return mainMenuViewBuilder.build();
    }
}
