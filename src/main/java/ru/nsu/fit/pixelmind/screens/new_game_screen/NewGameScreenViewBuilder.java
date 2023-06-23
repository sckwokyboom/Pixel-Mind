package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.widgets.Buttons;

import java.util.Map;
import java.util.function.Consumer;

public class NewGameScreenViewBuilder implements Builder<Region> {
    private final Runnable backToMainMenuButtonHandler;
    private final Runnable startButtonHandler;
    private final Consumer<CharacterType> handleUserChoose;
    private Map<CharacterType, Image> avatars;

    public NewGameScreenViewBuilder(Consumer<CharacterType> handleUserChoose, Runnable backToMainMenuButtonHandler, Runnable startButtonHandler) {
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
        this.startButtonHandler = startButtonHandler;
        this.handleUserChoose = handleUserChoose;
    }


    @Override
    public Region build() {
        handleUserChoose.accept(CharacterType.NORTH_WARRIOR);
        Canvas northWarrior = new Canvas(64, 64);
        northWarrior.getGraphicsContext2D().setGlobalAlpha(1);
        northWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.NORTH_WARRIOR), 0, 0);
        Canvas sandWarrior = new Canvas(64, 64);
        sandWarrior.getGraphicsContext2D().setGlobalAlpha(0.5);
        sandWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.SAND_WARRIOR), 0, 0);
        northWarrior.setOnMouseClicked(event -> {
            System.out.println("NORTH WARRIOR!");
            handleUserChoose.accept(CharacterType.NORTH_WARRIOR);
            northWarrior.getGraphicsContext2D().clearRect(0, 0, 64, 64);
            sandWarrior.getGraphicsContext2D().clearRect(0, 0, 64, 64);
            northWarrior.getGraphicsContext2D().setGlobalAlpha(1);
            northWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.NORTH_WARRIOR), 0, 0);
            sandWarrior.getGraphicsContext2D().setGlobalAlpha(0.5);
            sandWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.SAND_WARRIOR), 0, 0);
        });
        sandWarrior.setOnMouseClicked(event -> {
            System.out.println("SAND WARRIOR!");
            handleUserChoose.accept(CharacterType.SAND_WARRIOR);
            northWarrior.getGraphicsContext2D().clearRect(0, 0, 64, 64);
            sandWarrior.getGraphicsContext2D().clearRect(0, 0, 64, 64);
            northWarrior.getGraphicsContext2D().setGlobalAlpha(0.5);
            northWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.NORTH_WARRIOR), 0, 0);
            sandWarrior.getGraphicsContext2D().setGlobalAlpha(1);
            sandWarrior.getGraphicsContext2D().drawImage(avatars.get(CharacterType.SAND_WARRIOR), 0, 0);
        });
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        container.getChildren().addAll(northWarrior, sandWarrior);
        Label label = new Label("Click to create a new game");
        Button startGameButton = Buttons.button("Start Game", startButtonHandler);
        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);

        VBox results = new VBox(20, label, container, startGameButton, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }

    public void setAvatars(@NotNull Map<CharacterType, Image> avatars) {
        this.avatars = avatars;
    }
}