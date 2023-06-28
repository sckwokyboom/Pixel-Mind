package ru.nsu.fit.pixelmind.screens;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.screens.game.GameController;
import ru.nsu.fit.pixelmind.screens.game.GameSession;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game_end_screen.GameEndScreenController;
import ru.nsu.fit.pixelmind.screens.load_game_screen.LoadGameScreenController;
import ru.nsu.fit.pixelmind.screens.load_game_screen.LoadGameScreenInteractor;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.LoadingResourcesController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.LoadingResourcesInteractor;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;
import ru.nsu.fit.pixelmind.screens.main_menu_screen.MainMenuController;
import ru.nsu.fit.pixelmind.screens.new_game_screen.NewGameScreenController;
import ru.nsu.fit.pixelmind.screens.new_game_screen.UserModifications;
import ru.nsu.fit.pixelmind.screens.scores_screen.ScoresController;

public class MainController {
    private final Stage primaryStage;
    private final NewGameScreenController newGameScreenController;
    private final LoadGameScreenController loadGameScreenController;
    private final ScoresController scoresController;
    private final GameController gameController;
    private final MainMenuController mainMenuController;
    private final GameEndScreenController gameEndScreenController;
    private final LoadingResourcesController loadingResourcesScreenController;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadingResourcesScreenController = new LoadingResourcesController();
        gameController = new GameController(new GameEndSceneSwitcher());
        newGameScreenController = new NewGameScreenController(this);
        loadGameScreenController = new LoadGameScreenController(this);
        scoresController = new ScoresController(this);
        mainMenuController = new MainMenuController(this);
        gameEndScreenController = new GameEndScreenController(this);
        this.primaryStage.setOnCloseRequest(event -> {
            scoresController.dumpScores();
            gameController.saveGameSession();
            gameController.dumpNewSaves();
        });
    }


    public void init() {
        switchToLoadingResourcesScreen();
        new Thread(() -> {
            Resources resources = loadingResourcesScreenController.resources();
            newGameScreenController.setAvatars(resources.avatars());
            scoresController.loadScores();
            gameController.setResources(resources);
            loadGameScreenController.setSavedGameSessionEntriesInfo(LoadingResourcesInteractor.parseSavesSessionsEntries());
            Platform.runLater(this::switchToMainMenuScene);
        }).start();
    }

    public void runGame() {
        switchToLoadingResourcesScreen();
        new Thread(() -> {
            GameSessionConfig gameSessionConfig = loadingResourcesScreenController.gameSessionConfig();
            UserModifications userModifications = newGameScreenController.getUserModifications();
            gameController.createGameSession(userModifications, gameSessionConfig);
            Platform.runLater(this::switchToGameScene);
        }).start();
    }

    public void loadGame(SavedSessionEntry savedSessionEntry) {
        switchToLoadingResourcesScreen();
        new Thread(() -> {
            Resources resources = loadingResourcesScreenController.resources();
            GameSession gameSession = LoadGameScreenInteractor.parseGameSessionEntry(savedSessionEntry, resources);
            gameController.launchGameSession(gameSession);
            Platform.runLater(this::switchToGameScene);
        }).start();
    }

    public void switchToMainMenuScene() {
        Scene mainMenuScene = new Scene(mainMenuController.getView(), 512, 512);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public void switchToNewGameScene() {
        Scene newGameScene = new Scene(newGameScreenController.getView(), 512, 512);

        primaryStage.setScene(newGameScene);
        primaryStage.show();
    }

    public void switchToLoadGameScene() {
        Scene loadGameScene = new Scene(loadGameScreenController.getView(), 512, 512);

        primaryStage.setScene(loadGameScene);
        primaryStage.show();
    }

    public void switchToHighScoresScene() {
        Scene highScoresScene = new Scene(scoresController.getView(), 512, 512);

        primaryStage.setScene(highScoresScene);
        primaryStage.show();
    }

    public void switchToGameScene() {
        Scene gameScene = new Scene(gameController.getView(), 512, 512);

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public void switchToLoadingResourcesScreen() {
        Scene loadingResourcesScene = new Scene(loadingResourcesScreenController.getView(), 512, 512);

        primaryStage.setScene(loadingResourcesScene);
        primaryStage.show();
    }

    public void exit() {
        scoresController.dumpScores();
        primaryStage.close();
    }

    public interface GameEndSceneHandler {
        void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore);
    }

    public class GameEndSceneSwitcher implements GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
            gameEndScreenController.setGameResult(gameResult);
            gameEndScreenController.setScore(gameScore);
            scoresController.addNewScore(heroType, gameScore);
            gameController.clearGameSession();
            Scene gameEndScene = new Scene(gameEndScreenController.getView(), 512, 512);
            primaryStage.setScene(gameEndScene);
            primaryStage.show();
        }

    }

}

