package ru.nsu.fit.pixelmind.game_field;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.util.Builder;

public class GameFieldViewBuilder implements Builder<Canvas> {
    private final GameFieldModel model;
    private final Canvas gameFieldCanvas;

    public GameFieldViewBuilder(GameFieldModel model) {
        this.model = model;
        gameFieldCanvas = new Canvas(16 * 32, 16 * 32);
    }

    @Override
    public Canvas build() {
        System.out.println("Redraw tile map in GameField");
        draw();
        gameFieldCanvas.getGraphicsContext2D().setImageSmoothing(false);
        gameFieldCanvas.setCache(true);
        gameFieldCanvas.setCacheHint(CacheHint.SPEED);
        return gameFieldCanvas;
    }

    public void draw() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                gameFieldCanvas.getGraphicsContext2D().drawImage(model.tileMap()[i][j].getView(), i * 32, j * 32);
            }
        }
    }
}
