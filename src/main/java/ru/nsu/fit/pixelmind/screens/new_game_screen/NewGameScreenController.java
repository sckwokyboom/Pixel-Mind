package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;

import java.util.Map;

public class NewGameScreenController implements BackToMainMenuListener {
    private final NewGameScreenViewBuilder viewBuilder;
    private final MainController mainController;
    private final NewGameScreenModel model;

    public NewGameScreenController(MainController mainController) {
        this.model = new NewGameScreenModel();
        this.viewBuilder = new NewGameScreenViewBuilder(this::handleUserChoose, this::handleBackToMainMenu, this::handleStartButtonClicked);
        this.mainController = mainController;
    }

    @Override
    public void handleBackToMainMenu() {
        mainController.switchToMainMenuScene();
    }

    public void handleStartButtonClicked() {
        mainController.runGame();
    }

    public Region getView() {
        return viewBuilder.build();
    }

    public UserModifications getUserModifications() {
        return model.userModifications();
    }

    public void setAvatars(@NotNull Map<CharacterType, Image> avatars) {
        viewBuilder.setAvatars(avatars);
    }

    void handleUserChoose(CharacterType heroType) {
        model.setUserModifications(new UserModifications(heroType));
    }
}
