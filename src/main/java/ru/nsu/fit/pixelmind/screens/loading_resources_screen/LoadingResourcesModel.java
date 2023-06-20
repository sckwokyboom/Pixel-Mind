package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;

public class LoadingResourcesModel {
    // CR: just boolean
    private final BooleanProperty isSetup = new SimpleBooleanProperty(false);
    private Resources resources;
    private GameSessionConfig gameSessionConfig;

    public Resources resources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public BooleanProperty isSetupProperty() {
        return isSetup;
    }

    public GameSessionConfig getGameSessionConfig() {
        return gameSessionConfig;
    }

    public void setGameSessionConfig(GameSessionConfig gameSessionConfig) {
        this.gameSessionConfig = gameSessionConfig;
    }
}
