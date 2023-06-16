package ru.nsu.fit.pixelmind.characters.hero;

import javafx.scene.image.Image;
import javafx.util.Builder;

public class HeroViewBuilder implements Builder<Image> {
    private final HeroModel model;

    public HeroViewBuilder(HeroModel model) {
        this.model = model;
    }

    @Override
    public Image build() {
//        System.out.println(model.spriteType());
        return model.currentSprite();
    }
}
