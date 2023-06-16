package ru.nsu.fit.pixelmind.characters.character;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.characters.SpriteType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class CharacterModel {
    private int damageValue = 15;
    private final IntegerProperty healthPoints = new SimpleIntegerProperty(100);
    private final ArrayList<Image> characterSprites = new ArrayList<>(10);
    private final IntegerProperty currentPositionIndexX = new SimpleIntegerProperty();
    private final IntegerProperty currentPositionIndexY = new SimpleIntegerProperty();
    private final IntegerProperty targetIndexX = new SimpleIntegerProperty();
    private final IntegerProperty targetIndexY = new SimpleIntegerProperty();

    private final BooleanProperty routeHasChanged = new SimpleBooleanProperty(false);
    private final Deque<Pair<Integer, Integer>> route = new ArrayDeque<>();
    private final BooleanProperty coordinateChanged = new SimpleBooleanProperty(false);
    private EnemyController huntedEnemy;
    private SpriteType currentSpriteType = SpriteType.REGULAR_SPRITE;
    private Image currentSprite;

    public IntegerProperty currentPositionIndexXProperty() {
        return currentPositionIndexX;
    }

    public IntegerProperty currentPositionIndexYProperty() {
        return currentPositionIndexY;
    }

    public IntegerProperty targetIndexXProperty() {
        return targetIndexX;
    }

    public IntegerProperty targetIndexYProperty() {
        return targetIndexY;
    }

    public BooleanProperty coordinateChanged() {
        return coordinateChanged;
    }

    public BooleanProperty routeHasChanged() {
        return routeHasChanged;
    }

    public IntegerProperty healthPointsProperty() {
        return healthPoints;
    }

    public ArrayList<Image> getCharacterSprites() {
        return characterSprites;
    }

    public Deque<Pair<Integer, Integer>> route() {
        return route;
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
}
