package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.tile.TileType;

import java.util.Map;

public record Resources(@NotNull Map<CharacterType, Image> avatars,
                        @NotNull Map<@NotNull CharacterType, Map<SpriteType, Image>> sprites,
                        @NotNull Map<TileSetType, Map<TileType, Image>> tileSets) {
}
