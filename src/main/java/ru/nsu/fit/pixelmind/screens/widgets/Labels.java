package ru.nsu.fit.pixelmind.screens.widgets;

import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.Label;

public class Labels {
    public static Label styled(String textValue, String styleClass) {
        Label results = new Label(textValue);
        results.getStyleClass().add(styleClass);
        return results;
    }

    public static Label styled(ObservableStringValue textValue, String styleClass) {
        Label results = styled("", styleClass);
        results.textProperty().bind(textValue);
        return results;
    }

    public static Label h3(ObservableStringValue textValue) {
        return styled(textValue, "label-h3");
    }

    public static Label h4(ObservableStringValue textValue) {
        return styled(textValue, "label-h3");
    }

    public static Label h4(String textValue) {
        return styled(textValue, "label-h4");
    }
}
