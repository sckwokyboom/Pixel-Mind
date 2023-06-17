package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.SceneManager;

public class NewGameScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;

    public NewGameScreenController(SceneManager sceneManager) {
        NewGameScreenModel model = new NewGameScreenModel();
        viewBuilder = new NewGameScreenViewBuilder(model, this::handleBackToMainMenuButtonClicked, this::handleStartButtonClicked);
        this.sceneManager = sceneManager;
    }

    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }

    public void handleStartButtonClicked() {
        sceneManager.switchToGameScene();
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public Scene getScene() {
        return new Scene(getView(), 512, 512);
    }
}
