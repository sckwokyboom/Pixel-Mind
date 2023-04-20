package ru.nsu.fit.pixelmind.tilemap;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.Utils;
import ru.nsu.fit.pixelmind.tile.TileController;

import java.util.ArrayList;

public class GameFieldInteractor {
    private final GameFieldModel model;
    private static final int tilesNumInRow = 8;
    private static final int tilesNumInColumn = 1;

    private static final int tileHeight = 32;
    private static final int tileWidth = 32;
    private static final int tileSetWidth = 32 * 16;
    private static final int tileSetHeight = 32 * 3;

    public GameFieldInteractor(GameFieldModel model) {
        this.model = model;
    }

    public ArrayList<TileController> levelCreate() {
        ArrayList<TileController> tiles = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            tiles.add(new TileController(model.tileSet().get(TileType.REGULAR_FLOOR)));
        }
        tiles.set(140, new TileController(model.tileSet().get(TileType.DIRTY_FLOOR)));
        tiles.set(56, new TileController(model.tileSet().get(TileType.DIRTY_FLOOR)));
        tiles.set(98, new TileController(model.tileSet().get(TileType.DIRTY_FLOOR)));
        for (int i = 0; i < 16; i++) {
            tiles.set(i, new TileController(model.tileSet().get(TileType.REGULAR_WALL)));
        }
        tiles.set(4, new TileController(model.tileSet().get(TileType.MOSSY_WALL)));
        tiles.set(9, new TileController(model.tileSet().get(TileType.MOSSY_WALL)));
        tiles.set(5, new TileController(model.tileSet().get(TileType.CLOSED_DOOR)));
        tiles.set(15, new TileController(model.tileSet().get(TileType.WOOD_WALL)));
        tiles.set(0, new TileController(model.tileSet().get(TileType.WOOD_WALL)));
        for (int i = 112; i < 128; i++) {
            tiles.set(i, new TileController(model.tileSet().get(TileType.REGULAR_WALL)));
        }
        tiles.set(115, new TileController(model.tileSet().get(TileType.OPENED_DOOR)));
        tiles.set(120, new TileController(model.tileSet().get(TileType.MOSSY_WALL)));
        return tiles;
    }

    public void tileSetFill() {
        for (int j = 0; j < tilesNumInColumn; j++) {
            for (int i = 0; i < tilesNumInRow; i++) {
                int startOffsetX = (i * tileWidth) % tileSetWidth;
                int startOffsetY = (j * tileHeight) % tileSetHeight;
                Image tileImage = Utils.parseImageByPixels(Assets.TILES_REGULAR, startOffsetX, startOffsetY, tileHeight, tileWidth);
                model.tileSet().put(TileType.values()[i + j * tilesNumInRow], tileImage);
                System.out.println(TileType.values()[i + j * tilesNumInRow].toString() + " " + tileImage);
            }
        }
    }
}
