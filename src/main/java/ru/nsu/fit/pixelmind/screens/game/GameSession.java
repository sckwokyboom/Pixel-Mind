package ru.nsu.fit.pixelmind.screens.game;

import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;

import java.util.List;

public record GameSession(GameFieldController gameField, CharacterController hero, List<CharacterController> enemies) {
}
