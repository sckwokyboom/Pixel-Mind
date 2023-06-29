package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

public class ScoresController implements BackToMainMenuListener, ScreenController {
    @NotNull
    private final Builder<Region> viewBuilder;
    @NotNull
    private final MainController mainController;
    @NotNull
    private final ScoresInteractor scoresInteractor;

    public ScoresController(@NotNull MainController mainController) {
        ScoresModel scoresModel = new ScoresModel();
        scoresInteractor = new ScoresInteractor(scoresModel);
        viewBuilder = new ScoresViewBuilder(scoresModel, this::handleBackToMainMenu);
        this.mainController = mainController;
    }

    @Override
    public void handleBackToMainMenu() {
        mainController.switchToMainMenuScene();
    }

    @Override
    @NotNull
    public Region getView() {
        return viewBuilder.build();
    }

    public void dumpScores() {
        scoresInteractor.dumpScores();
    }

    public void addNewScore(@NotNull CharacterType heroType, int score) {
        scoresInteractor.addScore(heroType, score);
    }

    public void loadScores() {
        scoresInteractor.loadScoresFromFile();
    }

}
