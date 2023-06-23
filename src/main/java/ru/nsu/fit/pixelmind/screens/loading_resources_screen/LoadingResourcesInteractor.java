package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.config.Assets;
import ru.nsu.fit.pixelmind.config.GameSessionConfig;
import ru.nsu.fit.pixelmind.config.ResourcesConfig;
import ru.nsu.fit.pixelmind.config.TileTypeDeserializer;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.screens.game.game_screen.GameInteractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class LoadingResourcesInteractor {


    private final LoadingResourcesModel model;

    LoadingResourcesInteractor(LoadingResourcesModel model) {
        this.model = model;
    }

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

}
