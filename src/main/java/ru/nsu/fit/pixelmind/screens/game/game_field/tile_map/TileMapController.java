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
    @NotNull
    private final TileMapView viewBuilder;
    @NotNull
    private final TileMapModel tileMapModel;
    @NotNull
    private final Set<TileType> wallTypes;

    TileMapController(@NotNull TileType[][] tileMap, @NotNull TileMapSize tileMapSize,
                      @NotNull Function<TileMapModel, TileMapView> viewCreator) {
        tileMapModel = new TileMapModel(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize);
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
        if (tile.x() >= tileMapModel.tileMapSize().width() || tile.y() >= tileMapModel.tileMapSize().height() || tile.x() < 0 || tile.y() < 0) {
            return false;
        }
        TileController tileController = tileMapModel.tileMap()[tile.y()][tile.x()];
        return !wallTypes.contains(tileController.getType());
    }

    @Nullable
    public Deque<TileIndexCoordinates> buildRoute(@NotNull TileIndexCoordinates exclusiveFrom, @NotNull TileIndexCoordinates inclusiveTo, @NotNull List<TileIndexCoordinates> additionalBarriers) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(tileMapModel.tileMap(), tileMapModel.tileMapSize(), wallTypes);
        return shortestPathFinder.findShortestPath(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

    @Nullable
    public TileIndexCoordinates getRandomFreeTile() {
        ArrayList<TileIndexCoordinates> availableTiles = new ArrayList<>();
        for (int i = 0; i < tileMapModel.tileMapSize().height(); i++) {
            for (int j = 0; j < tileMapModel.tileMapSize().width(); j++) {
                TileType tileType = tileMapModel.tileMap()[i][j].getType();
                if (!wallTypes.contains(tileType) && !tileMapModel.tileMap()[i][j].isThereSomebodyOnTile()) {
                    availableTiles.add(new TileIndexCoordinates(j, i));
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
        return tileMapModel.tileMap()[tile.y()][tile.x()].isThereSomebodyOnTile();
    }

    public void captureTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.y()][tileIndex.x()].setIsThereSomebodyOnTile(true);
    }

    public void releaseTile(@NotNull TileIndexCoordinates tileIndex) {
        tileMapModel.tileMap()[tileIndex.y()][tileIndex.x()].setIsThereSomebodyOnTile(false);
    }

    @NotNull
    public TileController[][] getTileMap() {
        return tileMapModel.tileMap();
    }

    public int getHeight() {
        return tileMapModel.tileMapSize().height();
    }

    public int getWidth() {
        return tileMapModel.tileMapSize().width();
    }
}
