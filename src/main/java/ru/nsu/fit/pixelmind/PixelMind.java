package ru.nsu.fit.pixelmind;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PixelMind extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pixel Mind");
        Scene scene = new Scene(new MainPixelMindController().getView(), 512, 512);
        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}