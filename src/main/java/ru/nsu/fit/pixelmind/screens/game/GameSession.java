package ru.nsu.fit.pixelmind.screens.game;

import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.game_field.tile_map.TileMapController;

import java.util.List;

public record GameSession(TileMapController gameField, CharacterController hero, List<CharacterController> enemies) {
}
