package ru.nsu.fit.pixelmind.screens.scores_screen;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ScoresInteractor {
    public static final String SCORES_TABLE = "src/main/resources/scores.csv";
//    private ScoresInteractor instance;
//
//    private final List<HighScore> highScores;
//
//
//    public ScoresInteractor getInstance() {
//        if (instance != null) {
//            return instance;
//        }
//        instance = new ScoresInteractor(loadFromFile(SCORES_TABLE));
//        return instance;
//
//    }
//
//    List<HighScore> getScores() {}
//    void dumpScores() {}
//    void addScore(String player, int score) {
//
//    }

    public static void addScoreToTable(int gameScore) {
        try (FileWriter fileWriter = new FileWriter(SCORES_TABLE, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

            csvPrinter.printRecord(gameScore);
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static List<CSVRecord> readScoresFromTable() {
        ArrayList<CSVRecord> csvRecords = new ArrayList<>();
        try (Reader reader = new FileReader(SCORES_TABLE);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                csvRecords.add(csvRecord);
            }
        } catch (IOException ignored) {
        }
        return csvRecords;
    }
}
