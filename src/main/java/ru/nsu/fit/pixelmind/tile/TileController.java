package ru.nsu.fit.pixelmind.tile;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.game_field.TileType;

public class TileController {
    private final Builder<Image> viewBuilder;
    private final TileModel model;

    public TileController(Image tileTexture, TileType tileType) {
        this.model = new TileModel(tileTexture, tileType);
        this.viewBuilder = new TileViewBuilder(model);
    }

    public Image getView() {
        return viewBuilder.build();
    }

    public TileType getType() {
        return model.tileType();
    }
    public BooleanProperty isThereSomebodyOnTile() {
        return model.isThereSomebodyOnTile();
    }
}
