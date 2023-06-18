package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;
import ru.nsu.fit.pixelmind.game_field.TileSetType;
import ru.nsu.fit.pixelmind.game_field.TileType;

import java.util.Map;

public record Resources(Map<CharacterType, Map<SpriteType, Image>> sprites,
                        Map<TileSetType, Map<TileType, Image>> tileSets) {
}
