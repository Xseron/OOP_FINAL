package kz.edu.university.course;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** student's marks per course */
public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Course, Mark> marks = new HashMap<>();

    public Transcript() {}

    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
    }

    public Map<Course, Mark> getMarks() { return marks; }

    /** weighted gpa = sum(total*credits)/sum(credits) */
    public double calculateGPA() {
        double total = 0; int credits = 0;
        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            total += e.getValue().CalculateTotal() * e.getKey().getCredits();
            credits += e.getKey().getCredits();
        }
        return credits == 0 ? 0.0 : total / credits;
    }

    @Override public String toString() {
        return "Transcript{" + marks.size() + " marks, gpa=" + calculateGPA() + "}";
    }
}
