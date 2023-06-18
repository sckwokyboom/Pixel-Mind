package ru.nsu.fit.pixelmind.characters;

public enum SpriteType {
    REGULAR_SPRITE(0),
    MOVING_FIRST(1),
    ATTACK_FIRST(2),
    ATTACK_SECOND(3),
    ATTACK_THIRD(4),
    ATTACK_FOURTH(5),
    WAIT(6),
    DIE_FIRST(7),
    DIE_SECOND(8),
    DIE_THIRD(9);

    SpriteType(int key) {
        this.key = key;
    }

    private final int key;

    public int getKey() {
        return key;
    }

    public static SpriteType valueOf(int key) {
        for (SpriteType type : SpriteType.values()) {
            if (type.getKey() == key) {
                return type;
            }
        }
        return null;
    }
}
