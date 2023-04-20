package ru.nsu.fit.pixelmind;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.widgets.Labels;

public class MainPixelMindViewBuilder implements Builder<Region> {
    private final MainPixelMindModel model;
    private final Region gameContent;
    private final Region newGameContent;
    private final Region loadGameContent;
    private final Region scoresContent;
    private final Region mainMenuContent;


    public MainPixelMindViewBuilder(MainPixelMindModel model,
                                    Region gameContent,
                                    Region newGameContent,
                                    Region loadGameContent,
                                    Region scoresContent) {
        this.model = model;
        this.gameContent = gameContent;
        this.newGameContent = newGameContent;
        this.loadGameContent = loadGameContent;
        this.scoresContent = scoresContent;
        this.mainMenuContent = createMainMenuButtons();
    }

    @Override
    public Region build() {
        BorderPane results = new BorderPane();
        results.setCenter(createCentre());
        return results;
    }

    private Region createMainMenuButtons() {
        ToggleButton function1 = new ToggleButton("New Game");
        ToggleButton function2 = new ToggleButton("Load Game");
        ToggleButton function3 = new ToggleButton("Scores");
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(function1, function2, function3);
        model.newGameScreenSelectedProperty().bind(
                Bindings.or(
                        Bindings.and(function1.selectedProperty().not(), model.getNewGameStarted()),
                        Bindings.and(function1.selectedProperty(), model.getNewGameStarted().not())
                ));
        model.loadGameScreenSelectedProperty().bind(function2.selectedProperty());
        model.scoresScreenSelectedProperty().bind(function3.selectedProperty());
        VBox results = new VBox(20, Labels.h4("Pixel Mind"), function1, function2, function3);
        results.setPadding(new Insets(14));
        results.setAlignment(Pos.CENTER);
        return results;
    }

    private Region createCentre() {
        scoresContent.visibleProperty().bind(model.scoresScreenSelectedProperty());
        mainMenuContent.visibleProperty().bind(model.mainMenuScreenSelectedProperty());
        gameContent.visibleProperty().bind(model.gameScreenSelectedProperty());
        loadGameContent.visibleProperty().bind(model.loadGameScreenSelectedProperty());
        newGameContent.visibleProperty().bind(model.newGameScreenSelectedProperty());
        model.getNewGameStarted().addListener(e -> System.out.println("NewGameStarted state: " + model.getNewGameStarted().get()));
        model.newGameScreenSelectedProperty().addListener(e -> System.out.println("NewGameScreen state: " + model.newGameScreenSelectedProperty().asString().get()));
        return new StackPane(
                scoresContent,
                mainMenuContent,
                gameContent,
                newGameContent,
                loadGameContent
        );

    }
}
