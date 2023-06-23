package ru.nsu.fit.pixelmind.screens.main_menu_screen;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;

// CR:
// interface MenuListener {
//    void handleNewGameButtonClicked();
//}

public class MainMenuController implements ScreenController {
    private final MainController sceneManager;
    private final MainMenuViewBuilder mainMenuViewBuilder;

    public MainMenuController(MainController sceneManager) {
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

    @Override
    public Region getView() {
        return mainMenuViewBuilder.build();
    }
}
