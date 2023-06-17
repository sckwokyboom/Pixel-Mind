package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class LoadingResourcesModel {
    private final BooleanProperty isFinishedLoadingResources = new SimpleBooleanProperty();

    public BooleanProperty isFinishedLoadingResourcesProperty() {
        return isFinishedLoadingResources;
    }
}
