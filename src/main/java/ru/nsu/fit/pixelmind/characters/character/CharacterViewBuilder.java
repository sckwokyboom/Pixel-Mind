package ru.nsu.fit.pixelmind.characters.character;

import javafx.scene.image.Image;
import javafx.util.Builder;

public class CharacterViewBuilder implements Builder<Image> {
    private final CharacterModel model;

    public CharacterViewBuilder(CharacterModel model) {
        this.model = model;
    }

    @Override
    public Image build() {
        return model.getCharacterSprites().get(0);
    }
}
