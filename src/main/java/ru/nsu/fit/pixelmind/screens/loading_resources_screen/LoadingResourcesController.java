package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.ScreenController;

public class LoadingResourcesController implements ScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;
    private final LoadingResourcesModel model;
    private final LoadingResourcesInteractor interactor;


    public LoadingResourcesController(@NotNull SceneManager sceneManager) {
        this.model = new LoadingResourcesModel();
        viewBuilder = new LoadingResourcesViewBuilder(model);
        this.sceneManager = sceneManager;
        this.interactor = new LoadingResourcesInteractor(model);
    }

    @Override
    @NotNull

    public Region getView() {
        return viewBuilder.build();
    }

//    public void handleFinishOfLoading() {
////        sceneManager.switchToGameScene();
//    }

    @NotNull
    public Resources resources() {
        if (!model.isSetupProperty().get()) {
            model.setResources(interactor.parseResources());
            model.isSetupProperty().set(true);
            System.out.println("Load resources");
        }
        return model.resources();
    }

    @NotNull
    public GameSessionConfig gameSessionConfig() {
        model.setGameSessionConfig(LoadingResourcesInteractor.parseGameSessionConfig());
        return model.getGameSessionConfig();
    }
}
