package ru.nsu.fit.pixelmind.screens.load_game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.util.List;

public class LoadGameScreenModel {
    private List<SavedSessionEntry> savedSessionEntriesInfo;

    public List<SavedSessionEntry> savedSessionEntriesInfo() {
        return savedSessionEntriesInfo;
    }

    public void setSavedSessionEntriesInfo(@NotNull List<SavedSessionEntry> savedSessionEntriesInfo) {
        this.savedSessionEntriesInfo = savedSessionEntriesInfo;
    }
}
