package ru.nsu.fit.pixelmind.screens.game.game_field.tile;

import org.jetbrains.annotations.NotNull;

public class TileModel {
    private TileType tileType;
    private boolean isThereSomebodyOnTile;

    TileModel(@NotNull TileType tileType) {
        this.tileType = tileType;
    }

    public boolean isThereSomebodyOnTile() {
        return isThereSomebodyOnTile;
    }

    @NotNull
    public TileType tileType() {
        return tileType;
    }

    public void setTileType(@NotNull TileType tileType) {
        this.tileType = tileType;
    }

    public void setThereSomebodyOnTile(boolean thereSomebodyOnTile) {
        isThereSomebodyOnTile = thereSomebodyOnTile;
    }
}
