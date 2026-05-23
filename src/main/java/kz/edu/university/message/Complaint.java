package kz.edu.university.message;

import kz.edu.university.enums.UrgencyLevel;
import kz.edu.university.user.Manager;
import kz.edu.university.user.Student;
import kz.edu.university.user.Teacher;

import java.io.Serializable;
import java.time.LocalDateTime;

/** teacher's complaint about student to dean */
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    private Teacher teacher;
    private Student student;
    private Manager dean;
    private UrgencyLevel urgencyLevel;
    private String text;
    private LocalDateTime createdAt;

    public Complaint() {
    }

    public Complaint(Teacher teacher, Student student, Manager dean, UrgencyLevel urg, String text) {
        this.teacher = teacher;
        this.student = student;
        this.dean = dean;
        this.urgencyLevel = urg;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Student getStudent() {
        return student;
    }

    public Manager getDean() {
        return dean;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** "send" to dean - just print for demo */
    public void submit() {
        System.out.println("[Complaint " + urgencyLevel + "] from "
                + teacher.getUsername() + " about " + student.getUsername()
                + " to " + dean.getUsername() + ": " + text);
    }

    @Override
    public String toString() {
        return "Complaint{" + urgencyLevel + " " + teacher.getUsername() + "->"
                + dean.getUsername() + " re " + student.getUsername() + "}";
    }
}
