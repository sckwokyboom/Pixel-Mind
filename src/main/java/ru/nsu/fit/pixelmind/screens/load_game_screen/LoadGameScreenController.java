package ru.nsu.fit.pixelmind.screens.load_game_screen;

import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.util.List;

public class LoadGameScreenController implements BackToMainMenuListener, ScreenController {
    private final Builder<Region> viewBuilder;
    private final MainController sceneManager;
    private final LoadGameScreenModel model;

    public LoadGameScreenController(MainController sceneManager) {
        model = new LoadGameScreenModel();
        viewBuilder = new LoadGameScreenViewBuilder(model, this::handleBackToMainMenu, this::handleSelectedSave);
        this.sceneManager = sceneManager;
    }

    @Override
    public @NotNull Region getView() {
        return viewBuilder.build();
    }

    @Override
    public void handleBackToMainMenu() {
        sceneManager.switchToMainMenuScene();
    }

    public void setSavedGameSessionEntriesInfo(List<SavedSessionEntry> savedSessionEntry) {
        model.setSavedSessionEntriesInfo(savedSessionEntry);
    }

    public void handleSelectedSave(SavedSessionEntry selectedSave) {
        sceneManager.loadGame(selectedSave);
    }
}
