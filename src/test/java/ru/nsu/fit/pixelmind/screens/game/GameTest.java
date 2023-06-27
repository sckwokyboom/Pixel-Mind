package ru.nsu.fit.pixelmind.screens.game;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.pixelmind.Constants;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.game.camera.CameraController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterController;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterView;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapSize;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType.REGULAR_FLOOR;
import static ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType.REGULAR_WALL;

public class GameTest {
    public static class GameViewTest extends GameView {
        GameViewTest(@NotNull CameraController cameraController, @NotNull GameModel gameModel) {
            super(cameraController, gameModel);
        }

        @Override
        public void animateHeroAndEnemiesSteps(@NotNull CharacterView hero, @NotNull List<CharacterView> enemies, @NotNull Runnable callback) {
            callback.run();
        }
    }

    static class DefaultGameEndSceneHandler implements MainController.GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
        }
    }

    static class LoseSceneHandler implements MainController.GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
            Assertions.assertEquals(Constants.GAME_LOSS_MESSAGE, gameResult);
            Assertions.assertEquals(0, gameScore);
        }
    }

    static class VictorySceneHandler implements MainController.GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
            Assertions.assertEquals(Constants.GAME_VICTORY_MESSAGE, gameResult);
            Assertions.assertEquals(1000, gameScore);
        }
    }

    private GameSession createDefaultGameSession() {
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};
        TileMapController gameField = new TileMapController(tileMap, new TileMapSize(5, 5));
        CharacterController hero = new CharacterController(CharacterType.NORTH_WARRIOR);
        hero.setCurrentPosition(new TileIndexCoordinates(0, 0));
        hero.setDamageValue(15);
        hero.setCurrentHealth(100);
        List<CharacterController> enemies = new ArrayList<>();
        CharacterController closeDistanceEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        closeDistanceEnemy.setCurrentPosition(new TileIndexCoordinates(1, 1));
        closeDistanceEnemy.setCurrentHealth(100);
        closeDistanceEnemy.setDamageValue(1);
        enemies.add(closeDistanceEnemy);
        CharacterController remoteEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        remoteEnemy.setCurrentPosition(new TileIndexCoordinates(2, 0));
        remoteEnemy.setCurrentHealth(15);
        remoteEnemy.setDamageValue(5);
        enemies.add(remoteEnemy);
        return new GameSession(gameField, hero, enemies);
    }

    private GameSession createWallsCageGameSession() {
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_WALL, REGULAR_WALL, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};
        TileMapController gameField = new TileMapController(tileMap, new TileMapSize(5, 5));
        CharacterController hero = new CharacterController(CharacterType.NORTH_WARRIOR);
        hero.setCurrentPosition(new TileIndexCoordinates(0, 0));
        hero.setDamageValue(15);
        hero.setCurrentHealth(100);
        List<CharacterController> enemies = new ArrayList<>();
        CharacterController closeDistanceEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        closeDistanceEnemy.setCurrentPosition(new TileIndexCoordinates(1, 1));
        closeDistanceEnemy.setCurrentHealth(100);
        closeDistanceEnemy.setDamageValue(1);
        enemies.add(closeDistanceEnemy);
        CharacterController remoteEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        remoteEnemy.setCurrentPosition(new TileIndexCoordinates(3, 0));
        remoteEnemy.setCurrentHealth(15);
        remoteEnemy.setDamageValue(5);
        enemies.add(remoteEnemy);
        return new GameSession(gameField, hero, enemies);
    }

    private GameSession createEnemiesCageGameSession() {
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};
        TileMapController gameField = new TileMapController(tileMap, new TileMapSize(5, 5));
        CharacterController hero = new CharacterController(CharacterType.NORTH_WARRIOR);
        hero.setCurrentPosition(new TileIndexCoordinates(0, 0));
        hero.setDamageValue(15);
        hero.setCurrentHealth(100);
        List<CharacterController> enemies = new ArrayList<>();

        CharacterController enemy1 = new CharacterController(CharacterType.FIRE_WOMAN);
        enemy1.setCurrentPosition(new TileIndexCoordinates(0, 1));
        enemy1.setCurrentHealth(100);
        enemy1.setDamageValue(1);
        enemies.add(enemy1);
        CharacterController enemy2 = new CharacterController(CharacterType.FIRE_WOMAN);
        enemy2.setCurrentPosition(new TileIndexCoordinates(1, 0));
        enemy2.setCurrentHealth(100);
        enemy2.setDamageValue(1);
        enemies.add(enemy2);
        CharacterController enemy3 = new CharacterController(CharacterType.FIRE_WOMAN);
        enemy3.setCurrentPosition(new TileIndexCoordinates(1, 1));
        enemy3.setCurrentHealth(100);
        enemy3.setDamageValue(1);
        enemies.add(enemy3);

        CharacterController remoteEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        remoteEnemy.setCurrentPosition(new TileIndexCoordinates(4, 4));
        remoteEnemy.setCurrentHealth(100);
        remoteEnemy.setDamageValue(1);
        enemies.add(remoteEnemy);
        return new GameSession(gameField, hero, enemies);
    }

    private GameSession createLoseGameSession() {
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};
        TileMapController gameField = new TileMapController(tileMap, new TileMapSize(5, 5));
        CharacterController hero = new CharacterController(CharacterType.NORTH_WARRIOR);
        hero.setCurrentPosition(new TileIndexCoordinates(0, 0));
        hero.setCurrentHealth(1);
        List<CharacterController> enemies = new ArrayList<>();
        CharacterController closeDistanceEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        closeDistanceEnemy.setCurrentPosition(new TileIndexCoordinates(1, 1));
        closeDistanceEnemy.setCurrentHealth(100);
        closeDistanceEnemy.setDamageValue(1);
        enemies.add(closeDistanceEnemy);
        return new GameSession(gameField, hero, enemies);
    }

    private GameSession createVictoryGameSession() {
        TileType[][] tileMap = {
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR},
                {REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR, REGULAR_FLOOR}};
        TileMapController gameField = new TileMapController(tileMap, new TileMapSize(5, 5));
        CharacterController hero = new CharacterController(CharacterType.NORTH_WARRIOR);
        hero.setCurrentPosition(new TileIndexCoordinates(0, 0));
        hero.setCurrentHealth(1);
        hero.setDamageValue(1000);
        List<CharacterController> enemies = new ArrayList<>();
        CharacterController closeDistanceEnemy = new CharacterController(CharacterType.FIRE_WOMAN);
        closeDistanceEnemy.setCurrentPosition(new TileIndexCoordinates(1, 1));
        closeDistanceEnemy.setCurrentHealth(100);
        closeDistanceEnemy.setDamageValue(1);
        enemies.add(closeDistanceEnemy);
        return new GameSession(gameField, hero, enemies);
    }

    @Test
    public void simpleStepTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createDefaultGameSession());
        TileIndexCoordinates tileClicked = new TileIndexCoordinates(0, 1);
        TileIndexCoordinates oldHeroTile = gameController.getGameSession().hero().currentTile();
        gameController.handleTileClicked(tileClicked);
        Assertions.assertEquals(tileClicked, gameController.getGameSession().hero().currentTile());
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(tileClicked));
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(oldHeroTile));
    }

    @Test
    public void compoundStepTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createDefaultGameSession());
        gameController.handleTileClicked(new TileIndexCoordinates(4, 4));
        Assertions.assertEquals(gameController.getGameSession().hero().currentTile(), new TileIndexCoordinates(4, 4));
    }

    @Test
    public void closeDistanceEnemyHitTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createDefaultGameSession());
        TileIndexCoordinates enemyPos = new TileIndexCoordinates(1, 1);
        gameController.handleTileClicked(enemyPos);
        Assertions.assertEquals(gameController.getGameSession().hero().currentTile(), new TileIndexCoordinates(0, 0));
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(new TileIndexCoordinates(0, 0)));
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(enemyPos));
        Assertions.assertEquals(gameController.getGameSession().enemies().get(0).currentHealth(), 85);
        Assertions.assertEquals(gameController.getGameSession().hero().currentHealth(), 99);
    }

    @Test
    public void remoteEnemyHitTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createDefaultGameSession());
        TileIndexCoordinates remoteEnemyPos = new TileIndexCoordinates(2, 0);
        gameController.handleTileClicked(remoteEnemyPos);
        Assertions.assertEquals(new TileIndexCoordinates(1, 0), gameController.getGameSession().hero().currentTile());
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(new TileIndexCoordinates(0, 0)));
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(new TileIndexCoordinates(1, 0)));
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(remoteEnemyPos));
        Assertions.assertEquals(93, gameController.getGameSession().hero().currentHealth());
    }

    @Test
    public void tryToMoveFromWallsCageTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createWallsCageGameSession());
        TileIndexCoordinates freeTargetTile = new TileIndexCoordinates(4, 0);
        TileIndexCoordinates heroPosition = new TileIndexCoordinates(0, 0);
        gameController.handleTileClicked(freeTargetTile);
        Assertions.assertEquals(heroPosition, gameController.getGameSession().hero().currentTile());
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(heroPosition));
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(freeTargetTile));
        Assertions.assertEquals(100, gameController.getGameSession().hero().currentHealth());
    }

    @Test
    public void tryToHuntFromWallsCageTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createWallsCageGameSession());
        TileIndexCoordinates remoteEnemyPos = new TileIndexCoordinates(3, 0);
        gameController.handleTileClicked(remoteEnemyPos);
        Assertions.assertEquals(new TileIndexCoordinates(0, 0), gameController.getGameSession().hero().currentTile());
        Assertions.assertEquals(remoteEnemyPos, gameController.getGameSession().enemies().get(1).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().hero().currentHealth());
        Assertions.assertEquals(15, gameController.getGameSession().enemies().get(1).currentHealth());
    }

    @Test
    public void tryToMoveFromEnemiesCageTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createEnemiesCageGameSession());
        TileIndexCoordinates freeTargetTile = new TileIndexCoordinates(4, 0);
        TileIndexCoordinates heroPosition = new TileIndexCoordinates(0, 0);
        gameController.handleTileClicked(freeTargetTile);
        Assertions.assertEquals(heroPosition, gameController.getGameSession().hero().currentTile());
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(heroPosition));
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(freeTargetTile));
        Assertions.assertEquals(100, gameController.getGameSession().hero().currentHealth());
    }

    @Test
    public void tryToHuntFromEnemiesCageTest() {
        GameController gameController = new GameController(new DefaultGameEndSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createEnemiesCageGameSession());
        TileIndexCoordinates remoteEnemyPos = new TileIndexCoordinates(4, 4);
        gameController.handleTileClicked(remoteEnemyPos);
        Assertions.assertEquals(new TileIndexCoordinates(0, 0), gameController.getGameSession().hero().currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().hero().currentHealth());
        Assertions.assertEquals(new TileIndexCoordinates(0, 1), gameController.getGameSession().enemies().get(0).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().enemies().get(0).currentHealth());
        Assertions.assertEquals(new TileIndexCoordinates(1, 0), gameController.getGameSession().enemies().get(1).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().enemies().get(1).currentHealth());
        Assertions.assertEquals(new TileIndexCoordinates(1, 1), gameController.getGameSession().enemies().get(2).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().enemies().get(2).currentHealth());
        Assertions.assertEquals(remoteEnemyPos, gameController.getGameSession().enemies().get(3).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().enemies().get(3).currentHealth());
    }

    @Test
    public void loseTest() {
        GameController gameController = new GameController(new LoseSceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createLoseGameSession());
        TileIndexCoordinates oneStepToDie = new TileIndexCoordinates(1, 0);
        TileIndexCoordinates oldHeroPos = new TileIndexCoordinates(0, 0);
        gameController.handleTileClicked(oneStepToDie);
        Assertions.assertEquals(new TileIndexCoordinates(1, 0), gameController.getGameSession().hero().currentTile());
        Assertions.assertEquals(0, gameController.getGameSession().hero().currentHealth());
        Assertions.assertEquals(new TileIndexCoordinates(1, 1), gameController.getGameSession().enemies().get(0).currentTile());
        Assertions.assertEquals(100, gameController.getGameSession().enemies().get(0).currentHealth());
        Assertions.assertTrue(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(oneStepToDie));
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(oldHeroPos));
    }

    @Test
    public void victoryTest() {
        GameController gameController = new GameController(new VictorySceneHandler(), GameViewTest::new);
        gameController.launchGameSession(createVictoryGameSession());
        TileIndexCoordinates hitEnemyPos = new TileIndexCoordinates(1, 1);
        gameController.handleTileClicked(hitEnemyPos);
        Assertions.assertEquals(new TileIndexCoordinates(0, 0), gameController.getGameSession().hero().currentTile());
        Assertions.assertEquals(1, gameController.getGameSession().hero().currentHealth());
        Assertions.assertFalse(gameController.getGameSession().gameField().isThereSomebodyOnThisTile(hitEnemyPos));
    }
}
