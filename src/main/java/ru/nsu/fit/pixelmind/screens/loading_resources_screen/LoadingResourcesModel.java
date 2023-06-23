package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;

public class LoadingResourcesModel {
    private boolean isResourcesSetup;
    private boolean isGameSessionConfigSetup;
    private Resources resources;
    private GameSessionConfig gameSessionConfig;

    @NotNull
    public Resources resources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public boolean isResourcesSetup() {
        return isResourcesSetup;
    }

    public GameSessionConfig getGameSessionConfig() {
        return gameSessionConfig;
    }

    public void setGameSessionConfig(GameSessionConfig gameSessionConfig) {
        this.gameSessionConfig = gameSessionConfig;
    }

    public void setResourcesSetup(boolean isSetup) {
        this.isResourcesSetup = isSetup;
    }

    public boolean isGameSessionConfigSetup() {
        return isGameSessionConfigSetup;
    }

    public void setGameSessionConfigSetup(boolean gameSessionConfigSetup) {
        isGameSessionConfigSetup = gameSessionConfigSetup;
    }
}
