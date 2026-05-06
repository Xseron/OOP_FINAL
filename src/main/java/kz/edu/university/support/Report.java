package kz.edu.university.support;

import kz.edu.university.course.Mark;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/** simple report w/ stats helper */
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    public Report() {
    }

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
    return createdAt;
    }

    public void setContent(String c) {
        this.content = c;
    }

    public boolean hasContent() {
        return content != null && !content.trim().isEmpty();
    }

    /** noop — generation happens at construction; method exists per UML */
    public void generate() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }
    /** print to stdout (no file io for demo) */
    public void export() {
        System.out.println("=== " + title + " ===");
        System.out.println(content);
        System.out.println("(generated " + createdAt + ")");
    }

    /** static helper: stats over marks */
    public static String marksStats(List<Mark> marks) {
        if (marks.isEmpty())
            return "no marks";
        double sum = 0, min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        for (Mark m : marks) {
            double t = m.CalculateTotal();
            sum += t;
            if (t < min)
                min = t;
            if (t > max)
                max = t;
        }
        return "count=" + marks.size() + " avg=" + (sum / marks.size())
                + " min=" + min + " max=" + max;
    }

    @Override
    public String toString() {
        return "Report{" + title + "}";
    }
}
