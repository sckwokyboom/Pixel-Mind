package ru.nsu.fit.pixelmind.screens.load_game_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.GameSession;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.util.List;

public class LoadGameScreenModel {
    private List<GameSession> savesSessionsEntries;
    private List<SavedSessionEntry> savedSessionEntriesInfo;

    public List<GameSession> savesSessionsEntries() {
        return savesSessionsEntries;
    }

    public void setSavesGameSessions(@NotNull List<GameSession> savesSessionsEntries) {
        this.savesSessionsEntries = savesSessionsEntries;
    }

    public List<SavedSessionEntry> savedSessionEntriesInfo() {
        return savedSessionEntriesInfo;
    }

    public void setSavedSessionEntriesInfo(List<SavedSessionEntry> savedSessionEntriesInfo) {
        this.savedSessionEntriesInfo = savedSessionEntriesInfo;
    }
}
