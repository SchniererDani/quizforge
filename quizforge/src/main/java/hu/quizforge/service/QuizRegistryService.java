package hu.quizforge.service;

import hu.quizforge.model.Quiz;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QuizRegistryService {
    private static final String REGISTRY_FILE = "uploaded_quizzes.txt";
    private final List<Quiz> uploadedQuizzes = new ArrayList<>();
    private final QuizLoaderService loaderService = new QuizLoaderService();

    public QuizRegistryService() {
        loadPersistedQuizzes();
    }

    public void registerQuizFile(File file) throws Exception {
        Quiz quiz = loaderService.loadQuizFromFile(file);
        uploadedQuizzes.removeIf(q -> q.getId().equals(quiz.getId()));
        uploadedQuizzes.add(quiz);
        saveFilePathToRegistry(file.getAbsolutePath());
    }

    public List<Quiz> getUploadedQuizzes() {
        return uploadedQuizzes;
    }

    private void saveFilePathToRegistry(String path) {
        List<String> paths = readRegistryPaths();
        if (!paths.contains(path)) {
            paths.add(path);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(REGISTRY_FILE, StandardCharsets.UTF_8, false))) {
                for (String p : paths) {
                    writer.write(p);
                    writer.newLine();
                }
            } catch (IOException ignored) {}
        }
    }

    private List<String> readRegistryPaths() {
        List<String> paths = new ArrayList<>();
        File file = new File(REGISTRY_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        paths.add(line);
                    }
                }
            } catch (IOException ignored) {}
        }
        return paths;
    }

    private void loadPersistedQuizzes() {
        List<String> paths = readRegistryPaths();
        for (String p : paths) {
            File f = new File(p);
            if (f.exists() && f.isFile()) {
                try {
                    uploadedQuizzes.add(loaderService.loadQuizFromFile(f));
                } catch (Exception ignored) {}
            }
        }
    }
}
