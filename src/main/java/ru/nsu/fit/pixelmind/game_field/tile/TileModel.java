package ru.nsu.fit.pixelmind.game_field.tile;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jetbrains.annotations.NotNull;

public class TileModel {
    private TileType tileType;
    @NotNull
    private final BooleanProperty isThereSomebodyOnTile = new SimpleBooleanProperty();

    TileModel(@NotNull TileType tileType) {
        this.tileType = tileType;
    }

    @NotNull
    public BooleanProperty isThereSomebodyOnTile() {
        return isThereSomebodyOnTile;
    }

    @NotNull
    public TileType tileType() {
        return tileType;
    }

    public void setTileType(@NotNull TileType tileType) {
        this.tileType = tileType;
    }
}
