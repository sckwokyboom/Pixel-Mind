package ru.nsu.fit.pixelmind.screens.game;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterController;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile_map.TileMapController;

import java.util.List;

public record GameSession(@NotNull TileMapController gameField,
                          @NotNull CharacterController hero,
                          @NotNull List<CharacterController> enemies) {
}
