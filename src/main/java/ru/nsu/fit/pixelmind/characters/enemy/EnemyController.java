package ru.nsu.fit.pixelmind.characters.enemy;

import javafx.util.Pair;
import ru.nsu.fit.pixelmind.characters.character.CharacterController;

public class EnemyController extends CharacterController {

//    private final CharacterInteractor interactor;
//    private final Builder<Image> viewBuilder;
//    private final CharacterModel model;

    public EnemyController(String spriteType, Pair<Integer, Integer> initPos) {
        super(spriteType);
        this.getTileIndexXProperty().set(initPos.getKey());
        this.getTileIndexYProperty().set(initPos.getValue());
//        model.currentPositionIndexYProperty().set(initPos.getValue());
//        this.model = new CharacterModel();
//        interactor = new CharacterInteractor(model);
//        interactor.loadSprites(spriteType);
//        viewBuilder = new CharacterViewBuilder(model);
    }

//    public Image getView() {
//        return viewBuilder.build();
//    }
//
//    public IntegerProperty getTileIndexXProperty() {
//        return model.currentPositionIndexXProperty();
//    }
//
//    public IntegerProperty getTileIndexYProperty() {
//        return model.currentPositionIndexYProperty();
//    }
//
//    public IntegerProperty getTargetTileIndexXProperty() {
//        return model.targetIndexXProperty();
//    }
//
//    public IntegerProperty getTargetTileIndexYProperty() {
//        return model.targetIndexYProperty();
//    }
}
