package ru.nsu.fit.pixelmind.screens.game;

import ru.nsu.fit.pixelmind.characters.character.CharacterController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;

import java.util.List;

public record GameSession(GameFieldController gameField, CharacterController hero, List<CharacterController> enemies) {
}

//{
//    private final GameFieldController gameField;
//    private final CharacterController hero;
//    private final List<CharacterController> enemies;
//
//    public GameSession(GameFieldController gameField, CharacterController hero, List<CharacterController> enemies) {
//        this.gameField = gameField;
//        this.hero = hero;
//        this.enemies = enemies;
//    }
//
//}
