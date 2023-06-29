package ru.nsu.fit.pixelmind.screens.load_game_screen;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.GameSession;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapSize;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoadGameScreenInteractor {
    @NotNull
    public static GameSession parseGameSessionEntry(@NotNull SavedSessionEntry savedSessionEntry, @NotNull Resources resources) {
        Gson gson = new Gson();
        GameSessionForJson gameSessionForJson;
        try (BufferedReader configReader = new BufferedReader(new FileReader(Path.of(savedSessionEntry.path()).toFile()))) {
            gameSessionForJson = gson.fromJson(configReader, GameSessionForJson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TileType[][] tileMap = gameSessionForJson.gameField();
        //TODO: maybe load size in config?
        TileMapSize tileMapSize = new TileMapSize(tileMap.length, tileMap[0].length);
        TileMapController tileMapController = new TileMapController(tileMap, tileMapSize);
        tileMapController.setResources(resources.tileSets().get(gameSessionForJson.tileSetType()));
        CharacterController hero = new CharacterController(gameSessionForJson.character().characterType());
        hero.setResources(resources.sprites().get(gameSessionForJson.character().characterType()));
        hero.setCurrentHealth(gameSessionForJson.character().currentHealth());
        hero.setCurrentPosition(gameSessionForJson.character().currentPosition());
        hero.setDamageValue(gameSessionForJson.character().damageValue());
        List<CharacterController> enemies = new ArrayList<>();
        for (CharacterForJson enemyInfo : gameSessionForJson.enemies()) {
            CharacterController enemy = new CharacterController(enemyInfo.characterType());
            enemy.setResources(resources.sprites().get(enemyInfo.characterType()));
            enemy.setCurrentHealth(enemyInfo.currentHealth());
            enemy.setCurrentPosition(enemyInfo.currentPosition());
            enemy.setDamageValue(enemyInfo.damageValue());
            enemies.add(enemy);
        }
        return new GameSession(tileMapController, hero, enemies);
    }
}
