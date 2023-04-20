package ru.nsu.fit.pixelmind.tile;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Builder;

public class TileViewBuilder implements Builder<Image> {
    private final TileModel model;

    TileViewBuilder(TileModel model) {
        this.model = model;
    }

    @Override
    public Image build() {
        // TODO: if model.tileTexture() is null???
        return model.tileTexture();
    }
}
