package ru.nsu.fit.pixelmind.game_field.tile;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;

public class TileController {
    private final TileViewBuilder viewBuilder;
    private final TileModel model;

    public TileController(TileType tileType) {
        this.model = new TileModel(tileType);
        this.viewBuilder = new TileViewBuilder();
    }

    public Image getView() {
        return viewBuilder.build();
    }

    public TileType getType() {
        return model.tileType();
    }

    public void setType(TileType newTileType) {
        model.setTileType(newTileType);
    }
    public void setTexture(Image tileTexture) {
        viewBuilder.setTexture(tileTexture);
    }

    public BooleanProperty isThereSomebodyOnTile() {
        return model.isThereSomebodyOnTile();
    }
}
