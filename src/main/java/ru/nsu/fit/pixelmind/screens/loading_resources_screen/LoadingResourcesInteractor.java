package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

public class LoadingResourcesInteractor {


    private final LoadingResourcesModel model;

    public LoadingResourcesInteractor(LoadingResourcesModel model) {
        this.model = model;
    }

    public void parseResources() {
        // parse Json
        model.isFinishedLoadingResourcesProperty().set(true);
    }

}
