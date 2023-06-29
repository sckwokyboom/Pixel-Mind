package ru.nsu.fit.pixelmind.screens.new_game_screen;

import org.jetbrains.annotations.NotNull;

public class NewGameScreenModel {
    private UserModifications userModifications;

    public UserModifications userModifications() {
        return userModifications;
    }

    public void setUserModifications(@NotNull UserModifications userModifications) {
        this.userModifications = userModifications;
    }
}
