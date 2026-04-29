package kz.edu.university.auth;

import kz.edu.university.common.Person;
import kz.edu.university.enums.Language;

/** abstract authenticatable user */
public abstract class User extends Person {
    private static final long serialVersionUID = 1L;

    protected String username;
    protected String password;
    protected Language language;

    protected User() {
    }

    protected User(int id, String name, String email, String username, String pwd, Language lang) {
        super(id, name, email);
        this.username = username;
        this.password = pwd;
        this.language = lang;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public Language getLanguage() {
        return language;
    }

    /** check pwd */
    public boolean login(String pwd) {
        return this.password != null && this.password.equals(pwd);
    }

    /** noop, no runtime */
    public void logout() {
    }

    /** switch ui lang */
    public void switchLanguage(Language lang) {
        this.language = lang;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{username=" + username + "}";
    }
}
