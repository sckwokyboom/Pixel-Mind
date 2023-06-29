package ru.nsu.fit.pixelmind.screens.new_game_screen;

import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.BackToMainMenuListener;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.ScreenController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

import java.util.Map;

public class NewGameScreenController implements BackToMainMenuListener, ScreenController {
    @NotNull
    private final NewGameScreenViewBuilder viewBuilder;
    @NotNull
    private final MainController mainController;
    @NotNull
    private final NewGameScreenModel model;

    public NewGameScreenController(@NotNull MainController mainController) {
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

    @Override
    @NotNull
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
