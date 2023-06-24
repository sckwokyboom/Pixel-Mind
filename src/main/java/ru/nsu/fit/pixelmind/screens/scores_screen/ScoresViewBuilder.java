package ru.nsu.fit.pixelmind.screens.scores_screen;

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
import ru.nsu.fit.pixelmind.utils.widgets.Buttons;

import java.util.ArrayList;
import java.util.List;

public class ScoresViewBuilder implements Builder<Region> {
    private final ScoresModel model;
    private final Runnable backToMainMenuButtonHandler;

    public ScoresViewBuilder(ScoresModel model, Runnable backToMainMenuButtonHandler) {
        this.model = model;
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
    }

    @Override
    public Region build() {
        Label label = new Label("Scores");
        TableView<HighScoreEntry> tableView = new TableView<>();
        TableColumn<HighScoreEntry, String> heroTypeColumn = new TableColumn<>("Hero");
        heroTypeColumn.setCellValueFactory((e) -> new SimpleStringProperty(e.getValue().heroType().toString()));
        TableColumn<HighScoreEntry, String> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory((e) -> new SimpleStringProperty(String.valueOf(e.getValue().score())));

        List<TableColumn<HighScoreEntry, String>> columns = new ArrayList<>();
        columns.add(heroTypeColumn);
        columns.add(scoreColumn);
        tableView.getColumns().addAll(columns);
        ObservableList<HighScoreEntry> data = FXCollections.observableArrayList(model.scores());
        tableView.setItems(data);

        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);
        VBox results = new VBox(20, label, tableView, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }
}
