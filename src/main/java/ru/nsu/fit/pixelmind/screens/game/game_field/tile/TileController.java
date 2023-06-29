package ru.nsu.fit.pixelmind.screens.game.game_field.tile;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class TileController {
    @NotNull
    private final TileViewBuilder viewBuilder;
    @NotNull
    private final TileModel model;

    public TileController(@NotNull TileType tileType) {
        this.model = new TileModel(tileType);
        this.viewBuilder = new TileViewBuilder();
    }

    @NotNull
    public Image getView() {
        return viewBuilder.build();
    }

    @NotNull
    public TileType getType() {
        return model.tileType();
    }

    public void setType(@NotNull TileType newTileType) {
        model.setTileType(newTileType);
    }

    public void setTexture(@NotNull Image tileTexture) {
        viewBuilder.setTexture(tileTexture);
    }

    public boolean isThereSomebodyOnTile() {
        return model.isThereSomebodyOnTile();
    }

    public void setIsThereSomebodyOnTile(boolean isThereSomebodyOnTile) {
        model.setThereSomebodyOnTile(isThereSomebodyOnTile);
    }
}
