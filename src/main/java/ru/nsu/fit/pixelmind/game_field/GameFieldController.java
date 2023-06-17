package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.game_field.tile.TileController;
import ru.nsu.fit.pixelmind.utils.ShortestPathFinder;

import java.util.*;

public class GameFieldController {
    private final GameFieldViewBuilder viewBuilder;
    private final GameFieldModel tileMapModel;
    private final Set<TileType> wallTypes;

    public GameFieldController(@NotNull TileType[][] tileMap, @NotNull TileMapSize tileMapSize, @NotNull Map<TileType, Image> tileTypeImageResource) {
        tileMapModel = new GameFieldModel(GameFieldInteractor.tileTypesToTileControllers(tileMap, tileMapSize));
        tileMapModel.setHeight(tileMapSize.height());
        tileMapModel.setWidth(tileMapSize.width());
        viewBuilder = new GameFieldViewBuilder(tileMapModel, tileTypeImageResource);
        wallTypes = new HashSet<>();
        wallTypes.add(TileType.MOSSY_WALL);
        wallTypes.add(TileType.REGULAR_WALL);
        wallTypes.add(TileType.WOOD_WALL);
    }

    public Canvas getView() {
        return viewBuilder.build();
    }

    public void redrawTileMap() {
        viewBuilder.drawTileMap();
    }

    public boolean isTileAccessible(@NotNull TileIndexCoordinates tile) {
        if (tile.x() >= tileMapModel.width() || tile.y() >= tileMapModel.height()) {
            return false;
        }
        TileController tileController = tileMapModel.tileMap()[tile.x()][tile.y()];
        return !wallTypes.contains(tileController.getType());
    }

    @NotNull
    public Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(tileMapModel.tileMap(), wallTypes);
        return shortestPathFinder.findShortestPath(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

    @Nullable
    public TileIndexCoordinates getRandomFreeTile() {
        ArrayList<TileIndexCoordinates> availableTiles = new ArrayList<>();
        for (int i = 0; i < tileMapModel.height(); i++) {
            for (int j = 0; j < tileMapModel.width(); j++) {
                TileType tileType = tileMapModel.tileMap()[i][j].getType();
                if (!wallTypes.contains(tileType) && !tileMapModel.tileMap()[i][j].isThereSomebodyOnTile().get()) {
                    availableTiles.add(new TileIndexCoordinates(i, j));
                }
            }
        }
        Random random = new Random();
        if (availableTiles.isEmpty()) {
            return null;
        }
        return availableTiles.get(random.nextInt(availableTiles.size()));
    }

    public boolean isThereEnemyOnThisTile(@NotNull TileIndexCoordinates tile) {
        return tileMapModel.tileMap()[tile.x()][tile.y()].isThereSomebodyOnTile().get();
    }

    public void captureTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(true);
    }

    public void releaseTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(false);
    }

}
