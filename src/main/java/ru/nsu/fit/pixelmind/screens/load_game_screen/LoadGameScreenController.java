package ru.nsu.fit.pixelmind.screens.load_game_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.ScreenController;

public class LoadGameScreenController implements ScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;

    public LoadGameScreenController(SceneManager sceneManager) {
        viewBuilder = new LoadGameScreenViewBuilder(this::handleBackToMainMenuButtonClicked);
        this.sceneManager = sceneManager;
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }
}
