package ru.nsu.fit.pixelmind.characters.character;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

import java.util.ArrayList;

public class CharacterModel {
    private int damageValue = 15;
    private int healthPoints = 100;
    private final ArrayList<Image> characterSprites = new ArrayList<>(10);
    private final IntegerProperty currentPositionIndexX = new SimpleIntegerProperty();
    private final IntegerProperty currentPositionIndexY = new SimpleIntegerProperty();
    private TileIndexCoordinates currentPosition;
    private TileIndexCoordinates targetTile;
    private EnemyController huntedEnemy;
    private SpriteType currentSpriteType = SpriteType.REGULAR_SPRITE;
    private Image currentSprite;

    public int healthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int newHealthPoints) {
        healthPoints = newHealthPoints;
    }

    public ArrayList<Image> getCharacterSprites() {
        return characterSprites;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    public EnemyController getHuntedEnemy() {
        return huntedEnemy;
    }

    public void setHuntedEnemy(EnemyController huntedEnemy) {
        this.huntedEnemy = huntedEnemy;
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
}
