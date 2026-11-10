package hu.quizforge.model;

public class TextAnswerQuestion extends Question {
    private final String expectedAnswer;

    public TextAnswerQuestion(String id, String questionText, int points, String expectedAnswer) {
        super(id, questionText, points);
        this.expectedAnswer = expectedAnswer;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    @Override
    public boolean checkAnswer(String userInput) {
        if (userInput == null) return false;
        return userInput.trim().equalsIgnoreCase(expectedAnswer.trim());
    }

    @Override
    public String getFormattedQuestion() {
        return getQuestionText() + "\n  (Irja be a pontos valaszt szovegesen)";
    }
}
