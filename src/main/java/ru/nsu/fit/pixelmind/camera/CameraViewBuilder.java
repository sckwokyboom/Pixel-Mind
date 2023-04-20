package ru.nsu.fit.pixelmind.camera;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.transform.Affine;
import javafx.util.Builder;
import ru.nsu.fit.pixelmind.hero.HeroController;
import ru.nsu.fit.pixelmind.tilemap.GameFieldController;

public class CameraViewBuilder implements Builder<Region> {
    private static final double MIN_SCALE = 1.0;
    private static final double MAX_SCALE = 5.0;
    private final Canvas gameField;
    private final GameFieldController gameFieldController;
    private final HeroController heroController;
    private final CameraModel cameraModel;
    private double scale = 1.0;
    private double offsetX, offsetY;
    private double dragStartX, dragStartY;
    private double initTranslateX, initTranslateY;

    CameraViewBuilder(GameFieldController gameFieldController, HeroController heroController, CameraModel cameraModel) {
        this.gameField = gameFieldController.getView();
        this.gameFieldController = gameFieldController;
        this.heroController = heroController;
        this.cameraModel = cameraModel;
    }

    @Override
    public Region build() {
        cameraModel.isNeedToUpdateCenter().addListener((observable, oldValue, newValue) -> {
            System.out.println("Update Center!");
            cameraModel.isNeedToUpdateCenter().set(false);
            cameraModel.centerX().set(cameraModel.tileUserClickedXIndex().get());
            cameraModel.centerY().set(cameraModel.tileUserClickedYIndex().get());
        });
        Pane gamePane = new Pane();
        gamePane.getChildren().add(gameField);
        int centerOfGameFieldX = (int) gameField.getWidth() / 2;
        int centerOfGameFieldY = (int) gameField.getHeight() / 2;
        cameraModel.centerX().set(centerOfGameFieldX);
        cameraModel.centerY().set(centerOfGameFieldY);

        gameField.layoutXProperty().bind(gamePane.widthProperty().subtract(gameField.widthProperty()).divide(2));
        gameField.layoutYProperty().bind(gamePane.heightProperty().subtract(gameField.heightProperty()).divide(2));

        gameField.setOnMouseReleased(event -> {
            if (event.isStillSincePress()) {
                int curX = (int) (event.getX() / scale);
                int curY = (int) (event.getY() / scale);
                int tileSize = 32;
                System.out.println((curX / tileSize % 16) + " " + (curX / tileSize % 16));
                int tileCurX = (curX / tileSize % 16) * tileSize - ((int) (cameraModel.centerX().get() / scale) / tileSize % 16) * tileSize + ((int) cameraModel.centerX().get() / tileSize % 16) * tileSize;
                int tileCurY = (curY / tileSize % 16) * tileSize - ((int) (cameraModel.centerY().get() / scale) / tileSize % 16) * tileSize + ((int) cameraModel.centerY().get() / tileSize % 16) * tileSize;
                cameraModel.tileUserClickedXIndex().set(tileCurX);
                cameraModel.tileUserClickedYIndex().set(tileCurY);
                System.out.println("CENTER: " + cameraModel.centerX().get() + " " + cameraModel.centerY().get());
                System.out.println("TILE CLICKED: " + tileCurX + " " + tileCurY);
                updateCenterCoordinates();
                gameFieldController.redrawTileMap();
                gameField.getGraphicsContext2D().drawImage(heroController.getView(), cameraModel.tileUserClickedXIndex().get(), cameraModel.tileUserClickedYIndex().get());
            }
        });

        gamePane.setOnMousePressed(event -> {
            System.out.println("HEY!");
            if (event.isPrimaryButtonDown()) {
                dragStartX = event.getSceneX();
                dragStartY = event.getSceneY();
                initTranslateX = gameField.getGraphicsContext2D().getTransform().getTx();
                initTranslateY = gameField.getGraphicsContext2D().getTransform().getTy();
                System.out.println("Translate: " + initTranslateX + " " + initTranslateY);
            }
        });

        gamePane.setOnMouseReleased(event -> {
            offsetX = event.getSceneX() - dragStartX;
            offsetY = event.getSceneY() - dragStartY;
            offsetX /= scale;
            offsetY /= scale;
            System.out.println("RESULT OFFSET: " + offsetX + " " + offsetY);
            updateCenterCoordinates();
            System.out.println("CENTER UPDATE: " + cameraModel.centerX().get() + " " + cameraModel.centerY().get());
        });

        gamePane.setOnMouseDragged(event -> {
            if (event.isPrimaryButtonDown()) {
                Affine transform = new Affine();
                offsetX = event.getSceneX() - dragStartX;
                offsetY = event.getSceneY() - dragStartY;
                offsetX /= scale;
                offsetY /= scale;
                double newCenterX = calculateCenterCoordinateWithOffset(cameraModel.centerX().get(), offsetX, gameField.getWidth());
                double newCenterY = calculateCenterCoordinateWithOffset(cameraModel.centerY().get(), offsetY, gameField.getHeight());
                transform.appendTranslation(newCenterX, newCenterY);
                transform.appendScale(scale, scale);
                System.out.println("OFFSET: " + offsetX + " " + offsetY);
                System.out.println("CENTER:" + newCenterX + " " + newCenterY);
                transform.appendTranslation(-newCenterX, -newCenterY);
                gameField.getGraphicsContext2D().setTransform(transform);
                gameFieldController.redrawTileMap();
                gameField.getGraphicsContext2D().drawImage(heroController.getView(), cameraModel.tileUserClickedXIndex().get(), cameraModel.tileUserClickedYIndex().get());
            }
        });

        gamePane.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                scale /= 1.1;
            } else {
                scale *= 1.1;
            }
            scale = Math.max(MIN_SCALE, Math.min(scale, MAX_SCALE));
            Affine transform = new Affine();
            transform.appendTranslation(cameraModel.centerX().get(), cameraModel.centerY().get());
            transform.appendScale(scale, scale);
            transform.appendTranslation(-cameraModel.centerX().get(), -cameraModel.centerY().get());
            gameField.getGraphicsContext2D().setTransform(transform);
            // TODO: come up an idea for the scaling sizes of the tileMap.
            gameFieldController.redrawTileMap();
            gameField.getGraphicsContext2D().drawImage(heroController.getView(), cameraModel.tileUserClickedXIndex().get(), cameraModel.tileUserClickedYIndex().get());
        });
        return gamePane;
    }

    private double calculateCenterCoordinateWithOffset(double oldCenterCoordinate, double offset, double limit) {
        double newCenterCoordinate = oldCenterCoordinate;
        if (newCenterCoordinate - offset <= limit && newCenterCoordinate - offset >= 0) {
            newCenterCoordinate -= offset;
        } else {
            if (newCenterCoordinate - offset < 0) {
                newCenterCoordinate = 0;
            }
            if (newCenterCoordinate - offset > limit) {
                newCenterCoordinate = limit;
            }
        }
        return newCenterCoordinate;
    }

    private void updateCenterCoordinates() {
        double newCenterX = calculateCenterCoordinateWithOffset(cameraModel.centerX().get(), offsetX, gameField.getWidth());
        double newCenterY = calculateCenterCoordinateWithOffset(cameraModel.centerY().get(), offsetY, gameField.getHeight());
        cameraModel.centerX().set(newCenterX);
        cameraModel.centerY().set(newCenterY);
    }

}
