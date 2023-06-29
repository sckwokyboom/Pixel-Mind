package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.ScreenController;

import java.util.List;

public class LoadingResourcesController implements ScreenController {
    @NotNull
    private final Builder<Region> viewBuilder;
    @NotNull
    private final LoadingResourcesModel model;

    public LoadingResourcesController() {
        this.model = new LoadingResourcesModel();
        viewBuilder = new LoadingResourcesView();
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

    @NotNull
    public GameSessionConfig gameSessionConfig() {
        setupGameSessionConfig();
        return model.gameSessionConfig();
    }

    @NotNull
    public List<SavedSessionEntry> savedSessionEntries() {
        setupSavedSessionEntries();
        return model.savedSessionEntries();
    }

    private void setupResources() {
        if (!model.isResourcesSetup()) {
            model.setResources(LoadingResourcesInteractor.parseResources());
            model.setResourcesSetup(true);
            System.out.println("Loaded resources");
        }
    }

    private void setupGameSessionConfig() {
        if (!model.isGameSessionConfigSetup()) {
            model.setGameSessionConfig(LoadingResourcesInteractor.parseGameSessionConfig());
            model.setGameSessionConfigSetup(true);
            System.out.println("Loaded game session config");
        }
    }

    private void setupSavedSessionEntries() {
        if (!model.isSavedSessionEntriesSetup()) {
            model.setSavedSessionEntries(LoadingResourcesInteractor.parseSavesSessionsEntries());
            model.setSavedSessionEntriesSetup(true);
            System.out.println("Loaded saves sessions entries");
        }
    }
}
