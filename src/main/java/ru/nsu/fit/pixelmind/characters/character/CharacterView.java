package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.ActionType;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.game_field.tile.TileIndexCoordinates;

import java.util.Map;

public class CharacterView implements Builder<Image> {
    private TileIndexCoordinates currentPositionOnThisStep;
    private TileIndexCoordinates actionTargetTileOnThisStep;
    @NotNull
    private Map<SpriteType, Image> characterSprites;
    @NotNull
    private ActionType actionTypeOnThisStep = ActionType.WAIT;
    @NotNull
    private Image currentSprite;
    @NotNull
    private SpriteType currentSpriteType = SpriteType.REGULAR_SPRITE;


    CharacterView(Map<SpriteType, Image> characterSprites) {
        this.characterSprites = characterSprites;
        currentSprite = characterSprites.get(currentSpriteType);
    }

    public void setCurrentPositionOnThisStep(TileIndexCoordinates currentPositionOnThisStep) {
        this.currentPositionOnThisStep = currentPositionOnThisStep;
    }

    public void setActionTargetTileOnThisStep(TileIndexCoordinates actionTargetTileOnThisStep) {
        this.actionTargetTileOnThisStep = actionTargetTileOnThisStep;
    }

    public void setCurrentSpriteType(@NotNull SpriteType currentSpriteType) {
        this.currentSpriteType = currentSpriteType;
        this.currentSprite = characterSprites.get(currentSpriteType);
    }

    public void setCurrentSprite(@NotNull Image currentSprite) {
        this.currentSprite = currentSprite;
    }

    public void setActionTypeOnThisStep(@NotNull ActionType actionTypeOnThisStep) {
        this.actionTypeOnThisStep = actionTypeOnThisStep;
    }

    public void setCharacterSprites(@NotNull Map<SpriteType, Image> characterSprites) {
        this.characterSprites = characterSprites;
    }

    public TileIndexCoordinates currentTile() {
        return currentPositionOnThisStep;
    }

    public TileIndexCoordinates targetTile() {
        return actionTargetTileOnThisStep;
    }

    public Map<SpriteType, Image> characterSprites() {
        return characterSprites;
    }

    public SpriteType currentSpriteType() {
        return currentSpriteType;
    }

    public Image currentSprite() {
        return currentSprite;
    }

    public void chooseSprite(SpriteType spriteType) {
        setCurrentSpriteType(spriteType);
    }

    public void nextAttackSprite() {
        if ((currentSpriteType.ordinal() + 1) > 5 || currentSpriteType.ordinal() < 2) {
            setCurrentSpriteType(SpriteType.values()[2]);
            setCurrentSprite(characterSprites.get(SpriteType.values()[2]));
            return;
        }
        setCurrentSpriteType(SpriteType.values()[currentSpriteType.ordinal() + 1]);
        setCurrentSprite(characterSprites.get(currentSpriteType));
    }

    @Override
    @NotNull
    public Image build() {
        return currentSprite;
    }

    @NotNull
    public ActionType actionTypeOnThisStep() {
        return actionTypeOnThisStep;
    }
}
