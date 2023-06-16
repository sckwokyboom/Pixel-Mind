package ru.nsu.fit.pixelmind;

import javafx.application.Application;
import javafx.stage.Stage;

public class PixelMind extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pixel Mind");
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchToMainMenuScene();
    }

    public static void main(String[] args) {
        launch();
    }
}