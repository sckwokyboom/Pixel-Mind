package ru.nsu.fit.pixelmind.screens.game.game_screen;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.character.CharacterInteractor;
import ru.nsu.fit.pixelmind.characters.character.CharacterSpriteAssetName;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.config.ResourcesConfig;
import ru.nsu.fit.pixelmind.game_field.TileSetAssetName;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;
import ru.nsu.fit.pixelmind.game_field.tile_map.TileMapInteractor;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.Resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameInteractor {

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
