package kz.edu.university.user;

import kz.edu.university.auth.User;
import kz.edu.university.course.Course;
import kz.edu.university.enums.Language;
import kz.edu.university.enums.LessonType;
import kz.edu.university.enums.ManagerType;
import kz.edu.university.news.News;
import kz.edu.university.support.Report;
import kz.edu.university.support.SupportRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** dept / OR manager */
public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;
    private final List<SupportRequest> requests = new ArrayList<>();
    private final List<News> managedNews = new ArrayList<>();
    private final List<Course> registrationCourses = new ArrayList<>();

    public Manager() {
    }

    public Manager(int id, String name, String email, String username, String pwd, Language lang,
            double sal, LocalDate hireDt, ManagerType type) {
        super(id, name, email, username, pwd, lang, sal, hireDt);
        this.managerType = type;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    /** assign course to teacher with lesson type */
    public void assignCourse(Course course, Teacher teacher, LessonType lessonType) {
        course.addInstructor(teacher, lessonType);
        teacher.getAssignedCourses().add(course);
    }

    /** approve student registration (stub: always true) */
    public boolean approveRegistration(Student student, Course course) {
        return true; // simplified
    }

    /** add course to registration pool */
    public void addCourseForRegistration(Course course, String major, int year) {
        // tag is implicit - Course already carries major/year
        registrationCourses.add(course);
    }

    /** make academic report (stub data) */
    public Report createAcademicReport() {
        Report r = new Report("Academic Report", "Average GPA across school: TBD");
        r.generate();
        return r;
    }

    public void manageNews(News news) {
        managedNews.add(news);
    }

    public List<SupportRequest> viewRequests() {
        return requests;
    }

    public List<News> getManagedNews() {
        return managedNews;
    }

    public List<Course> getRegistrationCourses() {
        return registrationCourses;
    }
}
