package ru.nsu.fit.pixelmind.screens.game.character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.screens.game.game_field.tile.TileIndexCoordinates;

public class CharacterModel {
    private final CharacterType characterType;
    // CR: move to config
    //TODO: don't agree, thinking...
    private int damageValue = 15;
    private int healthPoints = 100;
    private TileIndexCoordinates currentPosition;
    private TileIndexCoordinates targetTile;
    private CharacterController huntingTarget;

    public CharacterModel(@NotNull CharacterType characterType) {
        this.characterType = characterType;
    }

    public int healthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int newHealthPoints) {
        healthPoints = newHealthPoints;
    }

    public int damageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    @NotNull
    public TileIndexCoordinates currentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(@NotNull TileIndexCoordinates currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TileIndexCoordinates targetTile() {
        return targetTile;
    }

    public void setTargetTile(@NotNull TileIndexCoordinates targetTile) {
        this.targetTile = targetTile;
    }

    @Nullable
    public CharacterController huntingTarget() {
        return huntingTarget;
    }

    public void setHuntingTarget(@Nullable CharacterController huntingTarget) {
        this.huntingTarget = huntingTarget;
    }

    @NotNull
    public CharacterType characterType() {
        return characterType;
    }
}
