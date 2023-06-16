package ru.nsu.fit.pixelmind.screens.load_game_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.SceneManager;

public class LoadGameScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;

    public LoadGameScreenController(SceneManager sceneManager) {
        viewBuilder = new LoadGameScreenViewBuilder(this::handleBackToMainMenuButtonClicked);
        this.sceneManager = sceneManager;
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }
}
