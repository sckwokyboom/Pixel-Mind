module ru.nsu.fit.pixelmind {
    requires javafx.controls;
    //TODO: check this requirements
//    requires javafx.fxml;
//    requires org.controlsfx.controls;
//    requires org.kordamp.bootstrapfx.core;
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
    opens ru.nsu.fit.pixelmind.screens.game to com.google.gson, org.apache.logging.log4j, lombok;
    opens ru.nsu.fit.pixelmind.screens.load_game_screen to com.google.gson, org.apache.logging.log4j, lombok;
    opens ru.nsu.fit.pixelmind.screens.game.game_field to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.game_field.tile to com.google.gson;
    opens ru.nsu.fit.pixelmind.config to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.character to com.google.gson;
    opens ru.nsu.fit.pixelmind.screens.game.game_field.tile_map to com.google.gson;

    exports ru.nsu.fit.pixelmind.screens.scores_screen;
    opens ru.nsu.fit.pixelmind.screens.scores_screen to org.apache.logging.log4j, lombok;
}