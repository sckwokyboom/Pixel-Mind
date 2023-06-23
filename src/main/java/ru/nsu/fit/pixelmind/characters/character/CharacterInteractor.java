package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class CharacterInteractor {
    public static final int NUMBER_OF_CHARACTER_SPRITES_IN_FILE = 10;
    public static final int NUMBER_OF_AVATARS = 2;
    public static final int SPRITE_SIZE = 32;
    public static final int AVATAR_SIZE = 64;

    @NotNull
    public static Map<SpriteType, Image> parseSprites(@NotNull String assetPath) {
        Map<SpriteType, Image> characterSprites = new HashMap<>(NUMBER_OF_CHARACTER_SPRITES_IN_FILE);
        assert (NUMBER_OF_CHARACTER_SPRITES_IN_FILE == SpriteType.values().length);
        for (int i = 0; i < NUMBER_OF_CHARACTER_SPRITES_IN_FILE; i++) {
            int startOffsetX = i * SPRITE_SIZE;
            int startOffsetY = 0;
            Image heroSpriteImage = Utils.parseImageByPixels(assetPath, startOffsetX, startOffsetY, SPRITE_SIZE, SPRITE_SIZE);
            characterSprites.put(SpriteType.valueOf(i), heroSpriteImage);
        }
        return characterSprites;
    }

    @NotNull
    public static Map<CharacterType, Image> parseAvatars(@NotNull String assetPath, @NotNull CharacterType[] avatarsTypes) {
        Map<CharacterType, Image> avatars = new HashMap<>(NUMBER_OF_AVATARS);
        assert (avatarsTypes.length == NUMBER_OF_AVATARS);
        for (int i = 0; i < NUMBER_OF_AVATARS; i++) {
            int startOffsetX = i * AVATAR_SIZE;
            int startOffsetY = 0;
            Image heroAvatarImage = Utils.parseImageByPixels(assetPath, startOffsetX, startOffsetY, AVATAR_SIZE, AVATAR_SIZE);
            avatars.put(avatarsTypes[i], heroAvatarImage);
        }
        return avatars;
    }
}
