package ru.nsu.fit.pixelmind.tile;

import javafx.scene.image.Image;

public class TileModel {
    private final Image tileTexture;

    public Image tileTexture() {
        return tileTexture;
    }

    TileModel(Image tileTexture) {
        this.tileTexture = tileTexture;
    }
}
