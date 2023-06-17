package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.SceneManager;

public class GameEndScreenController {

    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;
    private final GameEndScreenModel model;

    public GameEndScreenController(SceneManager sceneManager) {
        model = new GameEndScreenModel();
        viewBuilder = new GameEndScreenViewBuilder(this::handleBackToMainMenuButtonClicked, model.gameScoreProperty(), model.gameResultProperty());
        this.sceneManager = sceneManager;
    }

    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }


    public Region getView() {
        return viewBuilder.build();
    }

    public void setScore(int score) {
        model.gameScoreProperty().set(score);
    }
    public void setGameResult(String gameResult) {
        model.gameResultProperty().set(gameResult);
    }
}
