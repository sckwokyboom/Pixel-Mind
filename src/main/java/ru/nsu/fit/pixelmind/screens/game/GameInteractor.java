package ru.nsu.fit.pixelmind.screens.game;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.character.CharacterInteractor;
import ru.nsu.fit.pixelmind.characters.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.GameFieldInteractor;
import ru.nsu.fit.pixelmind.game_field.TileSetAssetName;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.TileType;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;
import ru.nsu.fit.pixelmind.config.ResourcesConfig;

import java.util.*;

public class GameInteractor {

    @NotNull
    public static Resources parseResources(@NotNull ResourcesConfig resourcesConfig) {
        return new Resources(parseSprites(resourcesConfig.sprites()), parseTileSets(resourcesConfig.tileSets()));
    }

    @NotNull
    private static Map<CharacterType, List<Image>> parseSprites(@NotNull CharacterSpriteAssetName[] assets) {
        Map<@NotNull CharacterType, @NotNull List<@NotNull Image>> sprites = new HashMap<>();
        for (CharacterSpriteAssetName asset : assets) {
            ArrayList<Image> characterSprites = CharacterInteractor.parseSprites(asset.spritesAsset());
            sprites.put(asset.characterType(), Collections.unmodifiableList(characterSprites));
        }
        return Collections.unmodifiableMap(sprites);
    }

    @NotNull
    private static Map<TileSetType, Map<TileType, Image>> parseTileSets(@NotNull TileSetAssetName[] tileSetAssetsNames) {
        Map<TileSetType, Map<TileType, Image>> tileMaps = new HashMap<>();
        for (TileSetAssetName tileSetAssetName : tileSetAssetsNames) {
            Map<TileType, Image> tileSet = GameFieldInteractor.parseTileSet(tileSetAssetName.tileSetAsset());
            tileMaps.put(tileSetAssetName.tileSetType(), Collections.unmodifiableMap(tileSet));
        }
        return Collections.unmodifiableMap(tileMaps);
    }
}