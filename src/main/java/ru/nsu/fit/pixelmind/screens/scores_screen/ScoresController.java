package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.common.BackToMainMenuListener;

public class ScoresController implements BackToMainMenuListener, ScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;

    public ScoresController(SceneManager sceneManager) {
        viewBuilder = new ScoresViewBuilder(this::handleBackToMainMenuButtonClicked);
        this.sceneManager = sceneManager;
    }
    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleBackToMainMenu() {
        sceneManager.switchToMainMenuScene();
    }
}
