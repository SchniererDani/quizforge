package hu.quizforge.exception;

public class QuizNotFoundException extends QuizMasterException {
    public QuizNotFoundException(String quizId) {
        super("A keresett kviz nem talalhato: " + quizId);
    }
}
