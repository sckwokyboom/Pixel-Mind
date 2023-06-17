package ru.nsu.fit.pixelmind.game;

import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.game_field.TileType;

import java.util.List;
import java.util.Map;

public record Resources(Map<String, List<Map<TileType,Image>>> tileMaps, Map<String, List<Image>> sprites) {
}
