package ru.nsu.fit.pixelmind.screens.scores_screen;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.screens.game.character.CharacterType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ScoresInteractor {
    public static final String SCORES_TABLE = "src/main/resources/scores.csv";
    @NotNull
    private final ScoresModel scoresModel;

    ScoresInteractor(@NotNull ScoresModel scoresModel) {
        this.scoresModel = scoresModel;
    }

    public void dumpScores() {
        try (FileWriter fileWriter = new FileWriter(SCORES_TABLE, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (HighScoreEntry scoreEntry : scoresModel.newScores()) {
                csvPrinter.printRecord(scoreEntry.heroType(), scoreEntry.score());
            }
        } catch (IOException e) {
            log.error("Unable to dump scores", e);
        }
    }

    public void addScore(@NotNull CharacterType heroType, int score) {
        scoresModel.newScores().add(new HighScoreEntry(heroType, score));
        scoresModel.scores().add(new HighScoreEntry(heroType, score));
    }

    public void loadScoresFromFile() {
        scoresModel.setScores(readScoresFromTable());
    }

    @NotNull
    private static List<HighScoreEntry> readScoresFromTable() {
        ArrayList<HighScoreEntry> csvRecords = new ArrayList<>();
        try (Reader reader = new FileReader(SCORES_TABLE);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() != 2) {
                    log.error("Broken scores table:" + csvRecord);
                    try (Writer writer = new FileWriter(SCORES_TABLE, false)) {
                        writer.write("");
                    } catch (IOException e) {
                        log.error("Unable to rewrite invalid scores table", e);
                    }
                    return new ArrayList<>();
                }
                csvRecords.add(new HighScoreEntry(CharacterType.valueOf(csvRecord.get(0)), Integer.parseInt(csvRecord.get(1))));
            }
        } catch (IOException e) {
            log.error("Unable to parse CSV-table with scores", e);
        }
        return csvRecords;
    }
}
