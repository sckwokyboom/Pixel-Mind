package ru.nsu.fit.pixelmind.characters.hero;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class HeroModel {
    private int damageValue = 15;
    private final IntegerProperty healthPoints = new SimpleIntegerProperty(100);
    private EnemyController huntedEnemy;
    private final ArrayList<Image> heroSprites = new ArrayList<>(10);
    private SpriteType spriteType = SpriteType.REGULAR_SPRITE;
    private Image currentSprite;
    private final IntegerProperty currentPositionIndexX = new SimpleIntegerProperty();
    private final IntegerProperty currentPositionIndexY = new SimpleIntegerProperty();
    private final IntegerProperty targetIndexX = new SimpleIntegerProperty();
    private final IntegerProperty targetIndexY = new SimpleIntegerProperty();

    private final BooleanProperty routeHasChanged = new SimpleBooleanProperty(false);
    private final Deque<Pair<Integer, Integer>> route = new ArrayDeque<>();

    private final BooleanProperty coordinateChanged = new SimpleBooleanProperty(false);

    public int currentPositionIndexX() {
        return currentPositionIndexX.get();
    }

    public int currentPositionIndexY() {
        return currentPositionIndexY.get();
    }

    public IntegerProperty currentPositionIndexXProperty() {
        return currentPositionIndexX;
    }

    public IntegerProperty currentPositionIndexYProperty() {
        return currentPositionIndexY;
    }

    public int targetIndexX() {
        return targetIndexX.get();
    }

    public int targetIndexY() {
        return targetIndexY.get();
    }

    public IntegerProperty targetIndexXProperty() {
        return targetIndexX;
    }

    public IntegerProperty targetIndexYProperty() {
        return targetIndexY;
    }

    public ArrayList<Image> heroSprites() {
        return heroSprites;
    }

    public BooleanProperty coordinateChanged() {
        return coordinateChanged;
    }

    public Deque<Pair<Integer, Integer>> route() {
        return route;
    }

    public BooleanProperty routeHasChanged() {
        return routeHasChanged;
    }

    public void chooseSprite(SpriteType spriteType) {
        currentSprite = heroSprites.get(spriteType.ordinal());
    }

    public EnemyController getHuntedEnemy() {
        return huntedEnemy;
    }

    public void setHuntedEnemy(EnemyController huntedEnemy) {
        this.huntedEnemy = huntedEnemy;
    }

    public int getHealthPoints() {
        return healthPoints.get();
    }

    public IntegerProperty healthPointsProperty() {
        return healthPoints;
    }

    public Image currentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(Image newSprite) {
        currentSprite = newSprite;
    }


    public SpriteType spriteType() {
        return spriteType;
    }

    public void setSpriteType(SpriteType spriteType) {
        this.spriteType = spriteType;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }
}
