package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.tile.TileController;
import ru.nsu.fit.pixelmind.tile.TileModel;
import ru.nsu.fit.pixelmind.utils.ShortestPathFinder;

import java.util.*;

public class GameFieldController {
    private final GameFieldViewBuilder viewBuilder;
    private final GameFieldInteractor interactor;
    private final GameFieldModel model;
    private final Set<TileType> wallTypes;

    public GameFieldController() {
        model = new GameFieldModel();
        interactor = new GameFieldInteractor(model);
        interactor.tileSetFill();
        viewBuilder = new GameFieldViewBuilder(model);
        wallTypes = new HashSet<>();
        wallTypes.add(TileType.MOSSY_WALL);
        wallTypes.add(TileType.REGULAR_WALL);
        wallTypes.add(TileType.WOOD_WALL);
    }

    public Canvas getView() {
        return viewBuilder.build();
    }

    public void setHeroToPos() {
    }

    public void loadTileMap() {
        // TODO: load level from somewhere...
        model.updateLevelTileMap(interactor.levelCreate());
    }

    public void redrawTileMap() {
        viewBuilder.draw();
    }

    public boolean isTileAccessible(TileIndexCoordinates tile) {
        if (tile.x() >= model.getTileMapWidth() || tile.y() >= model.getTileMapHeight()) {
            return false;
        }
        TileController tileController = model.tileMap()[tile.x()][tile.y()];
        return !wallTypes.contains(tileController.getType());
    }

    public Deque<TileIndexCoordinates> buildRoute(TileIndexCoordinates exclusiveFrom, TileIndexCoordinates inclusiveTo, List<TileIndexCoordinates> additionalBarriers) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(model.tileMap(), wallTypes);
        return shortestPathFinder.findShortestPath(exclusiveFrom, inclusiveTo, additionalBarriers);
    }

    public TileIndexCoordinates getRandomFreeTile() {
        ArrayList<TileIndexCoordinates> availableTiles = new ArrayList<>();
        for (int i = 0; i < model.getTileMapHeight(); i++) {
            for (int j = 0; j < model.getTileMapWidth(); j++) {
                if (!wallTypes.contains(model.tileMap()[i][j].getType()) && !model.tileMap()[i][j].isThereSomebodyOnTile().get()) {
                    availableTiles.add(new TileIndexCoordinates(i, j));
                }
            }
        }
        Random random = new Random();
        return availableTiles.get(random.nextInt(availableTiles.size()));
    }

    public boolean isThereEnemyOnThisTile(TileIndexCoordinates tile) {
        return model.tileMap()[tile.x()][tile.y()].isThereSomebodyOnTile().get();
    }

    public void captureTile(TileIndexCoordinates tileIndex) {
        model.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(true);
    }

    public void releaseTile(TileIndexCoordinates tileIndex) {
        model.tileMap()[tileIndex.x()][tileIndex.y()].isThereSomebodyOnTile().set(false);
    }

}
