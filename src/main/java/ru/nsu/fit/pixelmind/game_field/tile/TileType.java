package ru.nsu.fit.pixelmind.game_field.tile;

public enum TileType {
    VOID(0),
    REGULAR_FLOOR(1),
    DIRTY_FLOOR(2),
    WOOD_WALL(3),
    CLOSED_DOOR(4),
    OPENED_DOOR(5),
    REGULAR_WALL(6),
    MOSSY_WALL(7);

    private final int key;

    TileType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public static TileType valueOf(int key) {
        for (TileType type : TileType.values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        return null;
    }
}
