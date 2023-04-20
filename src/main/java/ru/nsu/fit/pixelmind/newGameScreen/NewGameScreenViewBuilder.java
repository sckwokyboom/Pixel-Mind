package ru.nsu.fit.pixelmind.newGameScreen;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class NewGameScreenViewBuilder implements Builder<Region> {
    private final NewGameScreenModel model;

    public NewGameScreenViewBuilder(NewGameScreenModel model) {
        this.model = model;
    }


    @Override
    public Region build() {
        ToggleButton startGame = new ToggleButton("Start game");
        model.startGameSelectedProperty().bind(startGame.selectedProperty());
        startGame.setOnAction(e -> {
            System.out.println(model.startGameSelectedProperty().get());
        });
        model.startGameSelectedProperty().addListener(e -> System.out.println("from NewGameScreen!"));
        VBox results = new VBox(6, startGame);
        results.setPadding(new Insets(40));
        return results;
    }
}