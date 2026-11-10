package hu.quizforge.ui;

import hu.quizforge.model.Quiz;
import hu.quizforge.model.ScoreEntry;
import hu.quizforge.service.*;
import hu.quizforge.strategy.CustomizableScoringStrategy;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final BuiltInQuizService builtInQuizService = new BuiltInQuizService();
    private final QuizLoaderService quizLoaderService = new QuizLoaderService();
    private final QuizRegistryService quizRegistryService = new QuizRegistryService();
    private final HighScoreService highScoreService = new HighScoreService();
    private final QuizRunnerService quizRunnerService = new QuizRunnerService();
    private final CustomizableScoringStrategy scoringStrategy = new CustomizableScoringStrategy();

    public void start() {
        boolean running = true;
        while (running) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println("                 QUIZFORGE - FOMENU               ");
            System.out.println("==================================================");
            System.out.println("1. Jatek inditasa es kvizek bongeszese");
            System.out.println("2. Uj kviz importalasa szoveges (.txt) fajlbol");
            System.out.println("3. Pontozasi rendszer es streak szabalyaik finomhangolasa");
            System.out.println("4. Scoreboard");
            System.out.println("5. Kilepes a programbol");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon menupontot (1-5): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> showBrowseMenu();
                case "2" -> showUploadMenu();
                case "3" -> showSettingsMenu();
                case "4" -> showScoreboardMenu();
                case "5" -> {
                    running = false;
                    ScreenUtils.clearScreen();
                    System.out.println("Koszonjuk, hogy a QuizForge-ot hasznalta! Viszontlatasra!");
                }
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t a folytatashoz...");
                    scanner.nextLine();
                }
            }
        }
    }

    private void showBrowseMenu() {
        boolean back = false;
        while (!back) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println(" 1. JATEK INDITASA ES KVIZEK BONGESZESE           ");
            System.out.println("==================================================");
            System.out.println("1.1. Ajanlott elso lepes: Interaktiv bemutato kviz (Tutorial)");
            System.out.println("1.2. Beepitett alapertelmezett kvizek");
            System.out.println("1.3. Sajat, importalt kvizek listaja es inditasa");
            System.out.println("1.4. Vissza a fomenuhoz");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon almenupontot (1-4): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1", "1.1" -> quizRunnerService.runQuiz(builtInQuizService.getTutorialQuiz(), scoringStrategy, highScoreService, scanner);
                case "2", "1.2" -> showBuiltInQuizzesSubmenu();
                case "3", "1.3" -> showUploadedQuizzesSubmenu();
                case "4", "1.4" -> back = true;
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t...");
                    scanner.nextLine();
                }
            }
        }
    }

    private void showBuiltInQuizzesSubmenu() {
        boolean back = false;
        while (!back) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println(" 1.2. BEEPITTET ALAPERTELMEZETT KVIZEK            ");
            System.out.println("==================================================");
            System.out.println("1.2.1. Altalanos szamitogepes alkatreszek kviz (8 kerdes)");
            System.out.println("1.2.2. Pecsi Tudomanyegyetem (PTE) kviz (8 kerdes)");
            System.out.println("1.2.3. Billentyuzet shortcutok kviz (8 kerdes)");
            System.out.println("1.2.4. Vissza");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon kvizt (1-4): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1", "1.2.1" -> quizRunnerService.runQuiz(builtInQuizService.getHardwareQuiz(), scoringStrategy, highScoreService, scanner);
                case "2", "1.2.2" -> quizRunnerService.runQuiz(builtInQuizService.getPteQuiz(), scoringStrategy, highScoreService, scanner);
                case "3", "1.2.3" -> quizRunnerService.runQuiz(builtInQuizService.getShortcutQuiz(), scoringStrategy, highScoreService, scanner);
                case "4", "1.2.4" -> back = true;
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t...");
                    scanner.nextLine();
                }
            }
        }
    }

    private void showUploadedQuizzesSubmenu() {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" 1.3. SAJAT, IMPORTALT KVIZEK LISTAJA             ");
        System.out.println("==================================================");

        List<Quiz> uploaded = quizRegistryService.getUploadedQuizzes();
        if (uploaded.isEmpty()) {
            System.out.println("Jelenleg nincs egyetlen importalt kviz sem a rendszerben.");
            System.out.println("Hasznalja a 2. menupontot (.txt fajl beolvasasa) uj kviz hozzaadasahoz.");
            System.out.println("--------------------------------------------------");
            System.out.println("Nyomjon ENTER-t a visszalepeshez...");
            scanner.nextLine();
            return;
        }

        for (int i = 0; i < uploaded.size(); i++) {
            Quiz q = uploaded.get(i);
            System.out.println((i + 1) + ". " + q.getTitle() + " (" + q.getQuestions().size() + " kerdes) [Szerzo: " + q.getAuthor() + "]");
        }
        System.out.println((uploaded.size() + 1) + ". Vissza");
        System.out.println("--------------------------------------------------");
        System.out.print("Valasszon elinditando kvizt (1-" + (uploaded.size() + 1) + "): ");

        String input = scanner.nextLine().trim();
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < uploaded.size()) {
                quizRunnerService.runQuiz(uploaded.get(idx), scoringStrategy, highScoreService, scanner);
            }
        } catch (NumberFormatException ignored) {}
    }

    private void showUploadMenu() {
        boolean back = false;
        while (!back) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println(" 2. UJ KVIZ IMPORTALASA (.TXT FAJLBOL)           ");
            System.out.println("==================================================");
            System.out.println("2.1. Formatumi utmutato (.txt kviz beolvasasahoz)");
            System.out.println("2.2. Uj .txt kvizfajl beolvasasa es regisztralasa");
            System.out.println("2.3. Korabban feltoltott kvizek es fajlutvonalak");
            System.out.println("2.4. Vissza a fomenuhoz");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon almenupontot (1-4): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1", "2.1" -> showFormatGuide();
                case "2", "2.2" -> handleQuizUpload();
                case "3", "2.3" -> showUploadedPaths();
                case "4", "2.4" -> back = true;
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t...");
                    scanner.nextLine();
                }
            }
        }
    }

    private void showFormatGuide() {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" 2.1. FORMATUMI UTMUTATO (.TXT KVIZ)             ");
        System.out.println("==================================================");
        System.out.println("Fejlec mezok:");
        System.out.println("  QUIZ: Kviz Cime");
        System.out.println("  CATEGORY: Kategoria");
        System.out.println("  AUTHOR: Szerzo neve");
        System.out.println();
        System.out.println("Kerdes sorok formatusa:");
        System.out.println(" 1. Feleletvalasztos:");
        System.out.println("    TYPE: MULTIPLE_CHOICE | POINTS: 10 | QUESTION: Kerdes? | OPTIONS: A, B, C | ANSWER: A");
        System.out.println(" 2. Igaz/Hamis:");
        System.out.println("    TYPE: TRUE_FALSE | POINTS: 10 | QUESTION: Kerdes? | ANSWER: Igaz");
        System.out.println(" 3. Szoveges valasz:");
        System.out.println("    TYPE: TEXT | POINTS: 10 | QUESTION: Kerdes? | ANSWER: Pontos valasz");
        System.out.println("--------------------------------------------------");
        System.out.println("Nyomjon ENTER-t a visszalepeshez...");
        scanner.nextLine();
    }

    private void handleQuizUpload() {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" 2.2. UJ .TXT KVIZ BEOLVASASA                    ");
        System.out.println("==================================================");
        System.out.print("Adja meg a .txt fajl teljes vagy relativ eleresi utjat: ");
        String path = scanner.nextLine().trim();

        if (path.isEmpty()) return;

        File f = new File(path);
        try {
            quizRegistryService.registerQuizFile(f);
            System.out.println();
            System.out.println("SIKERES FELTOLTES!");
            System.out.println("A kviz sikeresen beolvasva es regisztralva.");
        } catch (Exception e) {
            System.out.println();
            System.out.println("HIBA A FELTOLTESKOR: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Nyomjon ENTER-t a folytatashoz...");
        scanner.nextLine();
    }

    private void showUploadedPaths() {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" 2.3. FELTOLTOTT KVIZEK ES FAJLUTVONALAK          ");
        System.out.println("==================================================");
        List<Quiz> quizzes = quizRegistryService.getUploadedQuizzes();
        if (quizzes.isEmpty()) {
            System.out.println("Meg nincs regisztralva egyetlen kulso kviz sem.");
        } else {
            for (Quiz q : quizzes) {
                System.out.println(" - " + q.getTitle() + " [" + q.getCategory() + "] - " + q.getQuestions().size() + " kerdes");
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Nyomjon ENTER-t a visszalepeshez...");
        scanner.nextLine();
    }

    private void showSettingsMenu() {
        boolean back = false;
        while (!back) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println(" 3. PONTOZAS ES STREAK FINOMHANGOLASA             ");
            System.out.println("==================================================");
            System.out.println("3.1. Pontszamitas es streak parameterek modositas");
            System.out.println("3.2. Jelenleg aktiv beallitasok megtekintese");
            System.out.println("3.3. Vissza a fomenuhoz");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon almenupontot (1-3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1", "3.1" -> customizeScoring();
                case "2", "3.2" -> {
                    ScreenUtils.clearScreen();
                    System.out.println("==================================================");
                    System.out.println(" 3.2. JELENLEGI PONTOZASI BEALLITASOK            ");
                    System.out.println("==================================================");
                    System.out.println(scoringStrategy.getStrategyDescription());
                    System.out.println("--------------------------------------------------");
                    System.out.println("Nyomjon ENTER-t a visszalepeshez...");
                    scanner.nextLine();
                }
                case "3", "3.3" -> back = true;
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t...");
                    scanner.nextLine();
                }
            }
        }
    }

    private void customizeScoring() {
        ScreenUtils.clearScreen();
        System.out.println("==================================================");
        System.out.println(" 3.1. PONTOZAS ES STREAK TESTRESZABASA            ");
        System.out.println("==================================================");
        try {
            System.out.print("Jo valaszert jaro pontszam (Jelenlegi: " + scoringStrategy.getPointsPerCorrect() + "): ");
            int correct = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Rossz valaszert jaro pontszam (pl. 0 vagy -5) (Jelenlegi: " + scoringStrategy.getPointsPerWrong() + "): ");
            int wrong = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Streak kuszob - hany jo valasz utan jarjon bonusz (Jelenlegi: " + scoringStrategy.getStreakThreshold() + "): ");
            int threshold = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Streak bonusz pontszam (Jelenlegi: " + scoringStrategy.getStreakBonusPoints() + "): ");
            int bonus = Integer.parseInt(scanner.nextLine().trim());

            scoringStrategy.setPointsPerCorrect(correct);
            scoringStrategy.setPointsPerWrong(wrong);
            scoringStrategy.setStreakThreshold(threshold);
            scoringStrategy.setStreakBonusPoints(bonus);

            System.out.println();
            System.out.println("SIKERESEN FRISSITVE!");
            System.out.println("Uj beallitas: " + scoringStrategy.getStrategyDescription());
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("HIBA: Csak egesz szamokat adhat meg!");
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Nyomjon ENTER-t a folytatashoz...");
        scanner.nextLine();
    }

    private void showScoreboardMenu() {
        boolean back = false;
        while (!back) {
            ScreenUtils.clearScreen();
            System.out.println("==================================================");
            System.out.println(" 4. SCOREBOARD                                    ");
            System.out.println("==================================================");
            System.out.println("4.1. Legjobb eredmenyek listaja");
            System.out.println("4.2. Vissza a fomenuhoz");
            System.out.println("--------------------------------------------------");
            System.out.print("Valasszon almenupontot (1-2): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1", "4.1" -> {
                    ScreenUtils.clearScreen();
                    System.out.println("==================================================");
                    System.out.println("             SCOREBOARD - TOP EREDMENYEK          ");
                    System.out.println("==================================================");
                    List<ScoreEntry> scores = highScoreService.loadScores();
                    if (scores.isEmpty()) {
                        System.out.println("Meg nincsenek elmentett eredmenyek.");
                    } else {
                        System.out.println("KVIZ NEVE | JATEKOS NEVE | PONTSZAM | DATUM");
                        System.out.println("----------------------------------------------------------------------");
                        for (ScoreEntry s : scores) {
                            System.out.println(s.getQuizTitle() + " | " + s.getPlayerName() + " | " + s.getScore() + " | " + s.getDateString());
                        }
                    }
                    System.out.println("--------------------------------------------------");
                    System.out.println("Nyomjon ENTER-t a visszalepeshez...");
                    scanner.nextLine();
                }
                case "2", "4.2" -> back = true;
                default -> {
                    System.out.println("Ervenytelen valasztas! Nyomjon ENTER-t...");
                    scanner.nextLine();
                }
            }
        }
    }
}
