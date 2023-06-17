package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.screens.SceneManager;

public class LoadingResourcesScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;
    private final LoadingResourcesModel model;
    private final LoadingResourcesInteractor interactor;
    private boolean isSetup;

    public LoadingResourcesScreenController(SceneManager sceneManager, LoadingResourcesModel model, LoadingResourcesInteractor interactor) {
        this.model = model;
        viewBuilder = new LoadingResourcesViewBuilder(model, this::handleFinishOfLoading);
        this.sceneManager = sceneManager;
        this.interactor = interactor;
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public void handleFinishOfLoading() {
        sceneManager.switchToMainMenuScene();
    }

    public Scene getScene() {
        if (!isSetup) {
            interactor.parseResources();
            isSetup = true;
        }
        return new Scene(getView(), 512, 512);
    }
}
