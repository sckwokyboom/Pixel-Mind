package ru.nsu.fit.pixelmind.tilemap;

import javafx.scene.canvas.Canvas;

public class GameFieldController {
    private final GameFieldViewBuilder viewBuilder;
    private final GameFieldInteractor interactor;

    public GameFieldController() {
        GameFieldModel model = new GameFieldModel();
        interactor = new GameFieldInteractor(model);
        interactor.tileSetFill();
        model.updateLevelTileMap(interactor.levelCreate());
        viewBuilder = new GameFieldViewBuilder(model);
    }

    public Canvas getView() {
        return viewBuilder.build();
    }

    public void setHeroToPos() {
    }

    public void redrawTileMap() {
        viewBuilder.draw();
    }

}
