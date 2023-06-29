package ru.nsu.fit.pixelmind.screens.game.camera;

import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.GameModel;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;

import java.util.function.Consumer;

public class CameraController {
    @NotNull
    private final CameraViewBuilder cameraView;

    public CameraController(@NotNull GameModel gameModel, @NotNull Consumer<TileIndexCoordinates> tileClickedHandler) {
        cameraView = new CameraViewBuilder(gameModel, tileClickedHandler);
    }

    @NotNull
    public Region getView() {
        return cameraView.build();
    }
}
