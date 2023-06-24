package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;

public class ScoresController implements BackToMainMenuListener, ScreenController {
    private final Builder<Region> viewBuilder;
    private final MainController mainController;
    private final ScoresModel scoresModel;
    private final ScoresInteractor scoresInteractor;

    public ScoresController(MainController mainController) {
        scoresModel = new ScoresModel();
        scoresInteractor = new ScoresInteractor(scoresModel);
        viewBuilder = new ScoresViewBuilder(scoresModel, this::handleBackToMainMenu);
        this.mainController = mainController;
    }

    @Override
    public void handleBackToMainMenu() {
        mainController.switchToMainMenuScene();
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    public void dumpScores() {
        scoresInteractor.dumpScores();
    }

    public void addNewScore(CharacterType heroType, int score) {
        scoresInteractor.addScore(heroType, score);
    }

    public void loadScores() {
        scoresInteractor.loadScoresFromFile();
    }

}
