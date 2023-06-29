package ru.nsu.fit.pixelmind.screens.game_end_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;

public class GameEndScreenController implements BackToMainMenuListener, ScreenController {
    @NotNull
    private final Builder<Region> viewBuilder;
    @NotNull
    private final MainController sceneManager;
    @NotNull
    private final GameEndScreenModel model;

    public GameEndScreenController(@NotNull MainController sceneManager) {
        model = new GameEndScreenModel();
        viewBuilder = new GameEndScreenView(this::handleBackToMainMenu, model.gameScoreProperty(), model.gameResultProperty());
        this.sceneManager = sceneManager;
    }

    @Override
    @NotNull
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
