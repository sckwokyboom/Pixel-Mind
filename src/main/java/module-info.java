module ru.nsu.fit.pixelmind {
    requires javafx.controls;
    requires com.google.gson;
    requires org.jetbrains.annotations;
    requires org.apache.commons.csv;
    requires org.apache.logging.log4j;
    requires static lombok;

    exports ru.nsu.fit.pixelmind;
    exports ru.nsu.fit.pixelmind.screens;
    exports ru.nsu.fit.pixelmind.screens.game.character;
    exports ru.nsu.fit.pixelmind.screens.game;
    exports ru.nsu.fit.pixelmind.screens.loading_resources_screen;
    exports ru.nsu.fit.pixelmind.config;
    exports ru.nsu.fit.pixelmind.screens.scores_screen;
    exports ru.nsu.fit.pixelmind.screens.game.game_field.tile;
    exports ru.nsu.fit.pixelmind.screens.game.camera;
    exports ru.nsu.fit.pixelmind.screens.new_game_screen;

    opens ru.nsu.fit.pixelmind.screens.game to com.google.gson, org.apache.logging.log4j;
    opens ru.nsu.fit.pixelmind.screens.load_game_screen to com.google.gson, org.apache.logging.log4j;
    opens ru.nsu.fit.pixelmind.screens.scores_screen to org.apache.logging.log4j;
    opens ru.nsu.fit.pixelmind.screens.game.game_field to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.game_field.tile to com.google.gson;
    opens ru.nsu.fit.pixelmind.config to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.character to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.game_field.tile_map to com.google.gson;
}