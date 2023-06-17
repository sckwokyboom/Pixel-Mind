package ru.nsu.fit.pixelmind.game;

import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.characters.hero.HeroController;
import ru.nsu.fit.pixelmind.game_field.GameFieldController;

import java.util.List;

public record GameSession(GameFieldController gameField, HeroController hero, List<EnemyController> enemies){}

//{
//    private final GameFieldController gameField;
//    private final HeroController hero;
//    private final List<EnemyController> enemies;
//
//    public GameSession(GameFieldController gameField, HeroController hero, List<EnemyController> enemies) {
//        this.gameField = gameField;
//        this.hero = hero;
//        this.enemies = enemies;
//    }
//
//}
