package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.characters.SpriteType;
import ru.nsu.fit.pixelmind.characters.enemy.EnemyController;
import ru.nsu.fit.pixelmind.game_field.TileIndexCoordinates;

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

    public int currentHealth() {
        return model.healthPoints();
    }

    public void setCurrentHealth(int newHealthCurrent) {
        model.setHealthPoints(newHealthCurrent);
    }

    public int getDamageValue() {
        return model.getDamageValue();
    }

    public void hit(int damage) {
        setCurrentHealth(Math.max(currentHealth() - damage, 0));
    }

    public void setCurrentPosition(TileIndexCoordinates newPosition) {
        model.setCurrentPosition(newPosition);
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

    public TileIndexCoordinates currentPosition() {
        return model.currentPosition();
    }

    public TileIndexCoordinates targetTile() {
        return model.targetTile();
    }

    public void setTargetTile(TileIndexCoordinates newTarget) {
        model.setTargetTile(newTarget);
    }
}
