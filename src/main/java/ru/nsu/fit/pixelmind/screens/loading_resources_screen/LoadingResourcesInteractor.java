package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.Assets;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.config.ResourcesConfig;
import ru.nsu.fit.pixelmind.config.TileTypeDeserializer;
import ru.nsu.fit.pixelmind.screens.game.GameInteractor;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoadingResourcesInteractor {

    @NotNull
    public static Resources parseResources() {
        return GameInteractor.parseResources(parseResourcesConfig());
    }

    @NotNull
    private static ResourcesConfig parseResourcesConfig() {
        Gson gson = new Gson();
        try (BufferedReader configReader = new BufferedReader(new FileReader(Path.of(Assets.RESOURCES).toFile()))) {
            return gson.fromJson(configReader, ResourcesConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static GameSessionConfig parseGameSessionConfig() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(TileType[][].class, new TileTypeDeserializer())
                .create();
        try (BufferedReader configReader = new BufferedReader(new FileReader(Path.of(Assets.DEFAULT_GAME_SESSION_CONFIG).toFile()))) {
            return gson.fromJson(configReader, GameSessionConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static List<SavedSessionEntry> parseSavesSessionsEntries() {
        //TODO: maybe null?
        ArrayList<SavedSessionEntry> csvRecords = new ArrayList<>();
        try (Reader reader = new FileReader(Assets.SAVES_SESSIONS_ENTRIES);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                csvRecords.add(new SavedSessionEntry(CharacterType.valueOf(csvRecord.get(0)), csvRecord.get(1), csvRecord.get(2)));
            }
        } catch (IOException ignored) {
        }
        return csvRecords;
    }
}
