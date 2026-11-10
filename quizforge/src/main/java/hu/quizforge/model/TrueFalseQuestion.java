package hu.quizforge.model;

public class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String id, String questionText, int points, boolean correctAnswer) {
        super(id, questionText, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean checkAnswer(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return false;
        }
        String val = userInput.trim().toLowerCase();
        boolean userBool = val.equals("igaz") || val.equals("true") || val.equals("1") || val.equals("i") || val.equals("t");
        return userBool == correctAnswer;
    }

    @Override
    public String getFormattedQuestion() {
        return getQuestionText() + "\n  (Valaszlehetosegek: Igaz / Hamis)";
    }
}
