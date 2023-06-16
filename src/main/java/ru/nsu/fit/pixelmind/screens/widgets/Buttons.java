package ru.nsu.fit.pixelmind.screens.widgets;

import javafx.scene.control.Button;

public class Buttons {
    public static Button button(String text, Runnable handler) {
        Button button = new Button(text);
        button.setOnAction(event -> {
            handler.run();
        });
        return button;
    }
}
