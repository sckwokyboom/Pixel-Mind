package ru.nsu.fit.pixelmind.camera;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.hero.HeroController;
import ru.nsu.fit.pixelmind.tilemap.GameFieldController;

public class CameraController {
    private final Builder<Region> cameraView;

    public CameraController(GameFieldController gameFieldController, HeroController heroController) {
        CameraModel cameraModel = new CameraModel();
        cameraModel.heroX().bind(heroController.getXProperty());
        cameraModel.heroY().bind(heroController.getYProperty());
        cameraModel.tileUserClickedXIndex().addListener((observable, oldValue, newValue) -> {
            System.out.println("Changed!");
            cameraModel.isNeedToUpdateCenter().set(true);
        });
        cameraModel.tileUserClickedYIndex().addListener((observable, oldValue, newValue) -> {
            System.out.println("Changed!");
            cameraModel.isNeedToUpdateCenter().set(true);
        });
        cameraView = new CameraViewBuilder(gameFieldController, heroController, cameraModel);
    }

    public Region getView() {
        return cameraView.build();
    }
}
