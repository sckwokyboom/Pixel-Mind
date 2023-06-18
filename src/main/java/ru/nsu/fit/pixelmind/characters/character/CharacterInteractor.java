package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class CharacterInteractor {
    public static final int NUMBER_OF_CHARACTER_SPRITES_IN_FILE = 10;
    public static final int SPRITE_SIZE = 32;

    @NotNull
    public static Map<SpriteType, Image> parseSprites(@NotNull String assetPath) {
        Map<SpriteType, Image> characterSprites = new HashMap<>(NUMBER_OF_CHARACTER_SPRITES_IN_FILE);
        for (int i = 0; i < NUMBER_OF_CHARACTER_SPRITES_IN_FILE; i++) {
            int startOffsetX = i * SPRITE_SIZE;
            int startOffsetY = 0;
            Image heroSpriteImage = Utils.parseImageByPixels(assetPath, startOffsetX, startOffsetY, SPRITE_SIZE, SPRITE_SIZE);
            characterSprites.put(SpriteType.valueOf(i), heroSpriteImage);
        }
        return characterSprites;
    }
}
