package ru.nsu.fit.pixelmind.screens.load_game_screen;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.loading_resources_screen.SavedSessionEntry;
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LoadGameScreenView implements Builder<Region> {
    @NotNull
    private final LoadGameScreenModel model;
    @NotNull
    private final Runnable backToMainMenuButtonHandler;
    @NotNull
    private final Consumer<SavedSessionEntry> selectSaveHandler;

    LoadGameScreenView(@NotNull LoadGameScreenModel model, @NotNull Runnable backToMainMenuButtonHandler, @NotNull Consumer<SavedSessionEntry> selectSaveHandler) {
        this.model = model;
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
        this.selectSaveHandler = selectSaveHandler;
    }

    @Override
    @NotNull
    public Region build() {
        Label label = new Label("Load Game");
        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);
        TableView<SavedSessionEntry> tableView = new TableView<>();
        TableColumn<SavedSessionEntry, String> heroTypeColumn = new TableColumn<>("Hero");
        heroTypeColumn.setCellValueFactory((e) -> new SimpleStringProperty(e.getValue().heroType().toString()));
        TableColumn<SavedSessionEntry, String> saveColumn = new TableColumn<>("Date");
        saveColumn.setCellValueFactory((e) -> new SimpleStringProperty(e.getValue().date()));

        List<TableColumn<SavedSessionEntry, String>> columns = new ArrayList<>();
        columns.add(heroTypeColumn);
        columns.add(saveColumn);
        tableView.getColumns().addAll(columns);
        List<SavedSessionEntry> savedSessionEntriesInfo = new ArrayList<>();
        if (model.savedSessionEntriesInfo() != null) {
            savedSessionEntriesInfo = model.savedSessionEntriesInfo();
        }
        ObservableList<SavedSessionEntry> data = FXCollections.observableArrayList(savedSessionEntriesInfo);
        tableView.setItems(data);

        tableView.setOnMouseClicked(event -> {
            System.out.println(tableView.getSelectionModel().getSelectedItem());
            selectSaveHandler.accept(tableView.getSelectionModel().getSelectedItem());
        });

        VBox results = new VBox(20, label, tableView, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}
