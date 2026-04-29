package kz.edu.university.util;

import kz.edu.university.auth.User;
import kz.edu.university.enums.Language;
import kz.edu.university.user.Admin;
import kz.edu.university.user.Manager;
import kz.edu.university.user.Student;
import kz.edu.university.user.Teacher;
import kz.edu.university.user.TechSupportSpecialist;
import kz.edu.university.enums.ManagerType;
import kz.edu.university.enums.TeacherType;

import java.time.LocalDate;

/** factory for users by role */
public class UserFactory {

    public enum Role {
        ADMIN, MANAGER, TEACHER, STUDENT, TECH_SUPPORT
    }

    private UserFactory() {
    }

    /** make a user with given role + minimal info */
    public static User create(Role role, int id, String name, String email,
            String username, String pwd) {
        Language lang = Language.EN;
        LocalDate today = LocalDate.now();
        return switch (role) {
            case ADMIN -> new Admin(id, name, email, username, pwd, lang, 100000, today);
            case MANAGER -> new Manager(id, name, email, username, pwd, lang, 90000, today, ManagerType.DEPARTMENT);
            case TEACHER -> new Teacher(id, name, email, username, pwd, lang, 80000, today, TeacherType.LECTURER);
            case STUDENT -> new Student(id, name, email, username, pwd, lang,
                    "ST" + id, "SITE", "CS", 1);
            case TECH_SUPPORT -> new TechSupportSpecialist(id, name, email, username, pwd, lang, 70000, today);
        };
    }
}
