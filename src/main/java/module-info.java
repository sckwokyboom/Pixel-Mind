module ru.nsu.fit.pixelmind {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ru.nsu.fit.pixelmind to javafx.fxml;
    exports ru.nsu.fit.pixelmind;
    exports ru.nsu.fit.pixelmind.game;
    opens ru.nsu.fit.pixelmind.game to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.load_game_screen;
    opens ru.nsu.fit.pixelmind.screens.load_game_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.new_game_screen;
    opens ru.nsu.fit.pixelmind.screens.new_game_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.scores_screen;
    opens ru.nsu.fit.pixelmind.screens.scores_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.game_field;
    opens ru.nsu.fit.pixelmind.game_field to javafx.fxml;
    exports ru.nsu.fit.pixelmind.tile;
    opens ru.nsu.fit.pixelmind.tile to javafx.fxml;
    exports ru.nsu.fit.pixelmind.characters.hero;
    opens ru.nsu.fit.pixelmind.characters.hero to javafx.fxml;
    exports ru.nsu.fit.pixelmind.exceptions;
    opens ru.nsu.fit.pixelmind.exceptions to javafx.fxml;
    exports ru.nsu.fit.pixelmind.utils;
    opens ru.nsu.fit.pixelmind.utils to javafx.fxml;
    exports ru.nsu.fit.pixelmind.characters;
    opens ru.nsu.fit.pixelmind.characters to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens;
    opens ru.nsu.fit.pixelmind.screens to javafx.fxml;
}