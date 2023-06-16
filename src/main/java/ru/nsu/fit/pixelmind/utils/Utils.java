package ru.nsu.fit.pixelmind.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import ru.nsu.fit.pixelmind.game_field.GameFieldInteractor;

import java.util.Objects;

public class Utils {
    public static Image parseImageByPixels(String url, int startOffsetX, int startOffsetY, int height, int width) {
        // Creating a writable image
        WritableImage image = new WritableImage(width, height);
        try {
            Image resourceImage = new Image(Objects.requireNonNull(GameFieldInteractor.class.getResource(url)).toString());
            int fullResourceImageHeight = resourceImage.heightProperty().intValue();
            int fullResourceImageWidth = resourceImage.widthProperty().intValue();
            // Reading color from the loaded image
            PixelReader pixelReader = resourceImage.getPixelReader();

            // Getting the pixel writer
            PixelWriter writer = image.getPixelWriter();
            // Reading the color of the image
            for (int y = startOffsetY; y < startOffsetY + height; y++) {
                for (int x = startOffsetX; x < startOffsetX + width; x++) {
                    // Retrieving the color of the pixel of the loaded image
                    Color color = pixelReader.getColor(x, y);

                    // Setting the color to the writable image
                    writer.setColor(x - startOffsetX, y - startOffsetY, color);
                }
            }
            // Setting the view for the writable image
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Image loading error occurred: " + e.getMessage());
        }
        return image;
    }
}
