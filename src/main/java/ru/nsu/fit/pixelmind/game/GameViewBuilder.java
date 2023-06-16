package ru.nsu.fit.pixelmind.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.camera.CameraController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;


public class GameViewBuilder implements Builder<Region> {
    private Canvas tileMap;
    private final GameFieldController gameFieldController;
//    private final HeroController heroController;
    private final CameraController cameraController;
    private final GameModel gameModel;


    public GameViewBuilder(GameFieldController gameFieldController, CameraController cameraController, GameModel gameModel) {
        this.gameFieldController = gameFieldController;
        this.cameraController = cameraController;
        this.gameModel = gameModel;
    }

    @Override
    public Region build() {
        tileMap = gameFieldController.getView();
        StackPane gameScreen = new StackPane();
        gameScreen.getChildren().add(cameraController.getView());
        gameScreen.setBackground(Background.fill(Color.BLACK));
        return gameScreen;
    }
}
