package kz.edu.university.user;

import kz.edu.university.auth.User;
import kz.edu.university.enums.Language;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** sys admin */
public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    /** in-memory user store + log (just for demo) */
    private final List<User> users = new ArrayList<>();
    private final List<String> logs = new ArrayList<>();

    public Admin() {
    }

    public Admin(int id, String name, String email, String username, String pwd, Language lang,
            double sal, LocalDate hireDt) {
        super(id, name, email, username, pwd, lang, sal, hireDt);
    }

    public void addUser(User user) {
        users.add(user);
        logs.add("ADD " + user.getUsername());
    }

    public void removeUser(User user) {
        users.remove(user);
        logs.add("REMOVE " + user.getUsername());
    }

    public void updateUser(User user) {
        // assume already in list, just log
        logs.add("UPDATE " + user.getUsername());
    }

    public List<String> viewLogs() {
        return logs;
    }

    public List<User> getUsers() {
        return users;
    }
}
