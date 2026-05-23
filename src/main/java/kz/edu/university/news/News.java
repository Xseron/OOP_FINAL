package kz.edu.university.news;

import kz.edu.university.enums.NewsTopic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** uni news. RESEARCH topic is pinned first per spec */
public class News implements Serializable, Comparable<News> {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private NewsTopic topic;
    private boolean pinned;
    private LocalDate publishDate;
    private List<Comment> comments = new ArrayList<>();

    public News() {
    }

    public News(String title, String content, NewsTopic topic) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.publishDate = LocalDate.now();
        this.pinned = (topic == NewsTopic.RESEARCH); // research auto-pinned per spec
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NewsTopic getTopic() {
        return topic;
    }

    public boolean isPinned() {
        return pinned;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void pin() {
        this.pinned = true;
    }

    /** pinned first, then newest first */
    @Override
    public int compareTo(News other) {
        if (this.pinned != other.pinned)
            return this.pinned ? -1 : 1;
        if (publishDate == null && other.publishDate == null)
            return 0;
        if (publishDate == null)
            return 1;
        if (other.publishDate == null)
            return -1;
        return other.publishDate.compareTo(this.publishDate);
    }

    @Override
    public String toString() {
        return (pinned ? "[PINNED] " : "") + "News{" + title + " - " + topic + "}";
    }
}
