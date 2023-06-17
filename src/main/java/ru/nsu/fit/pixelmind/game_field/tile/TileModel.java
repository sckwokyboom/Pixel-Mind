package ru.nsu.fit.pixelmind.game_field.tile;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ru.nsu.fit.pixelmind.game_field.TileType;

public class TileModel {
    private TileType tileType;
    private final BooleanProperty isThereSomebodyOnTile = new SimpleBooleanProperty();

    TileModel(TileType tileType) {
        this.tileType = tileType;
    }

    public BooleanProperty isThereSomebodyOnTile() {
        return isThereSomebodyOnTile;
    }


    public TileType tileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
