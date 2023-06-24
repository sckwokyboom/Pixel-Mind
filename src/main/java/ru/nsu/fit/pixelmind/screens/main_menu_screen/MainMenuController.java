package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.common.MenuListener;


public class MainMenuController implements ScreenController, MenuListener {
    private final MainController sceneManager;
    private final MainMenuViewBuilder mainMenuViewBuilder;

    public MainMenuController(MainController sceneManager) {
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
    public Region getView() {
        return mainMenuViewBuilder.build();
    }
}
