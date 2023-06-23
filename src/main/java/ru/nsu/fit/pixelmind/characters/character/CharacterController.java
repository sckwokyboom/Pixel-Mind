package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.characters.ActionType;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.game_field.tile.TileIndexCoordinates;

import java.util.Map;

public class CharacterController {
    private final CharacterView heroView;
    private final CharacterModel model;

    public CharacterController(@NotNull CharacterType characterType, @NotNull Map<SpriteType, Image> imageResources) {
        this.model = new CharacterModel(characterType);
        heroView = new CharacterView(imageResources);
    }

    public int currentHealth() {
        return model.healthPoints();
    }

    public void setCurrentHealth(int newHealthCurrent) {
        model.setHealthPoints(newHealthCurrent);
    }

    public int damageValue() {
        return model.damageValue();
    }

    public void setDamageValue(int damageValue) {
        model.setDamageValue(damageValue);
    }

    public void hit(int damage) {
        model.setHealthPoints(Math.max(currentHealth() - damage, 0));
    }

    public void setCurrentPosition(TileIndexCoordinates newPosition) {
        model.setCurrentPosition(newPosition);
    }

    @Nullable
    public CharacterController huntingTarget() {
        return model.huntingTarget();
    }

    public void huntEnemy(@Nullable CharacterController enemy) {
        if (enemy != null) {
            model.setHuntingTarget(enemy);
        }
    }

    @NotNull
    public TileIndexCoordinates currentTile() {
        return model.currentPosition();
    }

    @NotNull
    public TileIndexCoordinates targetTile() {
        return model.targetTile();
    }

    public void setTargetTile(@NotNull TileIndexCoordinates newTarget) {
        model.setTargetTile(newTarget);
    }

    public void setAnimationInfoOnThisStep(@NotNull TileIndexCoordinates current, @NotNull TileIndexCoordinates target, @NotNull ActionType action) {
        heroView.setCurrentPositionOnThisStep(current);
        heroView.setActionTargetTileOnThisStep(target);
        heroView.setActionTypeOnThisStep(action);
    }

    public CharacterView getView() {
        return heroView;
    }

    public CharacterType characterType() {
        return model.characterType();
    }
}
