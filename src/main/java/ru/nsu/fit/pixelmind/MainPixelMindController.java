package ru.nsu.fit.pixelmind;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.game.GameController;
import ru.nsu.fit.pixelmind.loadGameScreen.LoadGameScreenController;
import ru.nsu.fit.pixelmind.newGameScreen.NewGameScreenController;
import ru.nsu.fit.pixelmind.scores.ScoresController;

public class MainPixelMindController {
    private final Builder<Region> viewBuilder;

    public MainPixelMindController() {
        MainPixelMindModel model = new MainPixelMindModel();
        NewGameScreenController newGameScreenController = new NewGameScreenController(model.getHeroTypeSelected(), model.getNewGameStarted());
        GameController gameController = new GameController(model.getHeroTypeSelected(), model.getNewGameStarted());
        viewBuilder = new MainPixelMindViewBuilder(
                model,
                gameController.getView(),
                newGameScreenController.getView(),
                new LoadGameScreenController().getView(),
                new ScoresController().getView());
    }

    public Region getView() {
        return viewBuilder.build();
    }
}
