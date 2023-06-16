package ru.nsu.fit.pixelmind.utils;

import javafx.util.Pair;
import ru.nsu.fit.pixelmind.game_field.TileType;
import ru.nsu.fit.pixelmind.tile.TileController;

import java.util.*;

public class ShortestPathFinder {

    private final TileController[][] tileMap;
    private final int width;
    private final int height;
    private final Set<TileType> wallTypes;
    private final Set<Pair<Integer, Integer>> barriers;

    public ShortestPathFinder(TileController[][] field, Set<TileType> wallTypes) {
        this.tileMap = field;
        this.width = field[0].length;
        this.height = field.length;
        this.wallTypes = wallTypes;
        barriers = new HashSet<>();
        buildWallBarriers();
    }

    public void buildWallBarriers() {
        barriers.clear();
        // TODO: if the tileMap don't change?
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (wallTypes.contains(tileMap[i][j].getType())) {
                    barriers.add(new Pair<>(i, j));
                }
            }
        }
    }

    public void addNewBarriers(List<Pair<Integer, Integer>> targets) {
        buildWallBarriers();
        barriers.addAll(targets);
    }

    public Deque<Pair<Integer, Integer>> findShortestPath(Pair<Integer, Integer> start, Pair<Integer, Integer> end, List<Pair<Integer, Integer>> additionalBarriers) {
        buildWallBarriers();
        barriers.addAll(additionalBarriers);
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Pair<Integer, Integer>> closedSet = new HashSet<>();
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom = new HashMap<>();
        Map<Pair<Integer, Integer>, Double> gScore = new HashMap<>();
        Map<Pair<Integer, Integer>, Double> fScore = new HashMap<>();

        gScore.put(start, 0.0);
        fScore.put(start, heuristicCostEstimate(start, end));
        openSet.add(new Node(start, fScore.get(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Pair<Integer, Integer> currentPosition = current.position;

            if (currentPosition.equals(end)) {
                return reconstructPath(cameFrom, end);
            }

            closedSet.add(currentPosition);

            for (Pair<Integer, Integer> neighbor : getNeighbors(currentPosition)) {
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

    private Deque<Pair<Integer, Integer>> reconstructPath(Map<Pair<Integer, Integer>, Pair<Integer, Integer>> cameFrom, Pair<Integer, Integer> current) {
        Deque<Pair<Integer, Integer>> path = new ArrayDeque<>();
        path.addFirst(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }

        return path;
    }

    private Set<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> position) {
        int x = position.getKey();
        int y = position.getValue();
        Set<Pair<Integer, Integer>> neighbors = new HashSet<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    neighbors.add(new Pair<>(nx, ny));
                }
            }
        }

        return neighbors;
    }

    private double distanceBetween(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        int dx = a.getKey() - b.getKey();
        int dy = a.getValue() - b.getValue();
//        return Math.sqrt(dx * dx + dy * dy);
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    private double heuristicCostEstimate(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        return distanceBetween(start, end);
    }
}

class Node implements Comparable<Node> {
    public Pair<Integer, Integer> position;
    public double fScore;

    public Node(Pair<Integer, Integer> position, double fScore) {
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
