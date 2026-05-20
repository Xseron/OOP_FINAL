package kz.edu.university.user;

import kz.edu.university.auth.User;
import kz.edu.university.enums.Language;
import kz.edu.university.message.Message;
import kz.edu.university.support.SupportRequest;

import java.io.Serializable;
import java.time.LocalDate;

/** abstract employee */
public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private double salary;
    private LocalDate hireDate;

    protected Employee() {
    }

    protected Employee(int id, String name, String email, String username, String pwd, Language lang,
            double sal, LocalDate hireDt) {
        super(id, name, email, username, pwd, lang);
        this.salary = sal;
        this.hireDate = hireDt;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setSalary(double s) {
        if (s < 0){
            throw new IllegalArgumentException("Salary cannot be negative, got: " + s);
        }
        this.salary = s;
    }

    /** send msg to other employee */
    public Message sendMessage(Employee receiver, String text) {
        Message m = new Message(this, receiver, text);
        return m;
    }

    /** create support req */
    public SupportRequest createRequest(String description) {
        return new SupportRequest(this, description);
    }
}
