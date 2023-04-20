package ru.nsu.fit.pixelmind.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.hero.HeroController;
import ru.nsu.fit.pixelmind.tilemap.GameFieldController;


public class GameViewBuilder implements Builder<Region> {
    private final Canvas tileMap;
    private final GameFieldController gameFieldController;
    private final HeroController heroController;
    private final CameraController cameraController;


    public GameViewBuilder(GameFieldController gameFieldController, HeroController heroController, CameraController cameraController) {
        this.gameFieldController = gameFieldController;
        this.tileMap = gameFieldController.getView();
        this.heroController = heroController;
        this.cameraController = cameraController;

    }

    @Override
    public Region build() {
        StackPane gameScreen = new StackPane();
        gameScreen.getChildren().add(cameraController.getView());
        gameScreen.setBackground(Background.fill(Color.BLACK));
        return gameScreen;
    }
}
