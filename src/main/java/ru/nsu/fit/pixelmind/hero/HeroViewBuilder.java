package ru.nsu.fit.pixelmind.hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Builder;

public class HeroViewBuilder implements Builder<Image> {
    private final HeroModel model;

    public HeroViewBuilder(HeroModel model) {
        this.model = model;
//        this.heroSprite = new ImageView();
    }

    @Override
    public Image build() {
//        ImageView hero = new ImageView(model.getHeroSprites().get(0));
//        if (model.getHeroSprites().isEmpty()) {
//            Node rectangle = new Rectangle(64, 64);
//            rectangle.setStyle("-fx-background-color: #C0C0C0;");
//            return rectangle;
        return model.getHeroSprites().get(0);
    }
}
