package hu.quizforge;

import hu.quizforge.model.MultipleChoiceQuestion;
import hu.quizforge.model.TrueFalseQuestion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    @Test
    public void testMultipleChoiceQuestionCheckAnswer() {
        MultipleChoiceQuestion q = new MultipleChoiceQuestion("q1", "Mi a Java?", 10, List.of("Nyelv", "Kave", "Sziget"), 0);
        assertTrue(q.checkAnswer("A"));
        assertTrue(q.checkAnswer("1"));
        assertFalse(q.checkAnswer("B"));
    }

    @Test
    public void testTrueFalseQuestionCheckAnswer() {
        TrueFalseQuestion q = new TrueFalseQuestion("q2", "A Fold kerek.", 10, true);
        assertTrue(q.checkAnswer("igaz"));
        assertTrue(q.checkAnswer("1"));
        assertFalse(q.checkAnswer("hamis"));
    }
}
