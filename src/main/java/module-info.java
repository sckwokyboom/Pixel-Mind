module ru.nsu.fit.pixelmind {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ru.nsu.fit.pixelmind to javafx.fxml;
    exports ru.nsu.fit.pixelmind;
    exports ru.nsu.fit.pixelmind.game;
    opens ru.nsu.fit.pixelmind.game to javafx.fxml;
    exports ru.nsu.fit.pixelmind.loadGameScreen;
    opens ru.nsu.fit.pixelmind.loadGameScreen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.newGameScreen;
    opens ru.nsu.fit.pixelmind.newGameScreen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.scores;
    opens ru.nsu.fit.pixelmind.scores to javafx.fxml;
    exports ru.nsu.fit.pixelmind.tilemap;
    opens ru.nsu.fit.pixelmind.tilemap to javafx.fxml;
    exports ru.nsu.fit.pixelmind.tile;
    opens ru.nsu.fit.pixelmind.tile to javafx.fxml;
    exports ru.nsu.fit.pixelmind.hero;
    opens ru.nsu.fit.pixelmind.hero to javafx.fxml;
    exports ru.nsu.fit.pixelmind.exceptions;
    opens ru.nsu.fit.pixelmind.exceptions to javafx.fxml;
}