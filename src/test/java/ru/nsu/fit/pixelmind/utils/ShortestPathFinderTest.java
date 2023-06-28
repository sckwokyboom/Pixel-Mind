package ru.nsu.fit.pixelmind.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapInteractor;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapSize;

import java.util.*;

import static ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType.REGULAR_FLOOR;
import static ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType.REGULAR_WALL;

public class ShortestPathFinderTest {
    @Test
    public void routeWithoutBarriersTest() {
        TileMapSize tileMapSize = new TileMapSize(5, 6);
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};

        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
        var startExclusive = new TileIndexCoordinates(0, 0);
        var end = new TileIndexCoordinates(4, 4);
        var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
        var expected = new ArrayDeque<TileIndexCoordinates>();
        expected.addLast(new TileIndexCoordinates(1, 1));
        expected.addLast(new TileIndexCoordinates(2, 2));
        expected.addLast(new TileIndexCoordinates(3, 3));
        expected.addLast(new TileIndexCoordinates(4, 4));
        Assertions.assertNotNull(route);
        Assertions.assertEquals(expected.stream().toList(), route.stream().toList());
    }

    @Test
    public void routeWithWallBarriersTest() {
        TileMapSize tileMapSize = new TileMapSize(5, 6);
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR}};

        Set<TileType> wallTypes = new HashSet<>();
        wallTypes.add(REGULAR_WALL);
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, wallTypes);

        {
            var startExclusive = new TileIndexCoordinates(1, 4);
            var end = new TileIndexCoordinates(3, 2);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            var expected = new ArrayDeque<TileIndexCoordinates>();
            expected.addLast(new TileIndexCoordinates(1, 3));
            expected.addLast(new TileIndexCoordinates(1, 2));
            expected.addLast(new TileIndexCoordinates(1, 1));
            expected.addLast(new TileIndexCoordinates(2, 0));
            expected.addLast(new TileIndexCoordinates(3, 1));
            expected.addLast(new TileIndexCoordinates(3, 2));
            Assertions.assertNotNull(route);
            Assertions.assertEquals(expected.stream().toList(), route.stream().toList());
        }
        {
            var startExclusive = new TileIndexCoordinates(0, 0);
            var end = new TileIndexCoordinates(4, 4);

            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }
    }

    @Test
    public void routeWithAdditionalBarriersTest() {
        TileMapSize tileMapSize = new TileMapSize(5, 6);
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};

        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
        List<TileIndexCoordinates> additionalBarriers = new ArrayList<>();
        additionalBarriers.add(new TileIndexCoordinates(2, 1));
        additionalBarriers.add(new TileIndexCoordinates(2, 2));
        additionalBarriers.add(new TileIndexCoordinates(2, 3));
        additionalBarriers.add(new TileIndexCoordinates(2, 4));
        additionalBarriers.add(new TileIndexCoordinates(2, 5));

        additionalBarriers.add(new TileIndexCoordinates(3, 3));
        additionalBarriers.add(new TileIndexCoordinates(4, 3));

        {
            var startExclusive = new TileIndexCoordinates(1, 4);
            var end = new TileIndexCoordinates(3, 2);

            var route = shortestPathFinder.findShortestPath(startExclusive, end, additionalBarriers);
            var expected = new ArrayDeque<TileIndexCoordinates>();
            expected.addLast(new TileIndexCoordinates(1, 3));
            expected.addLast(new TileIndexCoordinates(1, 2));
            expected.addLast(new TileIndexCoordinates(1, 1));
            expected.addLast(new TileIndexCoordinates(2, 0));
            expected.addLast(new TileIndexCoordinates(3, 1));
            expected.addLast(new TileIndexCoordinates(3, 2));
            Assertions.assertNotNull(route);
            Assertions.assertEquals(expected.stream().toList(), route.stream().toList());
        }
        {
            var startExclusive = new TileIndexCoordinates(0, 0);
            var end = new TileIndexCoordinates(4, 4);

            var route = shortestPathFinder.findShortestPath(startExclusive, end, additionalBarriers);
            Assertions.assertNull(route);
        }
    }

    @Test
    public void onlyWallsFieldTest() {
        TileMapSize tileMapSize = new TileMapSize(5, 5);
        TileType[][] tileMap = {
                {REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL},
                {REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL},
                {REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL},
                {REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL},
                {REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL, REGULAR_WALL}};

        Set<TileType> wallTypes = new HashSet<>();
        wallTypes.add(REGULAR_WALL);
        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, wallTypes);
        var startExclusive = new TileIndexCoordinates(0, 0);
        var end = new TileIndexCoordinates(4, 4);

        var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
        Assertions.assertNull(route);
    }

    @Test
    public void onlyAdditionalBarriersFieldTest() {
        TileMapSize tileMapSize = new TileMapSize(2, 2);
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR}};

        ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
        List<TileIndexCoordinates> additionalBarriers = new ArrayList<>();
        additionalBarriers.add(new TileIndexCoordinates(0, 0));
        additionalBarriers.add(new TileIndexCoordinates(0, 1));
        additionalBarriers.add(new TileIndexCoordinates(1, 0));
        additionalBarriers.add(new TileIndexCoordinates(1, 1));
        var startExclusive = new TileIndexCoordinates(0, 0);
        var end = new TileIndexCoordinates(1, 1);
        var route = shortestPathFinder.findShortestPath(startExclusive, end, additionalBarriers);
        Assertions.assertNull(route);
    }

    @Test
    public void fromWallOrBarrierToFreeTileTest() {
        TileMapSize tileMapSize = new TileMapSize(2, 3);
        TileType[][] tileMap = {
                {REGULAR_WALL, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR}};
        {
            Set<TileType> wallTypes = new HashSet<>();
            wallTypes.add(REGULAR_WALL);
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, wallTypes);
            var startExclusive = new TileIndexCoordinates(0, 0);
            var end = new TileIndexCoordinates(1, 1);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }
        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 1);
            var end = new TileIndexCoordinates(1, 0);
            List<TileIndexCoordinates> additionalBarriers = new ArrayList<>();
            additionalBarriers.add(new TileIndexCoordinates(1, 1));
            var route = shortestPathFinder.findShortestPath(startExclusive, end, additionalBarriers);
            Assertions.assertNull(route);
        }
    }

    @Test
    public void routeToItselfTest() {
        TileMapSize tileMapSize = new TileMapSize(2, 3);
        TileType[][] tileMap = {
                {REGULAR_WALL, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR}};

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 1);
            var end = new TileIndexCoordinates(1, 1);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNotNull(route);
            Assertions.assertTrue(route.isEmpty());
        }

        {
            Set<TileType> wallTypes = new HashSet<>();
            wallTypes.add(REGULAR_WALL);
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, wallTypes);
            var startExclusive = new TileIndexCoordinates(0, 0);
            var end = new TileIndexCoordinates(0, 0);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }
        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 1);
            var end = new TileIndexCoordinates(1, 1);
            List<TileIndexCoordinates> additionalBarriers = new ArrayList<>();
            additionalBarriers.add(new TileIndexCoordinates(1, 1));
            var route = shortestPathFinder.findShortestPath(startExclusive, end, additionalBarriers);
            Assertions.assertNull(route);
        }
    }

    @Test
    public void outOfMapTileIndexTest() {
        TileMapSize tileMapSize = new TileMapSize(2, 3);
        TileType[][] tileMap = {
                {REGULAR_WALL, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR}};

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(2, 3);
            var end = new TileIndexCoordinates(1, 1);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(3, 2);
            var end = new TileIndexCoordinates(1, 1);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 0);
            var end = new TileIndexCoordinates(3, 2);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }
        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 0);
            var end = new TileIndexCoordinates(2, 3);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(3, 3);
            var end = new TileIndexCoordinates(3, 3);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(-3, -3);
            var end = new TileIndexCoordinates(1, 1);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(1, 1);
            var end = new TileIndexCoordinates(-3, -3);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }

        {
            ShortestPathFinder shortestPathFinder = new ShortestPathFinder(TileMapInteractor.tileTypesToTileControllers(tileMap, tileMapSize), tileMapSize, null);
            var startExclusive = new TileIndexCoordinates(-3, -3);
            var end = new TileIndexCoordinates(-3, -3);
            var route = shortestPathFinder.findShortestPath(startExclusive, end, null);
            Assertions.assertNull(route);
        }
    }
}
