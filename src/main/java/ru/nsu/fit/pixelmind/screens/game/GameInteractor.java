package ru.nsu.fit.pixelmind.screens.game;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.Assets;
import ru.nsu.fit.pixelmind.config.ResourcesConfig;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterInteractor;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.character.SpriteType;
import ru.nsu.fit.pixelmind.screens.game.game_field.TileSetAssetName;
import ru.nsu.fit.pixelmind.screens.game.game_field.TileSetType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapInteractor;
import ru.nsu.fit.pixelmind.screens.load_game_screen.CharacterForJson;
import ru.nsu.fit.pixelmind.screens.load_game_screen.GameSessionForJson;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class GameInteractor {
    private final @NotNull GameModel gameModel;

    GameInteractor(@NotNull GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void saveCurrentGameSession() {
        if (gameModel.gameSession() == null) {
            return;
        }
        Gson gson = new Gson();
        TileType[][] gameField = new TileType[gameModel.gameSession().gameField().getHeight()][gameModel.gameSession().gameField().getWidth()];
        for (int i = 0; i < gameModel.gameSession().gameField().getHeight(); i++) {
            for (int j = 0; j < gameModel.gameSession().gameField().getWidth(); j++) {
                gameField[i][j] = gameModel.gameSession().gameField().getTileMap()[j][i].getType();
            }
        }
        GameSessionForJson gameSessionForJson = new GameSessionForJson(
                gameField,
                new CharacterForJson(gameModel.gameSession().hero().currentTile(),
                        gameModel.gameSession().hero().characterType(),
                        gameModel.gameSession().hero().currentHealth(),
                        gameModel.gameSession().hero().damageValue()),
                gameModel.gameSession().enemies().stream().map(enemy -> new CharacterForJson(
                        enemy.currentTile(), enemy.characterType(), enemy.currentHealth(), enemy.damageValue()
                )).toList(),
                TileSetType.REGULAR
        );

        String gameSessionJson = gson.toJson(gameSessionForJson);
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-H-m");
        String formattedDateTime = currentDate.format(formatter);
        try {
            Path savesDir = Paths.get("src/main/resources/saves");
            Path currentSaveDir = savesDir.resolve(formattedDateTime);
            Path gameSessionPath = currentSaveDir.resolve("gameSession.json");
            if (Files.notExists(gameSessionPath)) {
                Files.createDirectory(currentSaveDir);
                Files.createFile(gameSessionPath);
            }
            Files.write(gameSessionPath, gameSessionJson.getBytes(), StandardOpenOption.CREATE);
            gameModel.newSaves().add(new SavedSessionEntry(gameModel.gameSession().hero().characterType(), formattedDateTime, gameSessionPath.toString()));
        } catch (IOException e) {
            log.error("Unable to save current game session", e);
        }
    }

    public void dumpNewSaves() {
        if (gameModel.newSaves().isEmpty()) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(Assets.SAVES_SESSIONS_ENTRIES, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            System.out.println(gameModel.newSaves());
            for (SavedSessionEntry savedSessionEntry : gameModel.newSaves()) {
                csvPrinter.printRecord(savedSessionEntry.heroType(), savedSessionEntry.date(), savedSessionEntry.path());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            log.error("Unable to save game sessions entries", e);
        }
    }

    @NotNull
    public static Resources parseResources(@NotNull ResourcesConfig resourcesConfig) {
        return new Resources(parseAvatars(resourcesConfig.avatarsPath(), resourcesConfig.avatars()), parseSprites(resourcesConfig.sprites()), parseTileSets(resourcesConfig.tileSets()));
    }

    @NotNull
    private static Map<CharacterType, Map<SpriteType, Image>> parseSprites(@NotNull CharacterSpriteAssetName[] assets) {
        Map<@NotNull CharacterType, @NotNull Map<SpriteType, Image>> sprites = new HashMap<>();
        for (CharacterSpriteAssetName asset : assets) {
            Map<SpriteType, Image> characterSprites = CharacterInteractor.parseSprites(asset.spritesAsset());
            sprites.put(asset.characterType(), Collections.unmodifiableMap(characterSprites));
        }
        return Collections.unmodifiableMap(sprites);
    }

    @NotNull
    private static Map<CharacterType, Image> parseAvatars(@NotNull String avatarsPath, @NotNull CharacterType[] avatarsTypes) {
        return Collections.unmodifiableMap(CharacterInteractor.parseAvatars(avatarsPath, avatarsTypes));
    }

    @NotNull
    private static Map<TileSetType, Map<TileType, Image>> parseTileSets(@NotNull TileSetAssetName[] tileSetAssetsNames) {
        Map<TileSetType, Map<TileType, Image>> tileMaps = new HashMap<>();
        for (TileSetAssetName tileSetAssetName : tileSetAssetsNames) {
            Map<TileType, Image> tileSet = TileMapInteractor.parseTileSet(tileSetAssetName.tileSetAsset());
            tileMaps.put(tileSetAssetName.tileSetType(), Collections.unmodifiableMap(tileSet));
        }
        return Collections.unmodifiableMap(tileMaps);
    }
}
