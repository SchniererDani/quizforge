package hu.quizforge.exception;

public class InvalidQuizFormatException extends QuizMasterException {
    private final int lineNumber;

    public InvalidQuizFormatException(String message, int lineNumber) {
        super(message + " (Sor: " + lineNumber + ")");
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
