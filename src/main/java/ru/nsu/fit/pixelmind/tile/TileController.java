package ru.nsu.fit.pixelmind.tile;

import javafx.scene.image.Image;
import javafx.util.Builder;

public class TileController {
    private final Builder<Image> viewBuilder;

    public TileController(Image tileTexture) {
        TileModel model = new TileModel(tileTexture);
        this.viewBuilder = new TileViewBuilder(model);
    }

    public Image getView() {
        return viewBuilder.build();
    }
}
