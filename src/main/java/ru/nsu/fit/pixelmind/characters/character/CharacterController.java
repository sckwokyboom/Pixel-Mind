package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import org.jetbrains.annotations.Nullable;
import ru.nsu.fit.pixelmind.characters.ActionType;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.game_field.tile.TileIndexCoordinates;

import java.util.Map;

public class CharacterController {
    // private final CharacterInteractor interactor;
    private final CharacterView heroView;
    private final CharacterModel model;

    public CharacterController(CharacterType characterType, Map<SpriteType, Image> imageResources) {
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

    public CharacterController getHuntedEnemy() {
        return model.huntedEnemy();
    }

    public void huntEnemy(@Nullable CharacterController enemy) {
        if (enemy != null) {
            model.setHuntedEnemy(enemy);
        }
    }


    public TileIndexCoordinates currentPosition() {
        return model.currentPosition();
    }

    public TileIndexCoordinates targetTile() {
        return model.targetTile();
    }

    public void setTargetTile(TileIndexCoordinates newTarget) {
        model.setTargetTile(newTarget);
    }

    public void setAnimationInfoOnThisStep(TileIndexCoordinates current, TileIndexCoordinates target, ActionType action) {
        heroView.setCurrentPositionOnThisStep(current);
        heroView.setActionTargetTileOnThisStep(target);
        heroView.setActionTypeOnThisStep(action);
    }

    public CharacterView getView() {
        return heroView;
    }
}
