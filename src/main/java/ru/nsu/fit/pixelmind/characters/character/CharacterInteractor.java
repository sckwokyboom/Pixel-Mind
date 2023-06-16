package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.utils.Utils;

public class CharacterInteractor {
    private final CharacterModel model;
    public CharacterInteractor(CharacterModel model) {
        this.model = model;

    }
    public void loadSprites(String spriteType) {
        for (int i = 0; i < 10; i++) {
            int startOffsetX = i * 32;
            int startOffsetY = 0;
            Image heroSpriteImage = Utils.parseImageByPixels(spriteType, startOffsetX, startOffsetY, 32, 32);
            model.getCharacterSprites().add(heroSpriteImage);
        }
    }
}
