package kz.edu.university.storage;

import kz.edu.university.auth.User;
import kz.edu.university.course.Course;
import kz.edu.university.news.News;
import kz.edu.university.research.ResearchJournal;
import kz.edu.university.research.Researcher;
import kz.edu.university.support.SupportRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Map<String, User> users;
    private final List<Course> courses;
    private final List<News> news;
    private final List<ResearchJournal> journals;
    private final List<SupportRequest> requests;
    private final List<Researcher> researchers;

    public SystemState(Map<String, User> users, List<Course> courses, List<News> news,
            List<ResearchJournal> journals, List<SupportRequest> requests,
            List<Researcher> researchers) {
        this.users = users;
        this.courses = courses;
        this.news = news;
        this.journals = journals;
        this.requests = requests;
        this.researchers = researchers;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<News> getNews() {
        return news;
    }

    public List<ResearchJournal> getJournals() {
        return journals;
    }

    public List<SupportRequest> getRequests() {
        return requests;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }
}
