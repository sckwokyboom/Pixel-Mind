package ru.nsu.fit.pixelmind.tilemap;

import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.util.Builder;

public class GameFieldViewBuilder implements Builder<Canvas> {
    private final GameFieldModel model;
    private Canvas gameFieldCanvas;

    public GameFieldViewBuilder(GameFieldModel model) {
        this.model = model;
        gameFieldCanvas = new Canvas(16 * 32, 16 * 32);
    }

    @Override
    public Canvas build() {
        draw();
        gameFieldCanvas.getGraphicsContext2D().setImageSmoothing(false);
        gameFieldCanvas.setCache(true);
        gameFieldCanvas.setCacheHint(CacheHint.SPEED);
        gameFieldCanvas.setOnMousePressed(event -> {
            System.out.println((int) event.getX() / 32 + " " + (int) event.getY() / 32);
        });
        return gameFieldCanvas;
    }

    public void draw() {
        for (int i = 0; i < 256; i++) {
            gameFieldCanvas.getGraphicsContext2D().drawImage(model.tileMap().get(i).getView(), (int) i / 16 * 32, i % 16 * 32);
        }
    }
}
