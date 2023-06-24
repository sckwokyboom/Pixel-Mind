package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.ScreenController;

public class LoadingResourcesController implements ScreenController {
    private final Builder<Region> viewBuilder;
    private final LoadingResourcesModel model;


    public LoadingResourcesController() {
        this.model = new LoadingResourcesModel();
        viewBuilder = new LoadingResourcesViewBuilder();
    }

    @Override
    @NotNull
    public Region getView() {
        return viewBuilder.build();
    }

    @NotNull
    public Resources resources() {
        setupResources();
        return model.resources();
    }

    private void setupResources() {
        if (!model.isResourcesSetup()) {
            model.setResources(LoadingResourcesInteractor.parseResources());
            model.setResourcesSetup(true);
            System.out.println("Load resources");
        }
    }

    private void setupGameSessionConfig() {
        if (!model.isGameSessionConfigSetup()) {
            model.setGameSessionConfig(LoadingResourcesInteractor.parseGameSessionConfig());
            model.setGameSessionConfigSetup(true);
            System.out.println("Load game session config");
        }
    }

    @NotNull
    public GameSessionConfig gameSessionConfig() {
        setupGameSessionConfig();
        return model.getGameSessionConfig();
    }
}
