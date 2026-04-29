package kz.edu.university.course;

import kz.edu.university.user.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** student club / org */
public class StudentOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Student> members = new ArrayList<>();
    private Student head;

    public StudentOrganization() {
    }

    public StudentOrganization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public Student getHead() {
        return head;
    }

    public void addMember(Student student) {
        if (!members.contains(student))
            members.add(student);
    }

    public void setHead(Student student) {
        this.head = student;
        addMember(student);
    }

    @Override
    public String toString() {
        return "Org{" + name + ", " + members.size() + " members}";
    }
}
