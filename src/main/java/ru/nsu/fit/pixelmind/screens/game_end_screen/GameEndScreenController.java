package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;

public class GameEndScreenController implements BackToMainMenuListener, ScreenController {
    private final Builder<Region> viewBuilder;
    private final MainController sceneManager;
    private final GameEndScreenModel model;

    public GameEndScreenController(MainController sceneManager) {
        model = new GameEndScreenModel();
        viewBuilder = new GameEndScreenViewBuilder(this::handleBackToMainMenu, model.gameScoreProperty(), model.gameResultProperty());
        this.sceneManager = sceneManager;
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    public void setScore(int score) {
        model.gameScoreProperty().set(score);

    }

    public void setGameResult(String gameResult) {
        model.gameResultProperty().set(gameResult);
    }

    @Override
    public void handleBackToMainMenu() {
        sceneManager.switchToMainMenuScene();
    }
}
