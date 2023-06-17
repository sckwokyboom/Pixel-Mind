package ru.nsu.fit.pixelmind.characters.character;

import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

public class CharacterModel {
    private int damageValue = 15;
    private int healthPoints = 100;
    private TileIndexCoordinates currentPosition;
    private TileIndexCoordinates targetTile;
    private EnemyController huntedEnemy;

    public int healthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int newHealthPoints) {
        healthPoints = newHealthPoints;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
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

    public EnemyController getHuntedEnemy() {
        return huntedEnemy;
    }

    public void setHuntedEnemy(EnemyController huntedEnemy) {
        this.huntedEnemy = huntedEnemy;
    }
}
