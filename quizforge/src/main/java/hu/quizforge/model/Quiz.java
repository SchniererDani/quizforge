package hu.quizforge.model;

import java.util.List;

public class Quiz {
    private final String id;
    private final String title;
    private final String category;
    private final String author;
    private final List<Question> questions;

    public Quiz(String id, String title, String category, String author, List<Question> questions) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.author = author;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
