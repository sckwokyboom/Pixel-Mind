package ru.nsu.fit.pixelmind.utils;

import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.game_field.TileType;
import ru.nsu.fit.pixelmind.game_field.tile.TileController;

import java.util.*;

public class ShortestPathFinder {

    private final TileController[][] tileMap;
    private final int width;
    private final int height;
    private final Set<TileType> wallTypes;
    private final Set<TileIndexCoordinates> barriers;

    public ShortestPathFinder(TileController[][] field, Set<TileType> wallTypes) {
        this.tileMap = field;
        this.width = field[0].length;
        this.height = field.length;
        this.wallTypes = wallTypes;
        barriers = new HashSet<>();
    }

    public void buildWallBarriers() {
        barriers.clear();
        // TODO: if the tileMap don't change?
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (wallTypes.contains(tileMap[i][j].getType())) {
                    barriers.add(new TileIndexCoordinates(i, j));
                }
            }
        }
    }

    public void addNewBarriers(List<TileIndexCoordinates> targets) {
        buildWallBarriers();
        barriers.addAll(targets);
    }

    public Deque<TileIndexCoordinates> findShortestPath(TileIndexCoordinates start, TileIndexCoordinates end, List<TileIndexCoordinates> additionalBarriers) {
        if (start == null || end == null) {
            return null;
        }
        buildWallBarriers();
        barriers.addAll(additionalBarriers);
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<TileIndexCoordinates> closedSet = new HashSet<>();
        Map<TileIndexCoordinates, TileIndexCoordinates> cameFrom = new HashMap<>();
        Map<TileIndexCoordinates, Double> gScore = new HashMap<>();
        Map<TileIndexCoordinates, Double> fScore = new HashMap<>();

        gScore.put(start, 0.0);
        fScore.put(start, heuristicCostEstimate(start, end));
        openSet.add(new Node(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            TileIndexCoordinates currentPosition = current.position;

            if (currentPosition.equals(end)) {
                return reconstructPath(cameFrom, end);
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
                    fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, end));

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
//        return Math.sqrt(dx * dx + dy * dy);
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    private double heuristicCostEstimate(TileIndexCoordinates start, TileIndexCoordinates end) {
        return distanceBetween(start, end);
    }
}

class Node implements Comparable<Node> {
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
        if (obj instanceof Node other) {
            return position.equals(other.position);
        }
        return false;
    }

    public int hashCode() {
        return position.hashCode();
    }
}
