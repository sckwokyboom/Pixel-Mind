package ru.nsu.fit.pixelmind.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.hero.HeroController;
import ru.nsu.fit.pixelmind.tilemap.GameFieldController;

public class GameController {
    private final Builder<Region> viewBuilder;
    private final HeroController heroController;
    private final CameraController cameraController;
    private final GameFieldController gameFieldController;

    public GameController(StringProperty heroType, BooleanProperty newGameStarted) {
        heroController = new HeroController();
        gameFieldController = new GameFieldController();
        cameraController = new CameraController(gameFieldController, heroController);
        GameModel model = new GameModel(heroType, newGameStarted, heroController);

        viewBuilder = new GameViewBuilder(gameFieldController, heroController, cameraController);
    }

    public Region getView() {
        return viewBuilder.build();
    }
    public void updateWorld() {

    }
}
