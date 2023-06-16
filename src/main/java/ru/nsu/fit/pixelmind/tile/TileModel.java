package ru.nsu.fit.pixelmind.tile;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.game_field.TileType;

public class TileModel {
    private final Image tileTexture;
    private final TileType tileType;
    private final BooleanProperty isThereSomebodyOnTile = new SimpleBooleanProperty();

    TileModel(Image tileTexture, TileType tileType) {
        this.tileTexture = tileTexture;
        this.tileType = tileType;
    }

    public BooleanProperty isThereSomebodyOnTile() {
        return isThereSomebodyOnTile;
    }

    public Image tileTexture() {
        return tileTexture;
    }
    public TileType tileType() {
        return tileType;
    }
}
