package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.ArrayList;

public class CharacterViewBuilder implements Builder<Image> {
    private final CharacterModel model;
    private TileIndexCoordinates currentPosition;
    private TileIndexCoordinates targetTile;
    private final ArrayList<Image> characterSprites = new ArrayList<>(10);
    private Image currentSprite;
    private SpriteType currentSpriteType = SpriteType.REGULAR_SPRITE;


    public CharacterViewBuilder(CharacterModel model) {
        this.model = model;
    }

    public TileIndexCoordinates currentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(TileIndexCoordinates currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TileIndexCoordinates targetTile() {
        return targetTile;
    }

    public void setTargetTile(TileIndexCoordinates targetTile) {
        this.targetTile = targetTile;
    }

    public ArrayList<Image> getCharacterSprites() {
        return characterSprites;
    }

    public SpriteType getCurrentSpriteType() {
        return currentSpriteType;
    }

    public void setCurrentSpriteType(SpriteType currentSpriteType) {
        this.currentSpriteType = currentSpriteType;
    }

    public Image getCurrentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(Image currentSprite) {
        this.currentSprite = currentSprite;
    }

    public void chooseSprite(SpriteType spriteType) {
        setCurrentSpriteType(spriteType);
    }

    public void nextAttackSprite() {
        if ((getCurrentSpriteType().ordinal() + 1) > 5 || getCurrentSpriteType().ordinal() < 2) {
            setCurrentSpriteType(SpriteType.values()[2]);
            setCurrentSprite(getCharacterSprites().get(2));
            return;
        }
        setCurrentSpriteType(SpriteType.values()[getCurrentSpriteType().ordinal() + 1]);
        setCurrentSprite(getCharacterSprites().get(getCurrentSpriteType().ordinal()));
    }

    @Override
    public Image build() {
        return getCharacterSprites().get(0);
    }
    public Image getView() {
        return build();
    }
}
