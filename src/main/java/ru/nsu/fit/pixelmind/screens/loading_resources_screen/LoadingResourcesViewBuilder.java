package ru.nsu.fit.pixelmind.screens.loading_resources_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class LoadingResourcesViewBuilder implements Builder<Region> {


    private final LoadingResourcesModel model;
//    private final Runnable finishLoadingHandler;

    public LoadingResourcesViewBuilder(LoadingResourcesModel model) {
        this.model = model;
//        this.finishLoadingHandler = finishLoadingHandler;
    }

    @Override
    public Region build() {
        Label label = new Label("Loading Game...");
        VBox results = new VBox(20, label);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
//        model.isSetupProperty().addListener((observable, newValue, oldValue) -> {
//            System.out.println("Finish loading!");
//            finishLoadingHandler.run();
//        });
        return results;
    }
}
