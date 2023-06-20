package ru.nsu.fit.pixelmind.screens;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.game_screen.GameController;
import ru.nsu.fit.pixelmind.screens.game_end_screen.GameEndScreenController;
import ru.nsu.fit.pixelmind.screens.load_game_screen.LoadGameScreenController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.LoadingResourcesController;
import ru.nsu.fit.pixelmind.screens.main_menu_screen.MainMenuController;
import ru.nsu.fit.pixelmind.screens.new_game_screen.NewGameScreenController;
import ru.nsu.fit.pixelmind.screens.scores_screen.ScoresController;
import ru.nsu.fit.pixelmind.screens.scores_screen.ScoresInteractor;

public class SceneManager {
    private final Stage primaryStage;
    private final NewGameScreenController newGameScreenController;
    private final LoadGameScreenController loadGameScreenController;
    private final ScoresController scoresController;
    private final GameController gameController;
    private final MainMenuController mainMenuController;
    private final GameEndScreenController gameEndScreenController;
    private final LoadingResourcesController loadingResourcesScreenController;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadingResourcesScreenController = new LoadingResourcesController(this);
        GameEndSceneHandler gameEndSceneHandler = new GameEndSceneSwitcher();
        gameController = new GameController(gameEndSceneHandler);
        newGameScreenController = new NewGameScreenController(this, loadingResourcesScreenController::resources, loadingResourcesScreenController::gameSessionConfig, gameController::launchGameSession);
        loadGameScreenController = new LoadGameScreenController(this);
        scoresController = new ScoresController(this);
        mainMenuController = new MainMenuController(this);
        gameEndScreenController = new GameEndScreenController(this);
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

    public void switchToMainMenuScene() {
        Scene mainMenuScene = new Scene(mainMenuController.getView(), 512, 512);

        primaryStage.setScene(mainMenuScene);
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
        System.out.println("Set loading scene");
    }


    public void exit() {
        primaryStage.close();
    }

    public interface GameEndSceneHandler {
        void switchToGameEndScene(@NotNull String gameResult, int gameScore);
    }

    public class GameEndSceneSwitcher implements GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, int gameScore) {
            gameEndScreenController.setGameResult(gameResult);
            gameEndScreenController.setScore(gameScore);
            ScoresInteractor.addScoreToTable(gameScore);
            Scene gameEndScene = new Scene(gameEndScreenController.getView(), 512, 512);
            primaryStage.setScene(gameEndScene);
            primaryStage.show();
        }

    }

}

