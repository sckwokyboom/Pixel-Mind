package ru.nsu.fit.pixelmind.utils.widgets;

import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class Labels {
    @NotNull
    public static Label styled(@NotNull String textValue, @NotNull String styleClass) {
        Label results = new Label(textValue);
        results.getStyleClass().add(styleClass);
        return results;
    }

    @NotNull
    public static Label styled(@NotNull ObservableStringValue textValue, @NotNull String styleClass) {
        Label results = styled("", styleClass);
        results.textProperty().bind(textValue);
        return results;
    }

    @NotNull
    public static Label h3(@NotNull ObservableStringValue textValue) {
        return styled(textValue, "label-h3");
    }

    @NotNull
    public static Label h4(@NotNull ObservableStringValue textValue) {
        return styled(textValue, "label-h3");
    }

    @NotNull
    public static Label h4(@NotNull String textValue) {
        return styled(textValue, "label-h4");
    }
}
