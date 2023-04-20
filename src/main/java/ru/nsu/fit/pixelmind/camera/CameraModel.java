package ru.nsu.fit.pixelmind.camera;

import javafx.beans.property.*;

public class CameraModel {
    private final IntegerProperty heroX = new SimpleIntegerProperty();
    private final IntegerProperty heroY = new SimpleIntegerProperty();
    private final DoubleProperty centerY = new SimpleDoubleProperty();
    private final DoubleProperty centerX = new SimpleDoubleProperty();
    private final BooleanProperty isNeedToUpdateCenter = new SimpleBooleanProperty(false);
    private final IntegerProperty tileUserClickedXIndex = new SimpleIntegerProperty();
    private final IntegerProperty tileUserClickedYIndex = new SimpleIntegerProperty();

    public IntegerProperty heroX() {
        return heroX;
    }

    public IntegerProperty heroY() {
        return heroY;
    }

    public DoubleProperty centerX() {
        return centerX;
    }

    public DoubleProperty centerY() {
        return centerY;
    }

    public BooleanProperty isNeedToUpdateCenter() {
        return isNeedToUpdateCenter;
    }

    public IntegerProperty tileUserClickedXIndex() {
        return tileUserClickedXIndex;
    }

    public IntegerProperty tileUserClickedYIndex() {
        return tileUserClickedYIndex;
    }
}
