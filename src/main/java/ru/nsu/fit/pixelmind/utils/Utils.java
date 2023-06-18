package ru.nsu.fit.pixelmind.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;

public class Utils {
    @NotNull
    public static Image parseImageByPixels(@NotNull String url, int startOffsetX, int startOffsetY, int height, int width) {
        WritableImage image = new WritableImage(width, height);
        try {
            Image resourceImage = new Image(Objects.requireNonNull(Path.of(url).toUri()).toString());
            PixelReader pixelReader = resourceImage.getPixelReader();

            PixelWriter writer = image.getPixelWriter();
            for (int y = startOffsetY; y < startOffsetY + height; y++) {
                for (int x = startOffsetX; x < startOffsetX + width; x++) {
                    Color color = pixelReader.getColor(x, y);
                    writer.setColor(x - startOffsetX, y - startOffsetY, color);
                }
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            System.out.println(url);
            e.printStackTrace();
            System.out.println("Tile set loading error occurred: " + e.getMessage());
        }
        return image;
    }
}
