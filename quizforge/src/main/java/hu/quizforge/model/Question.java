package hu.quizforge.model;

public abstract class Question {
    private final String id;
    private final String questionText;
    private final int points;

    public Question(String id, String questionText, int points) {
        this.id = id;
        this.questionText = questionText;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean checkAnswer(String userInput);

    public abstract String getFormattedQuestion();
}
