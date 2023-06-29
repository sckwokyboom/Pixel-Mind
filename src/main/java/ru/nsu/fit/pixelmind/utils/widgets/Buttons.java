package ru.nsu.fit.pixelmind.utils.widgets;

import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

public class Buttons {
    @NotNull
    public static Button button(@NotNull String text, @NotNull Runnable handler) {
        Button button = new Button(text);
        button.setOnAction(event -> handler.run());
        return button;
    }
}
