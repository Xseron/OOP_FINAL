package kz.edu.university.user;

import kz.edu.university.course.Course;
import kz.edu.university.course.Mark;
import kz.edu.university.enums.Language;
import kz.edu.university.enums.TeacherType;
import kz.edu.university.enums.UrgencyLevel;
import kz.edu.university.message.Complaint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** teacher */
public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private TeacherType teacherType;
    private List<Course> assignedCourses = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(int id, String name, String email, String username, String pwd, Language lang,
            double sal, LocalDate hireDt, TeacherType type) {
        super(id, name, email, username, pwd, lang, sal, hireDt);
        this.teacherType = type;
    }

    public TeacherType getTeacherType() {
        return teacherType;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void setTeacherType(TeacherType t) {
        this.teacherType = t;
    }

    public List<Course> viewCourses() {
        return assignedCourses;
    }

    /** placeholder — could update lessons / details */
    public void manageCourse(Course course) {
        if (!assignedCourses.contains(course))
            assignedCourses.add(course);
    }

    /** put mark for student in course */
    public void putMark(Student student, Course course, Mark mark) {
        // Transcript is the single source of truth for marks and GPA calculations.
        student.getTranscript().addMark(course, mark);
    }

    /** all students who registered for this course (filter from a roster — stub) */
    public List<Student> viewStudents(Course course) {
        // in real system would query roster; stub returns empty
        return new ArrayList<>();
    }

    /** send complaint to dean about student */
    public Complaint sendComplaint(Student student, Manager dean, UrgencyLevel urgency, String text) {
        Complaint c = new Complaint(this, student, dean, urgency, text);
        c.submit();
        return c;
    }
}
