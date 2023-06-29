package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;

import java.util.List;

public class LoadingResourcesModel {
    private boolean isResourcesSetup;
    private boolean isGameSessionConfigSetup;
    private boolean isSavedSessionEntriesSetup;
    private Resources resources;
    private GameSessionConfig gameSessionConfig;
    private List<SavedSessionEntry> savedSessionEntries;

    @NotNull
    public Resources resources() {
        return resources;
    }

    public void setResources(@NotNull Resources resources) {
        this.resources = resources;
    }

    public boolean isResourcesSetup() {
        return isResourcesSetup;
    }

    public GameSessionConfig gameSessionConfig() {
        return gameSessionConfig;
    }

    public void setGameSessionConfig(@NotNull GameSessionConfig gameSessionConfig) {
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

    public boolean isSavedSessionEntriesSetup() {
        return isSavedSessionEntriesSetup;
    }

    public void setSavedSessionEntriesSetup(boolean savesEntriesSetup) {
        isSavedSessionEntriesSetup = savesEntriesSetup;
    }

    public List<SavedSessionEntry> savedSessionEntries() {
        return savedSessionEntries;
    }

    public void setSavedSessionEntries(@NotNull List<SavedSessionEntry> savedSessionEntries) {
        this.savedSessionEntries = savedSessionEntries;
    }
}
