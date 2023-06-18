package ru.nsu.fit.pixelmind.game_field.tile_map;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.Constants;
import ru.nsu.fit.pixelmind.game_field.tile.TileController;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static ru.nsu.fit.pixelmind.Constants.TILE_SIZE;

public class TileMapInteractor {
    private static final int TILES_NUM_IN_ROW = 8;
    private static final int TILES_NUM_IN_COLUMN = 1;
    private static final int TILE_SET_WIDTH = 32 * 16;
    private static final int TILE_SET_HEIGHT = 32 * 3;

    @NotNull
    public static TileController[][] tileTypesToTileControllers(@NotNull TileType[][] tileTypes, @NotNull TileMapSize tileMapSize) {
        assert (tileMapSize.height() > 0);
        assert (tileMapSize.width() > 0);

        TileController[][] tileControllers = new TileController[tileMapSize.height()][tileMapSize.width()];
        for (int i = 0; i < tileMapSize.height(); i++) {
            for (int j = 0; j < tileMapSize.width(); j++) {
                tileControllers[i][j] = new TileController(tileTypes[j][i]);
            }
        }
        return tileControllers;
    }

    public static Map<TileType, Image> parseTileSet(String asset) {
        Map<TileType, Image> tileSet = new HashMap<>();
        for (int j = 0; j < TILES_NUM_IN_COLUMN; j++) {
            for (int i = 0; i < TILES_NUM_IN_ROW; i++) {
                int startOffsetX = (i * Constants.TILE_SIZE) % TILE_SET_WIDTH;
                int startOffsetY = (j * TILE_SIZE) % TILE_SET_HEIGHT;
                Image tileImage = Utils.parseImageByPixels(asset, startOffsetX, startOffsetY, TILE_SIZE, Constants.TILE_SIZE);
                tileSet.put(TileType.values()[i + j * TILES_NUM_IN_ROW], tileImage);
                System.out.println(TileType.values()[i + j * TILES_NUM_IN_ROW].toString() + " " + tileImage);
            }
        }
        return tileSet;
    }
}
