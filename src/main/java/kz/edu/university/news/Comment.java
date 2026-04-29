package kz.edu.university.news;

import kz.edu.university.auth.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/** news comment */
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private User author;
    private String text;
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(User author, String text) {
        this.author = author;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void edit(String newText) {
        this.text = newText;
    }

    @Override
    public String toString() {
        return "Comment{by " + (author != null ? author.getUsername() : "?") + ": " + text + "}";
    }
}
