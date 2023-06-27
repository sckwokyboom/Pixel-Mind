package ru.nsu.fit.pixelmind.screens.game.game_field.tile_map;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.utils.ShortestPathFinder;

import java.util.*;
import java.util.function.Function;

public class TileMapController {
    private final TileMapView viewBuilder;
    private final TileMapModel tileMapModel;
    private final Set<TileType> wallTypes;

    TileMapController(@NotNull TileType[][] tileMap, @NotNull TileMapSize tileMapSize,
                             Function<TileMapModel, TileMapView> viewCreator) {
        tileMapModel = new TileMapModel(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize));
        tileMapModel.setHeight(tileMapSize.height());
        tileMapModel.setWidth(tileMapSize.width());
        viewBuilder = viewCreator.apply(tileMapModel);
        wallTypes = new HashSet<>();
        wallTypes.add(TileType.MOSSY_WALL);
        wallTypes.add(TileType.REGULAR_WALL);
        wallTypes.add(TileType.WOOD_WALL);
    }

    public TileMapController(@NotNull TileType[][] tileMap, @NotNull TileMapSize tileMapSize) {
        this(tileMap, tileMapSize, TileMapView::new);
    }

    public void setResources(@NotNull Map<TileType, Image> tileTypeImageResource) {
        viewBuilder.setTileTypeImageMap(tileTypeImageResource);
    }

    @NotNull
    public Canvas getView() {
        return viewBuilder.build();
    }

    public void redrawTileMap() {
        viewBuilder.drawTileMap();
    }

    public boolean isTileAccessible(@NotNull TileIndexCoordinates tile) {
        if (tile.x() >= tileMapModel.width() || tile.y() >= tileMapModel.height() || tile.x() < 0 || tile.y() < 0) {
            return false;
        }
        TileController tileController = tileMapModel.tileMap()[tile.x()][tile.y()];
        return !wallTypes.contains(tileController.getType());
    }

    @NotNull
    public Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(tileMapModel.tileMap(), wallTypes);
        var route = shortestPathFinder.findShortestPath(exclusiveFrom, inclusiveTo, additionalBarriers);
        if (route == null) {
            return new ArrayDeque<>();
        }
        return route;
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

    public boolean isThereSomebodyOnThisTile(@NotNull TileIndexCoordinates tile) {
        return tileMapModel.tileMap()[tile.x()][tile.y()].isThereSomebodyOnTile().get();
    }

    public void captureTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(true);
    }

    public void releaseTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(false);
    }

    public TileController[][] getTileMap() {
        return tileMapModel.tileMap();
    }

    public int getHeight() {
        return tileMapModel.height();
    }

    public int getWidth() {
        return tileMapModel.width();
    }

    @NotNull
    public Map<TileType, Image> tileTypeImageMapResource() {
        return viewBuilder.tileTypeImageMap();
    }

}
