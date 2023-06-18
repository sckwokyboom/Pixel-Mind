package ru.nsu.fit.pixelmind.game_field.tile;

import javafx.scene.image.Image;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;

public class TileViewBuilder implements Builder<Image> {
    private Image texture;

    public void setTexture(@NotNull Image texture) {
        this.texture = texture;
    }

    @Override
    @NotNull
    public Image build() {
        return texture;
    }
}
