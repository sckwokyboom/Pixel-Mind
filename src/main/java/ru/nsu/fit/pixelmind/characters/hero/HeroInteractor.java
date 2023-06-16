package ru.nsu.fit.pixelmind.characters.hero;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.utils.Utils;

public class HeroInteractor {
    private final HeroModel model;
    public HeroInteractor(HeroModel model) {
        this.model = model;

    }
    public void loadSprites() {
        for (int i = 0; i < 10; i++) {
            int startOffsetX = i * 32;
            int startOffsetY = 0;
            Image heroSpriteImage = Utils.parseImageByPixels(Assets.HERO_SPRITES, startOffsetX, startOffsetY, 32, 32);
            model.heroSprites().add(heroSpriteImage);
        }
    }
}
