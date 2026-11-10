package hu.quizforge.service;

import hu.quizforge.model.Question;
import hu.quizforge.model.Quiz;
import hu.quizforge.model.ScoreEntry;
import hu.quizforge.strategy.ScoringStrategy;
import hu.quizforge.ui.ScreenUtils;

import java.util.Scanner;

public class QuizRunnerService {

    public void runQuiz(Quiz quiz, ScoringStrategy strategy, HighScoreService highScoreService, Scanner scanner) {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" KVIZ INDITASA: " + quiz.getTitle().toUpperCase());
        System.out.println(" Kategoria: " + quiz.getCategory() + " | Szerzo: " + quiz.getAuthor());
        System.out.println("==================================================");
        System.out.println(strategy.getStrategyDescription());
        System.out.println("--------------------------------------------------");
        System.out.println("Nyomjon ENTER-t a kezdeshez...");
        scanner.nextLine();

        int totalScore = 0;
        int currentStreak = 0;
        int qNum = 1;

        for (Question q : quiz.getQuestions()) {
            ScreenUtils.clearScreen();
            System.out.println(" Kerdes " + qNum + " / " + quiz.getQuestions().size());
            System.out.println(" Aktualis pontszam: " + totalScore + " | Aktualis streak: " + currentStreak);
            System.out.println("--------------------------------------------------");
            System.out.println(q.getFormattedQuestion());
            System.out.println("--------------------------------------------------");
            System.out.print("Valasz: ");

            String ans = scanner.nextLine();
            boolean correct = q.checkAnswer(ans);

            if (correct) {
                currentStreak++;
                int earned = strategy.calculateScore(q, true, currentStreak);
                totalScore += earned;
                System.out.println();
                System.out.println("-> HELYES VALASZ! Szerzett pont: +" + earned + " (Streak: " + currentStreak + ")");
            } else {
                currentStreak = 0;
                int earned = strategy.calculateScore(q, false, currentStreak);
                totalScore += earned;
                System.out.println();
                System.out.println("-> HIBAS VALASZ! Pont valtozas: " + earned);
            }

            System.out.println();
            System.out.println("Nyomjon ENTER-t a kovetkezo kerdeshez...");
            scanner.nextLine();
            qNum++;
        }

        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println("               KVIZ VEGE!                         ");
        System.out.println("==================================================");
        System.out.println("Elert vegso pontszam: " + totalScore + " pont");
        System.out.println("--------------------------------------------------");
        System.out.print("Kerjuk, adja meg a nevet a menteshez: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Nevtelen Jatekos";

        ScoreEntry entry = new ScoreEntry(quiz.getTitle(), name, totalScore);
        highScoreService.saveOrUpdateScore(entry);

        System.out.println();
        System.out.println("Eredmeny elmentve! Nyomjon ENTER-t a visszalepeshez...");
        scanner.nextLine();
    }
}
