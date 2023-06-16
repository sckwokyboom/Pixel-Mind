package ru.nsu.fit.pixelmind.characters.character;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.Image;
import javafx.util.Builder;
import javafx.util.Pair;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.characters.SpriteType;

import java.util.Deque;

public class CharacterController {
    private final CharacterInteractor interactor;
    private final Builder<Image> viewBuilder;
    private final CharacterModel model;

    public CharacterController(String spriteType) {
        this.model = new CharacterModel();
        interactor = new CharacterInteractor(model);
        interactor.loadSprites(spriteType);
        viewBuilder = new CharacterViewBuilder(model);
    }

    public Image getView() {
        return viewBuilder.build();
    }

    public IntegerProperty getTileIndexXProperty() {
        return model.currentPositionIndexXProperty();
    }

    public IntegerProperty getTileIndexYProperty() {
        return model.currentPositionIndexYProperty();
    }

    public IntegerProperty getTargetTileIndexXProperty() {
        return model.targetIndexXProperty();
    }

    public IntegerProperty getTargetTileIndexYProperty() {
        return model.targetIndexYProperty();
    }

    public BooleanProperty getCoordinateChanged() {
        return model.coordinateChanged();
    }

    public Deque<Pair<Integer, Integer>> getRoute() {
        return model.route();
    }

    public BooleanProperty routeHasChanged() {
        return model.routeHasChanged();
    }

    public IntegerProperty getCurrentHealth() {
        return model.healthPointsProperty();
    }

    public int getDamageValue() {
        return model.getDamageValue();
    }

    public void hit(int damage) {
        getCurrentHealth().set(Math.max(getCurrentHealth().get() - damage, 0));
    }

    public void setHeroToPos(Pair<Integer, Integer> target) {
        model.currentPositionIndexXProperty().set(target.getKey());
        model.currentPositionIndexYProperty().set(target.getValue());
    }

    public EnemyController getHuntedEnemy() {
        return model.getHuntedEnemy();
    }

    public void huntEnemy(EnemyController enemy) {
        model.setHuntedEnemy(enemy);
    }

    public void chooseSprite(SpriteType spriteType) {
        model.setCurrentSpriteType(spriteType);
    }

    public void nextAttackSprite() {
        if ((model.getCurrentSpriteType().ordinal() + 1) > 5 || model.getCurrentSpriteType().ordinal() < 2) {
            model.setCurrentSpriteType(SpriteType.values()[2]);
            model.setCurrentSprite(model.getCharacterSprites().get(2));
            return;
        }
        model.setCurrentSpriteType(SpriteType.values()[model.getCurrentSpriteType().ordinal() + 1]);
        model.setCurrentSprite(model.getCharacterSprites().get(model.getCurrentSpriteType().ordinal()));
    }
}
