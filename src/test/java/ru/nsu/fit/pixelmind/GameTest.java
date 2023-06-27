package ru.nsu.fit.pixelmind;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.pixelmind.screens.MainController;
import ru.nsu.fit.pixelmind.screens.game.GameController;
import ru.nsu.fit.pixelmind.screens.game.GameModel;
import ru.nsu.fit.pixelmind.screens.game.GameSession;
import ru.nsu.fit.pixelmind.screens.game.GameView;
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

    private GameSession createGameSession() {
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
        enemy1.setCurrentPosition(new TileIndexCoordinates(1, 1));
        enemy1.setCurrentHealth(100);
        enemy1.setDamageValue(1);
        enemies.add(enemy1);
        return new GameSession(gameField, hero, enemies);
    }

    static class GameEndSceneSwitcher implements MainController.GameEndSceneHandler {
        @Override
        public void switchToGameEndScene(@NotNull String gameResult, @NotNull CharacterType heroType, int gameScore) {
        }
    }

    @Test
    public void testSimpleStep() {
        GameController gameController = new GameController(new GameEndSceneSwitcher(), GameViewTest::new);
        gameController.launchGameSession(createGameSession());
        gameController.handleTileClicked(new TileIndexCoordinates(0, 1));
        Assertions.assertEquals(gameController.getGameSession().hero().currentTile(), new TileIndexCoordinates(0, 1));
    }

    @Test
    public void testCompoundStep() {
        GameController gameController = new GameController(new GameEndSceneSwitcher(), GameViewTest::new);
        gameController.launchGameSession(createGameSession());
        gameController.handleTileClicked(new TileIndexCoordinates(4, 4));
        Assertions.assertEquals(gameController.getGameSession().hero().currentTile(), new TileIndexCoordinates(4, 4));
    }

}
