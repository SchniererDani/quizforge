package hu.quizforge.service;

import hu.quizforge.exception.InvalidQuizFormatException;
import hu.quizforge.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizLoaderService {

    public Quiz loadQuizFromFile(File file) throws IOException, InvalidQuizFormatException {
        if (!file.exists() || !file.isFile()) {
            throw new IOException("A fajl nem letezik: " + file.getAbsolutePath());
        }

        String quizTitle = file.getName().replace(".txt", "");
        String category = "Altalanos";
        String author = "Felhasznalo";
        List<Question> questions = new ArrayList<>();

        int lineNumber = 0;
        int qIdCounter = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("QUIZ:")) {
                    quizTitle = line.substring(5).trim();
                } else if (line.startsWith("CATEGORY:")) {
                    category = line.substring(9).trim();
                } else if (line.startsWith("AUTHOR:")) {
                    author = line.substring(7).trim();
                } else if (line.startsWith("TYPE:")) {
                    Question q = parseQuestionLine(line, "q_" + qIdCounter++, lineNumber);
                    questions.add(q);
                }
            }
        }

        if (questions.isEmpty()) {
            throw new InvalidQuizFormatException("A kviz nem tartalmaz ervenyes kerdeseket.", lineNumber);
        }

        String quizId = file.getName().toLowerCase().replace(".txt", "").replaceAll("\\s+", "_");
        return new Quiz(quizId, quizTitle, category, author, questions);
    }

    private Question parseQuestionLine(String line, String qId, int lineNumber) throws InvalidQuizFormatException {
        String[] parts = line.split("\\|");
        String type = null;
        int points = 10;
        String questionText = null;
        String answerStr = null;
        List<String> options = new ArrayList<>();

        for (String part : parts) {
            String[] keyValue = part.split(":", 2);
            if (keyValue.length < 2) continue;
            String key = keyValue[0].trim().toUpperCase();
            String value = keyValue[1].trim();

            switch (key) {
                case "TYPE" -> type = value.toUpperCase();
                case "POINTS" -> {
                    try {
                        points = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        throw new InvalidQuizFormatException("Ervenytelen pontertek: " + value, lineNumber);
                    }
                }
                case "QUESTION" -> questionText = value;
                case "OPTIONS" -> options = Arrays.stream(value.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
                case "ANSWER" -> answerStr = value;
            }
        }

        if (type == null || questionText == null || answerStr == null) {
            throw new InvalidQuizFormatException("Hianyzo TYPE, QUESTION vagy ANSWER mezo.", lineNumber);
        }

        return switch (type) {
            case "MULTIPLE_CHOICE" -> {
                if (options.isEmpty()) {
                    throw new InvalidQuizFormatException("MULTIPLE_CHOICE tipusnal hianyoznak az OPTIONS opciok.", lineNumber);
                }
                int correctIdx = 0;
                if (answerStr.length() == 1 && answerStr.charAt(0) >= 'A' && answerStr.charAt(0) <= 'Z') {
                    correctIdx = answerStr.charAt(0) - 'A';
                } else {
                    try {
                        correctIdx = Integer.parseInt(answerStr) - 1;
                    } catch (NumberFormatException ignored) {}
                }
                if (correctIdx < 0 || correctIdx >= options.size()) {
                    throw new InvalidQuizFormatException("Ervenytelen valasz index: " + answerStr, lineNumber);
                }
                yield new MultipleChoiceQuestion(qId, questionText, points, options, correctIdx);
            }
            case "TRUE_FALSE" -> {
                boolean boolAns = answerStr.equalsIgnoreCase("igaz") || answerStr.equalsIgnoreCase("true") || answerStr.equals("1") || answerStr.equalsIgnoreCase("i");
                yield new TrueFalseQuestion(qId, questionText, points, boolAns);
            }
            case "TEXT" -> new TextAnswerQuestion(qId, questionText, points, answerStr);
            default -> throw new InvalidQuizFormatException("Ismeretlen kerdes tipus: " + type, lineNumber);
        };
    }
}
