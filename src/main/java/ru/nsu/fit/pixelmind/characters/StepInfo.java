package ru.nsu.fit.pixelmind.characters;

import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

public record StepInfo(StepType stepType,
                       TileIndexCoordinates currentTile,
                       TileIndexCoordinates targetTile) {
}
