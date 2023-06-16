package ru.nsu.fit.pixelmind.camera;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.game.GameModel;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;

public class CameraController {
    private final CameraViewBuilder cameraView;
    private final CameraModel cameraModel;
    private final GameModel gameModel;

    public CameraController(GameFieldController gameFieldController, GameModel gameModel) {
        this.cameraModel = new CameraModel();
        this.gameModel = gameModel;
//        cameraModel.heroX().bind(heroController.getTileIndexXProperty());
//        cameraModel.heroY().bind(heroController.getTileIndexYProperty());
//        cameraModel.tileUserClickedXIndex().addListener((observable, oldValue, newValue) -> {
//            cameraModel.isNeedToUpdateCenter().set(true);
//        });
//        cameraModel.tileUserClickedYIndex().addListener((observable, oldValue, newValue) -> {
//            cameraModel.isNeedToUpdateCenter().set(true);
//        });
        cameraView = new CameraViewBuilder(gameFieldController, gameModel, cameraModel);
    }

    public Region getView() {
        return cameraView.build();
    }

    public BooleanProperty getWasUpdatedTileTargetProperty() {
        return cameraModel.targetTileUpdate();
    }

    public int getTileUserClickedXIndex() {
        return cameraModel.tileUserClickedXIndex().get();
    }

    public int getTileUserClickedYIndex() {
        return cameraModel.tileUserClickedYIndex().get();
    }

    public BooleanProperty getIsNeedToUpdateWorld() {
        return cameraModel.isNeedToUpdateTheWorld();
    }

    public BooleanProperty getIsNeedToUpdateHeroPosition() {
        return cameraModel.isNeedToUpdateHeroPosition();
    }

//    public void drawHeroRoute(List<Pair<Integer, Integer>> heroRoute) {
//    }

    public void animateNextStep(Pair<Integer, Integer> tileTargetCoordinate, Runnable callback) {
        cameraView.animateNextStep(tileTargetCoordinate, callback);
    }

    public BooleanProperty getReadyToNextTile() {
        return cameraModel.readyToNextTile();
    }

    public void animateHeroAttack() {
        cameraView.animateHeroAttack();
    }

    public BooleanProperty isAnimationEndProperty() {
        return cameraModel.isAnimationEndProperty();
    }

    public void animateEnemiesAttack() {
        cameraView.animateEnemiesAttack();
    }
}
