package hu.quizforge.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreEntry implements Comparable<ScoreEntry> {
    private final String quizTitle;
    private final String playerName;
    private final int score;
    private final String dateString;

    public ScoreEntry(String quizTitle, String playerName, int score) {
        this(quizTitle, playerName, score, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public ScoreEntry(String quizTitle, String playerName, int score, String dateString) {
        this.quizTitle = quizTitle;
        this.playerName = playerName;
        this.score = score;
        this.dateString = dateString;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getDateString() {
        return dateString;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        return Integer.compare(other.score, this.score);
    }
}
