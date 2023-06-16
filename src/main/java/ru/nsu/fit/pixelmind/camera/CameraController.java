package ru.nsu.fit.pixelmind.camera;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.game.GameModel;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.function.Consumer;

public class CameraController {
    private final CameraViewBuilder cameraView;
    private final CameraModel cameraModel;
    private final GameModel gameModel;

    public CameraController(GameFieldController gameFieldController, GameModel gameModel, Consumer<TileIndexCoordinates> tileClickedHandler) {
        this.cameraModel = new CameraModel();
        this.gameModel = gameModel;
        cameraView = new CameraViewBuilder(gameFieldController, gameModel, cameraModel, tileClickedHandler);
    }

    public Region getView() {
        return cameraView.build();
    }

//    public BooleanProperty getWasUpdatedTileTargetProperty() {
//        return cameraModel.targetTileUpdate();
//    }

//    public int getTileUserClickedXIndex() {
//        return cameraModel.tileUserClickedXIndex().get();
//    }

//    public int getTileUserClickedYIndex() {
//        return cameraModel.tileUserClickedYIndex().get();
//    }

//    public BooleanProperty getIsNeedToUpdateWorld() {
//        return cameraModel.isNeedToUpdateTheWorld();
//    }

//    public BooleanProperty getIsNeedToUpdateHeroPosition() {
//        return cameraModel.isNeedToUpdateHeroPosition();
//    }

//    public void drawHeroRoute(List<Pair<Integer, Integer>> heroRoute) {
//    }

//    public void animateNextStep(Pair<Integer, Integer> tileTargetCoordinate, Runnable callback) {
//        cameraView.animateNextStep(tileTargetCoordinate, callback);
//    }

//    public BooleanProperty getReadyToNextTile() {
//        return cameraModel.readyToNextTile();
//    }

//    public void animateHeroAttack() {
//        cameraView.animateHeroAttack();
//    }

//    public BooleanProperty isAnimationEndProperty() {
//        return cameraModel.isAnimationEndProperty();
//    }

//    public void animateEnemiesAttack() {
//        cameraView.animateEnemiesAttack();
//    }
}
