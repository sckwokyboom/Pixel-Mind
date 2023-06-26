package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

public record SavedSessionEntry(@NotNull CharacterType heroType,
                                @NotNull String date,
                                @NotNull String path) {
}
