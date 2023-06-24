package ru.nsu.fit.pixelmind.screens.scores_screen;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import ru.nsu.fit.pixelmind.characters.character.CharacterType;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ScoresInteractor {
    public static final String SCORES_TABLE = "src/main/resources/scores.csv";
    private final ScoresModel scoresModel;

    public ScoresInteractor(ScoresModel scoresModel) {
        this.scoresModel = scoresModel;
    }

    public void dumpScores() {
        try (FileWriter fileWriter = new FileWriter(SCORES_TABLE, true);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (HighScoreEntry scoreEntry : scoresModel.newScores()) {
                csvPrinter.printRecord(scoreEntry.heroType(), scoreEntry.score());

            }
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(CharacterType heroType, int score) {
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
                csvRecords.add(new HighScoreEntry(CharacterType.valueOf(csvRecord.get(0)), Integer.parseInt(csvRecord.get(1))));
            }
        } catch (IOException ignored) {
        }
        return csvRecords;
    }
}
