package ru.nsu.fit.pixelmind;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.fit.pixelmind.screens.MainController;

public class PixelMind extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pixel Mind");
        MainController mainController = new MainController(stage);
        mainController.init();
    }

    public static void main(String[] args) {
        launch();
    }
}