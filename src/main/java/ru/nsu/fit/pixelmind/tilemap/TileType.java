package ru.nsu.fit.pixelmind.tilemap;

public enum TileType {
    VOID(0),
    REGULAR_FLOOR(1),
    DIRTY_FLOOR(2),
    WOOD_WALL(3),
    CLOSED_DOOR(4),
    OPENED_DOOR(5),
    REGULAR_WALL(6),
    MOSSY_WALL(7);

    TileType(int i) {
    }
}
