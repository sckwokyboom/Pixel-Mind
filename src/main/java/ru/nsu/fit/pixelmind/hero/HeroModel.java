package ru.nsu.fit.pixelmind.hero;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class HeroModel {
    private final ArrayList<Image> heroSprites = new ArrayList<>(10);
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();
//    private int x;
//    private int y;

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty getXProperty() {
        return x;
    }

    public IntegerProperty getYProperty() {
        return y;
    }

    public ArrayList<Image> getHeroSprites() {
        return heroSprites;
    }
}
