package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.SceneManager;

public class ScoresController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;

    public ScoresController(SceneManager sceneManager) {
        viewBuilder = new ScoresViewBuilder(this::handleBackToMainMenuButtonClicked);
        this.sceneManager = sceneManager;
    }
    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
