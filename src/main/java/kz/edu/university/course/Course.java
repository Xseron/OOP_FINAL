package kz.edu.university.course;

import kz.edu.university.enums.CourseType;
import kz.edu.university.enums.LessonType;
import kz.edu.university.user.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** uni course */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String title;
    private int credits;
    private CourseType courseType;
    private String major;
    private int yearOfStudy;
    private Teacher lectureInstructor;
    private Teacher practiceInstructor;
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(String code, String title, int credits, CourseType type, String major, int year) {
        this.courseCode = code;
        this.title = title;
        this.credits = credits;
        this.courseType = type;
        this.major = major;
        this.yearOfStudy = year;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public String getMajor() {
        return major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public Teacher getLectureInstructor() {
        return lectureInstructor;
    }

    public Teacher getPracticeInstructor() {
        return practiceInstructor;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLectureInstructor(Teacher t) {
        this.lectureInstructor = t;
    }

    public void setPracticeInstructor(Teacher t) {
        this.practiceInstructor = t;
    }

    public void setCourseType(CourseType t) {
        this.courseType = t;
    }

    /** assign by lesson type */
    public void addInstructor(Teacher t, LessonType type) {
        if (type == LessonType.LECTURE)
            lectureInstructor = t;
        else
            practiceInstructor = t;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /** all instructors (skip nulls) */
    public List<Teacher> getInstructors() {
        List<Teacher> r = new ArrayList<>();
        if (lectureInstructor != null)
            r.add(lectureInstructor);
        if (practiceInstructor != null)
            r.add(practiceInstructor);
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Course c))
            return false;
        return Objects.equals(courseCode, c.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return "Course{" + courseCode + " " + title + " (" + credits + "cr)}";
    }
}
