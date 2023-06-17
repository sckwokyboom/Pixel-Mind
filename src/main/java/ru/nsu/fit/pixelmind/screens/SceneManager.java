package ru.nsu.fit.pixelmind.screens;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fit.pixelmind.game.GameController;
import ru.nsu.fit.pixelmind.screens.game_end_screen.GameEndScreenController;
import ru.nsu.fit.pixelmind.screens.load_game_screen.LoadGameScreenController;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.LoadingResourcesScreenController;
import ru.nsu.fit.pixelmind.screens.main_menu_screen.MainMenuController;
import ru.nsu.fit.pixelmind.screens.new_game_screen.NewGameScreenController;
import ru.nsu.fit.pixelmind.screens.scores_screen.ScoresController;

public class SceneManager {
    private Stage primaryStage;
    private final NewGameScreenController newGameScreenController;
    private final LoadGameScreenController loadGameScreenController;
    private final ScoresController scoresController;
    private final GameController gameController;
    private final MainMenuController mainMenuController;
    private final GameEndScreenController gameEndScreenController;
    private final LoadingResourcesScreenController loadingResourcesScreenController;

    private final Scene mainMenuScene;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        newGameScreenController = new NewGameScreenController(this);
        loadGameScreenController = new LoadGameScreenController(this);
        scoresController = new ScoresController(this);
        gameController = new GameController(this);
        mainMenuController = new MainMenuController(this);
        gameEndScreenController = new GameEndScreenController(this);
        loadingResourcesScreenController = new LoadingResourcesScreenController(this, model, interactor);
        mainMenuScene = new Scene(mainMenuController.getView(), 512, 512);
    }

    public void switchToNewGameScene() {
        Scene newGameScene = newGameScreenController.getScene();

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
//        Scene mainMenuScene = new Scene(mainMenuController.getView(), 512, 512);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public void switchToGameScene() {
        gameController.launchGameSession();
        Scene gameScene = new Scene(gameController.getView(), 512, 512);

        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public void switchToGameEndScene(String gameResult, int gameScore) {
        gameEndScreenController.setScore(gameScore);
        gameEndScreenController.setGameResult(gameResult);
        Scene gameEndScene = new Scene(gameEndScreenController.getView(), 512, 512);

        primaryStage.setScene(gameEndScene);
        primaryStage.show();
    }

    public void switchToLoadingResourcesScreen() {
        Scene loadingResourcesScene = loadingResourcesScreenController.getScene();

        primaryStage.setScene(loadingResourcesScene);
        primaryStage.show();
    }

    public void exit() {
        primaryStage.close();
    }

}

