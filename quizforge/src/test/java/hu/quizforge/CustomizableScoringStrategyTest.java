package hu.quizforge;

import hu.quizforge.model.TrueFalseQuestion;
import hu.quizforge.strategy.CustomizableScoringStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomizableScoringStrategyTest {

    @Test
    public void testStreakBonusCalculation() {
        CustomizableScoringStrategy strategy = new CustomizableScoringStrategy(10, 0, 2, 5);
        TrueFalseQuestion q = new TrueFalseQuestion("q1", "Teszt", 10, true);

        // 1. streak -> streak 1 < 2 threshold -> 10 pont
        assertEquals(10, strategy.calculateScore(q, true, 1));

        // 2. streak -> streak 2 >= 2 threshold -> 10 + 5 = 15 pont
        assertEquals(15, strategy.calculateScore(q, true, 2));
    }
}
