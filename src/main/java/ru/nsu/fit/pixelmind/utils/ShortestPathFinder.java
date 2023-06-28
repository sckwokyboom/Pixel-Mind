package ru.nsu.fit.pixelmind.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapSize;

import java.util.*;

public class ShortestPathFinder {

    private final TileController[][] tileMap;
    private final int width;
    private final int height;
    private final Set<TileType> wallTypes;
    private final Set<TileIndexCoordinates> barriers;

    public ShortestPathFinder(@NotNull TileController[][] field, @NotNull TileMapSize tileMapSize, @Nullable Set<TileType> wallTypes) {
        this.tileMap = field;
        this.width = tileMapSize.width();
        this.height = tileMapSize.height();
        this.wallTypes = wallTypes;
        barriers = new HashSet<>();
    }

    private void buildWallBarriers() {
        if (wallTypes == null) {
            return;
        }
        barriers.clear();
        // TODO: if the tileMap don't change?
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (wallTypes.contains(tileMap[i][j].getType())) {
                    barriers.add(new TileIndexCoordinates(j, i));
                }
            }
        }
    }

    @Nullable
    public Deque<TileIndexCoordinates> findShortestPath(@NotNull TileIndexCoordinates startExclusive, @NotNull TileIndexCoordinates endInclusive, @Nullable List<TileIndexCoordinates> additionalBarriers) {
        buildWallBarriers();
        if (additionalBarriers != null) {
            barriers.addAll(additionalBarriers);
        }
        if (startExclusive.x() >= width || startExclusive.x() < 0 || endInclusive.x() >= width || endInclusive.x() < 0
                || startExclusive.y() >= height || startExclusive.y() < 0 || endInclusive.y() >= height || endInclusive.y() < 0) {
            return null;
        }
        if (wallTypes != null
                && (wallTypes.contains(tileMap[startExclusive.y()][startExclusive.x()].getType()) || wallTypes.contains(tileMap[endInclusive.y()][endInclusive.x()].getType()))) {
            return null;
        }
        if (additionalBarriers != null
                && (additionalBarriers.contains(startExclusive) || additionalBarriers.contains(endInclusive))) {
            return null;
        }
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<TileIndexCoordinates> closedSet = new HashSet<>();
        Map<TileIndexCoordinates, TileIndexCoordinates> cameFrom = new HashMap<>();
        Map<TileIndexCoordinates, Double> gScore = new HashMap<>();
        Map<TileIndexCoordinates, Double> fScore = new HashMap<>();

        gScore.put(startExclusive, 0.0);
        fScore.put(startExclusive, heuristicCostEstimate(startExclusive, endInclusive));
        openSet.add(new Node(startExclusive, fScore.get(startExclusive)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            TileIndexCoordinates currentPosition = current.position;

            if (currentPosition.equals(endInclusive)) {
                return reconstructPath(cameFrom, endInclusive);
            }

            closedSet.add(currentPosition);

            for (TileIndexCoordinates neighbor : getNeighbors(currentPosition)) {
                if (barriers.contains(neighbor) || closedSet.contains(neighbor)) {
                    continue;
                }

                double tentativeGScore = gScore.get(currentPosition) + distanceBetween(currentPosition, neighbor);

                if (!openSet.contains(new Node(neighbor, 0.0)) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, currentPosition);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, endInclusive));

                    if (!openSet.contains(new Node(neighbor, 0.0))) {
                        openSet.add(new Node(neighbor, fScore.get(neighbor)));
                    }
                }
            }
        }

        return null;
    }

    private Deque<TileIndexCoordinates> reconstructPath(Map<TileIndexCoordinates, TileIndexCoordinates> cameFrom, TileIndexCoordinates current) {
        Deque<TileIndexCoordinates> path = new ArrayDeque<>();
        path.addFirst(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        path.removeFirst();

        return path;
    }

    private Set<TileIndexCoordinates> getNeighbors(TileIndexCoordinates position) {
        int x = position.x();
        int y = position.y();
        Set<TileIndexCoordinates> neighbors = new HashSet<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    neighbors.add(new TileIndexCoordinates(nx, ny));
                }
            }
        }

        return neighbors;
    }

    private double distanceBetween(TileIndexCoordinates a, TileIndexCoordinates b) {
        int dx = a.x() - b.x();
        int dy = a.y() - b.y();
        return Math.sqrt(dx * dx + dy * dy);
//        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    private double heuristicCostEstimate(TileIndexCoordinates start, TileIndexCoordinates end) {
        return distanceBetween(start, end);
    }

    static class Node implements Comparable<Node> {
        public TileIndexCoordinates position;
        public double fScore;

        public Node(TileIndexCoordinates position, double fScore) {
            this.position = position;
            this.fScore = fScore;
        }

        public int compareTo(Node other) {
            return Double.compare(fScore, other.fScore);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Node other) {
                return position.equals(other.position);
            }
            return false;
        }

        public int hashCode() {
            return position.hashCode();
        }
    }

}
