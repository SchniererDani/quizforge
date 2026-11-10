package hu.quizforge.strategy;

import hu.quizforge.model.Question;

public class CustomizableScoringStrategy implements ScoringStrategy {
    private int pointsPerCorrect;
    private int pointsPerWrong;
    private int streakThreshold;
    private int streakBonusPoints;

    public CustomizableScoringStrategy() {
        this.pointsPerCorrect = 10;
        this.pointsPerWrong = 0;
        this.streakThreshold = 2;
        this.streakBonusPoints = 5;
    }

    public CustomizableScoringStrategy(int pointsPerCorrect, int pointsPerWrong, int streakThreshold, int streakBonusPoints) {
        this.pointsPerCorrect = pointsPerCorrect;
        this.pointsPerWrong = pointsPerWrong;
        this.streakThreshold = streakThreshold;
        this.streakBonusPoints = streakBonusPoints;
    }

    public int getPointsPerCorrect() { return pointsPerCorrect; }
    public void setPointsPerCorrect(int pointsPerCorrect) { this.pointsPerCorrect = pointsPerCorrect; }

    public int getPointsPerWrong() { return pointsPerWrong; }
    public void setPointsPerWrong(int pointsPerWrong) { this.pointsPerWrong = pointsPerWrong; }

    public int getStreakThreshold() { return streakThreshold; }
    public void setStreakThreshold(int streakThreshold) { this.streakThreshold = streakThreshold; }

    public int getStreakBonusPoints() { return streakBonusPoints; }
    public void setStreakBonusPoints(int streakBonusPoints) { this.streakBonusPoints = streakBonusPoints; }

    @Override
    public int calculateScore(Question question, boolean isCorrect, int currentStreak) {
        if (isCorrect) {
            int score = (question.getPoints() > 0) ? question.getPoints() : pointsPerCorrect;
            if (currentStreak >= streakThreshold) {
                score += streakBonusPoints;
            }
            return score;
        } else {
            return pointsPerWrong;
        }
    }

    @Override
    public String getStrategyDescription() {
        return "Pontozas: Jo valasz=+" + pointsPerCorrect + "p | Rossz valasz=" + pointsPerWrong + "p | Streak kuszob=" + streakThreshold + " szor | Streak bonusz=+" + streakBonusPoints + "p";
    }
}
