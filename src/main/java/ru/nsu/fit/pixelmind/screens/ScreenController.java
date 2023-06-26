package ru.nsu.fit.pixelmind.screens;

import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;


/**
 * The controller of some screen in the understanding of MVCI.
 * Does not store any data necessary for the logic of its work.
 * Does not handle view in any way.
 * Responsible for creating model object, view object, and possibly an interactor if needed.
 * Communication between different screens with different logic occurs through the <code>ScreenController</code>.
 */
public interface ScreenController {
    /**
     * Returns a standalone representation of the screen obtained from the special class responsible for this view.
     * This method does not contain any logic for the operation and construction of this screen,
     * it only takes out the object obtained from a special class. */
    @NotNull
    Region getView();
}

