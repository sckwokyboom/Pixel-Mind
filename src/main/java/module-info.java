module ru.nsu.fit.pixelmind {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.jetbrains.annotations;
    requires org.apache.commons.csv;

    opens ru.nsu.fit.pixelmind to javafx.fxml;
    exports ru.nsu.fit.pixelmind;
    exports ru.nsu.fit.pixelmind.screens.game;
    opens ru.nsu.fit.pixelmind.screens.game to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.load_game_screen;
    opens ru.nsu.fit.pixelmind.screens.load_game_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.new_game_screen;
    opens ru.nsu.fit.pixelmind.screens.new_game_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.scores_screen;
    opens ru.nsu.fit.pixelmind.screens.scores_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.game_field;
    opens ru.nsu.fit.pixelmind.game_field to javafx.fxml;
    exports ru.nsu.fit.pixelmind.game_field.tile;
    opens ru.nsu.fit.pixelmind.game_field.tile to javafx.fxml;
    exports ru.nsu.fit.pixelmind.exceptions;
    opens ru.nsu.fit.pixelmind.exceptions to javafx.fxml;
    exports ru.nsu.fit.pixelmind.utils;
    opens ru.nsu.fit.pixelmind.utils to javafx.fxml;
    exports ru.nsu.fit.pixelmind.characters;
    opens ru.nsu.fit.pixelmind.characters to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens;
    opens ru.nsu.fit.pixelmind.screens to javafx.fxml;
    exports ru.nsu.fit.pixelmind.screens.loading_resources_screen;
    opens ru.nsu.fit.pixelmind.screens.loading_resources_screen to javafx.fxml;
    exports ru.nsu.fit.pixelmind.config;
    opens ru.nsu.fit.pixelmind.config to javafx.fxml;
    exports ru.nsu.fit.pixelmind.characters.character;
    opens ru.nsu.fit.pixelmind.characters.character to javafx.fxml;
    exports ru.nsu.fit.pixelmind.game_field.tile_map;
    opens ru.nsu.fit.pixelmind.game_field.tile_map to javafx.fxml;
    exports ru.nsu.fit.pixelmind.camera;
    opens ru.nsu.fit.pixelmind.camera to javafx.fxml;
}