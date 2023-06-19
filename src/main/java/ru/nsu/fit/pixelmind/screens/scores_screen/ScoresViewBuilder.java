package ru.nsu.fit.pixelmind.screens.scores_screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.apache.commons.csv.CSVRecord;
import ru.nsu.fit.pixelmind.screens.widgets.Buttons;

public class ScoresViewBuilder implements Builder<Region> {
    private final Runnable backToMainMenuButtonHandler;

    public ScoresViewBuilder(Runnable backToMainMenuButtonHandler) {
        this.backToMainMenuButtonHandler = backToMainMenuButtonHandler;
    }

    @Override
    public Region build() {
        Label label = new Label("Scores");
        TableView<CSVRecordForTable> tableView = new TableView<>();
        TableColumn<CSVRecordForTable, String> column = new TableColumn<>("Scores");
        column.setCellValueFactory(new PropertyValueFactory<>("value"));
        for (CSVRecord csvRecord : ScoresInteractor.readScoresFromTable()) {
            CSVRecordForTable csvRecordForTable = new CSVRecordForTable(csvRecord.get(0));
            tableView.getItems().add(csvRecordForTable);
        }
        tableView.getColumns().add(column);
        Button backToMainMenuButton = Buttons.button("Back", backToMainMenuButtonHandler);
        VBox results = new VBox(20, label, tableView, backToMainMenuButton);
        results.setPadding(new Insets(40));
        results.setAlignment(Pos.CENTER);
        return results;
    }

    public static class CSVRecordForTable {
        private final String value;

        public CSVRecordForTable(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
