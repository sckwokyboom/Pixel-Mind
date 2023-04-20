package ru.nsu.fit.pixelmind.tilemap;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.tile.TileController;

import java.util.ArrayList;
import java.util.HashMap;

public class GameFieldModel {
    private final HashMap<TileType, Image> tileSet = new HashMap<>();
    private ArrayList<TileController> tileMap;

    public HashMap<TileType, Image> tileSet() {
        return tileSet;
    }

    public void updateLevelTileMap(ArrayList<TileController> tileMap) {
        this.tileMap = tileMap;
    }

    public ArrayList<TileController> tileMap() {
        return tileMap;
    }

}
