package hu.quizforge.strategy;

import hu.quizforge.model.Question;

public interface ScoringStrategy {
    int calculateScore(Question question, boolean isCorrect, int currentStreak);
    String getStrategyDescription();
}
