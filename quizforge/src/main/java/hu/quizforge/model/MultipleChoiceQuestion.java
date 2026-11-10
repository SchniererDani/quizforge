package hu.quizforge.model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private final List<String> options;
    private final int correctOptionIndex;

    public MultipleChoiceQuestion(String id, String questionText, int points, List<String> options, int correctOptionIndex) {
        super(id, questionText, points);
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    @Override
    public boolean checkAnswer(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return false;
        }
        String input = userInput.trim().toUpperCase();
        if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'Z') {
            int idx = input.charAt(0) - 'A';
            return idx == correctOptionIndex;
        }
        try {
            int numIdx = Integer.parseInt(input) - 1;
            return numIdx == correctOptionIndex;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getFormattedQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(getQuestionText()).append("\n");
        char label = 'A';
        for (String opt : options) {
            sb.append("  [").append(label++).append("] ").append(opt).append("\n");
        }
        return sb.toString();
    }
}
