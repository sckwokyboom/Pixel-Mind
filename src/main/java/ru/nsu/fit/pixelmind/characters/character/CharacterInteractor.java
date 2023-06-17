package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.utils.Utils;

import java.util.ArrayList;

public class CharacterInteractor {
    public static final int NUMBER_OF_CHARACTER_SPRITES_IN_FILE = 10;
    public static final int SPRITE_SIZE = 32;
    private final CharacterView characterViewBuilder;

    public CharacterInteractor(CharacterView characterViewBuilder) {
        this.characterViewBuilder = characterViewBuilder;

    }

    @NotNull
    public static ArrayList<Image> parseSprites(@NotNull String assetPath) {
        ArrayList<Image> characterSprites = new ArrayList<>(NUMBER_OF_CHARACTER_SPRITES_IN_FILE);
        for (int i = 0; i < NUMBER_OF_CHARACTER_SPRITES_IN_FILE; i++) {
            int startOffsetX = i * SPRITE_SIZE;
            int startOffsetY = 0;
            Image heroSpriteImage = Utils.parseImageByPixels(assetPath, startOffsetX, startOffsetY, SPRITE_SIZE, SPRITE_SIZE);
            characterSprites.add(heroSpriteImage);
        }
        return characterSprites;
    }
}
