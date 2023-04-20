package ru.nsu.fit.pixelmind.hero;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.util.Builder;

public class HeroController {
    private final HeroInteractor interactor;
    private final Builder<Image> viewBuilder;
    private final HeroModel model;

    public HeroController() {
        this.model = new HeroModel();
        interactor = new HeroInteractor(model);
        interactor.loadSprites();
        viewBuilder = new HeroViewBuilder(model);
    }

    public int getX() {
        return model.getX();
    }

    public int getY() {
        return model.getY();
    }

    public IntegerProperty getXProperty() {
        return model.getXProperty();
    }

    public IntegerProperty getYProperty() {
        return model.getYProperty();
    }


    public Image getView() {
        return viewBuilder.build();
    }
}
