package kz.edu.university.course;

import java.io.Serializable;

/** student mark - 1st att / 2nd att / final */
public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    public Mark() {}

    public Mark(double a1, double a2, double fin) {
        validate(a1); validate(a2); validate(fin);
        this.firstAttestation = a1;
        this.secondAttestation = a2;
        this.finalExam = fin;
    }

    public double getFirstAttestation() { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam() { return finalExam; }


    public void setFirstAttestation(double v) { validate(v); this.firstAttestation = v; }
    public void setSecondAttestation(double v) { validate(v); this.secondAttestation = v; }
    public void setFinalExam(double v) { validate(v); this.finalExam = v; }

    /** 30/30/40 standart */
    public double CalculateTotal() {
        // Final exam has the largest weight, so poor finals strongly impact total.
        return 0.3 * firstAttestation + 0.3 * secondAttestation + 0.4 * finalExam;
    }

    /** letter from total */
    public String getLetterGrade() {
        double t = CalculateTotal();
        // Boundaries are checked from highest to lowest so first match is the final grade.
        if (t >= 90) return "A";
        if (t >= 75) return "B";
        if (t >= 60) return "C";
        if (t >= 50) return "D";
        return "F";
    }

    private static void validate(double v) {
        if (v < 0 || v > 100)
            throw new IllegalArgumentException("Mark must be between 0 and 100, got: " + v);
    }

    @Override public String toString() {
        return "Mark{" + firstAttestation + "/" + secondAttestation + "/" + finalExam
                + " = " + CalculateTotal() + " (" + getLetterGrade() + ")}";
    }
}
