package ru.nsu.fit.pixelmind.camera;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CameraModel {
    private final IntegerProperty heroX = new SimpleIntegerProperty();
    private final IntegerProperty heroY = new SimpleIntegerProperty();
    //    private final DoubleProperty centerY = new SimpleDoubleProperty();
//    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final BooleanProperty isAnimationEnd = new SimpleBooleanProperty(true);
    private final BooleanProperty isNeedToUpdateCenter = new SimpleBooleanProperty(false);
    private final IntegerProperty tileUserClickedXIndex = new SimpleIntegerProperty();
    private final IntegerProperty tileUserClickedYIndex = new SimpleIntegerProperty();
    private final BooleanProperty targetTileUpdate = new SimpleBooleanProperty(false);
    private final BooleanProperty isNeedToUpdateTheWorld = new SimpleBooleanProperty(false);
    private final BooleanProperty isNeedToUpdateHeroPosition = new SimpleBooleanProperty(false);
    private final BooleanProperty readyToNextTile = new SimpleBooleanProperty(false);

    public BooleanProperty readyToNextTile() {
        return readyToNextTile;
    }


//    public DoubleProperty centerX() {
//        return centerX;
//    }
//
//    public DoubleProperty centerY() {
//        return centerY;
//    }

    public BooleanProperty isNeedToUpdateCenter() {
        return isNeedToUpdateCenter;
    }

    public IntegerProperty tileUserClickedXIndex() {
        return tileUserClickedXIndex;
    }

    public IntegerProperty tileUserClickedYIndex() {
        return tileUserClickedYIndex;
    }

    public BooleanProperty targetTileUpdate() {
        return targetTileUpdate;
    }

    public BooleanProperty isNeedToUpdateTheWorld() {
        return isNeedToUpdateTheWorld;
    }

    public BooleanProperty isNeedToUpdateHeroPosition() {
        return isNeedToUpdateHeroPosition;
    }

    public BooleanProperty isAnimationEndProperty() {
        return isAnimationEnd;
    }
}
