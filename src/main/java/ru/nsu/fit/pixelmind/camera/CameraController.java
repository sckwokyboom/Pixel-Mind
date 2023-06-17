package ru.nsu.fit.pixelmind.camera;

import javafx.scene.layout.Region;
import ru.nsu.fit.pixelmind.screens.game.GameModel;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.function.Consumer;

public class CameraController {
    private final CameraViewBuilder cameraView;
    private final CameraModel cameraModel;

    public CameraController(GameModel gameModel, Consumer<TileIndexCoordinates> tileClickedHandler) {
        this.cameraModel = new CameraModel();
        cameraView = new CameraViewBuilder(gameModel, tileClickedHandler);
    }

    public Region getView() {
        return cameraView.build();
    }
}
