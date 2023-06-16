package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.canvas.Canvas;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.tile.TileController;
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

    public boolean isTileAccessible(int tileIndexX, int tileIndexY) {
        if (tileIndexX >= model.getTileMapWidth() || tileIndexY >= model.getTileMapHeight()) {
            return false;
        }
        TileController tileController = model.tileMap()[tileIndexX][tileIndexY];
        return !wallTypes.contains(tileController.getType());
    }

    public Deque<Pair<Integer, Integer>> buildRoute(Pair<Integer, Integer> start, Pair<Integer, Integer> end, List<Pair<Integer, Integer>> additionalBarriers) {
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(model.tileMap(), wallTypes);
        return shortestPathFinder.findShortestPath(start, end, additionalBarriers);
    }

    public Pair<Integer, Integer> getRandomFreeTile() {
        ArrayList<Pair<Integer, Integer>> availableTiles = new ArrayList<>();
        for (int i = 0; i < model.getTileMapHeight(); i++) {
            for (int j = 0; j < model.getTileMapWidth(); j++) {
                if (!wallTypes.contains(model.tileMap()[i][j].getType())) {
                    availableTiles.add(new Pair<>(i, j));
                }
            }
        }
        Random random = new Random();
        return availableTiles.get(random.nextInt(availableTiles.size()));
    }

    public boolean isThereEnemyOnThisTile(Pair<Integer, Integer> tileIndex) {
        return model.tileMap()[tileIndex.getKey()][tileIndex.getValue()].isThereEnemyOnTile().get();
    }

    public void captureTile(Pair<Integer, Integer> tileIndex) {
        model.tileMap()[tileIndex.getKey()][tileIndex.getValue()].isThereEnemyOnTile().set(true);
    }

    public void releaseTile(Pair<Integer, Integer> tileIndex) {
        model.tileMap()[tileIndex.getKey()][tileIndex.getValue()].isThereEnemyOnTile().set(false);
    }

}
