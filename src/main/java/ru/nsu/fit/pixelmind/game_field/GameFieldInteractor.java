package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.Assets;
import ru.nsu.fit.pixelmind.Constants;
import ru.nsu.fit.pixelmind.tile.TileController;
import ru.nsu.fit.pixelmind.utils.Utils;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class GameFieldInteractor {
    private final GameFieldModel model;
    private static final int tilesNumInRow = 8;
    private static final int tilesNumInColumn = 1;
    private static final int tileSetWidth = 32 * 16;
    private static final int tileSetHeight = 32 * 3;

    public GameFieldInteractor(GameFieldModel model) {
        this.model = model;
    }

    public TileController[][] levelCreate() {
        TileController[][] tiles = new TileController[16][16];
        model.setTileMapWidth(16);
        model.setTileMapHeight(16);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                tiles[i][j] = new TileController(model.tileSet().get(TileType.REGULAR_FLOOR), TileType.REGULAR_FLOOR);
            }
        }
        tiles[14][10] = new TileController(model.tileSet().get(TileType.DIRTY_FLOOR), TileType.DIRTY_FLOOR);
        tiles[8][7] = new TileController(model.tileSet().get(TileType.DIRTY_FLOOR), TileType.DIRTY_FLOOR);
        tiles[10][13] = new TileController(model.tileSet().get(TileType.DIRTY_FLOOR), TileType.DIRTY_FLOOR);
        for (int i = 0; i < 16; i++) {
            tiles[i][0] = new TileController(model.tileSet().get(TileType.REGULAR_WALL), TileType.REGULAR_WALL);
        }
        return tiles;
    }

    public void tileSetFill() {
        for (int j = 0; j < tilesNumInColumn; j++) {
            for (int i = 0; i < tilesNumInRow; i++) {
                int startOffsetX = (i * Constants.TILE_SIZE) % tileSetWidth;
                int startOffsetY = (j * TILE_SIZE) % tileSetHeight;
                Image tileImage = Utils.parseImageByPixels(Assets.REGULAR_TILES_SET, startOffsetX, startOffsetY, TILE_SIZE, Constants.TILE_SIZE);
                model.tileSet().put(TileType.values()[i + j * tilesNumInRow], tileImage);
                System.out.println(TileType.values()[i + j * tilesNumInRow].toString() + " " + tileImage);
            }
        }
    }
}
