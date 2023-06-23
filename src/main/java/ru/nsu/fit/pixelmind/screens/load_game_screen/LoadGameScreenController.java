package ru.nsu.fit.pixelmind.screens.load_game_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.common.BackToMainMenuListener;

public class LoadGameScreenController implements BackToMainMenuListener, ScreenController {
    private final Builder<Region> viewBuilder;
    private final MainController sceneManager;

    public LoadGameScreenController(MainController sceneManager) {
        viewBuilder = new LoadGameScreenViewBuilder(this::handleBackToMainMenu);
        this.sceneManager = sceneManager;
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
