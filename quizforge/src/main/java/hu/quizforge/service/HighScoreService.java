package hu.quizforge.service;

import hu.quizforge.model.ScoreEntry;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoreService {
    private static final String SCORE_FILE = "scores.txt";

    public List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(SCORE_FILE);
        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    scores.add(new ScoreEntry(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()), parts[3].trim()));
                }
            }
        } catch (Exception ignored) {}

        Collections.sort(scores);
        return scores;
    }

    public void saveOrUpdateScore(ScoreEntry newEntry) {
        List<ScoreEntry> scores = loadScores();
        boolean updated = false;

        for (int i = 0; i < scores.size(); i++) {
            ScoreEntry existing = scores.get(i);
            if (existing.getQuizTitle().equalsIgnoreCase(newEntry.getQuizTitle()) && existing.getPlayerName().equalsIgnoreCase(newEntry.getPlayerName())) {
                if (newEntry.getScore() > existing.getScore()) {
                    scores.set(i, newEntry);
                }
                updated = true;
                break;
            }
        }

        if (!updated) {
            scores.add(newEntry);
        }

        Collections.sort(scores);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, StandardCharsets.UTF_8, false))) {
            for (ScoreEntry s : scores) {
                writer.write(s.getQuizTitle() + " | " + s.getPlayerName() + " | " + s.getScore() + " | " + s.getDateString());
                writer.newLine();
            }
        } catch (IOException ignored) {}
    }
}
