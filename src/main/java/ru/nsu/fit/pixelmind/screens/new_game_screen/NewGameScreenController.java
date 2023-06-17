package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.SceneManager;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class NewGameScreenController {
    private final Builder<Region> viewBuilder;
    private final SceneManager sceneManager;
    private final Supplier<Resources> resourcesGetter;
    private final Supplier<GameSessionConfig> gameSessionConfigGetter;
    private final BiConsumer<Resources, GameSessionConfig> launchGameSession;

    public NewGameScreenController(SceneManager sceneManager, Supplier<Resources> resourcesGetter, @NotNull Supplier<GameSessionConfig> gameSessionConfig, BiConsumer<Resources, GameSessionConfig> launchGameSession) {
        this.gameSessionConfigGetter = gameSessionConfig;
        this.launchGameSession = launchGameSession;
        NewGameScreenModel model = new NewGameScreenModel();
        viewBuilder = new NewGameScreenViewBuilder(model, this::handleBackToMainMenuButtonClicked, this::handleStartButtonClicked);
        this.sceneManager = sceneManager;
        this.resourcesGetter = resourcesGetter;
    }

    public void handleBackToMainMenuButtonClicked() {
        sceneManager.switchToMainMenuScene();
    }

    public void handleStartButtonClicked() {
        sceneManager.switchToLoadingResourcesScreen();
        Resources resources = resourcesGetter.get();
        GameSessionConfig gameSessionConfig = gameSessionConfigGetter.get();
        launchGameSession.accept(resources, gameSessionConfig);
        sceneManager.switchToGameScene();
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public Scene getScene() {
        return new Scene(getView(), 512, 512);
    }
}
