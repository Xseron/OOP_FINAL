package kz.edu.university;

import kz.edu.university.auth.User;
import kz.edu.university.course.Course;
import kz.edu.university.course.Mark;
import kz.edu.university.enums.CourseType;
import kz.edu.university.enums.DegreeType;
import kz.edu.university.enums.Format;
import kz.edu.university.enums.Language;
import kz.edu.university.enums.LessonType;
import kz.edu.university.enums.ManagerType;
import kz.edu.university.enums.NewsTopic;
import kz.edu.university.enums.RequestStatus;
import kz.edu.university.enums.TeacherType;
import kz.edu.university.enums.UrgencyLevel;
import kz.edu.university.exception.CreditLimitExceededException;
import kz.edu.university.exception.LowHIndexException;
import kz.edu.university.exception.TooManyFailsException;
import kz.edu.university.news.News;
import kz.edu.university.research.Professor;
import kz.edu.university.research.ResearchJournal;
import kz.edu.university.research.ResearchPaper;
import kz.edu.university.research.Researcher;
import kz.edu.university.research.comparator.PaperByCitationComparator;
import kz.edu.university.research.comparator.PaperByDateComparator;
import kz.edu.university.research.comparator.PaperByPagesComparator;
import kz.edu.university.support.SupportRequest;
import kz.edu.university.user.Admin;
import kz.edu.university.user.Employee;
import kz.edu.university.user.GraduateStudent;
import kz.edu.university.user.Manager;
import kz.edu.university.user.Student;
import kz.edu.university.user.Teacher;
import kz.edu.university.user.TechSupportSpecialist;
import kz.edu.university.util.LocalizationManager;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** interactive cli - login + role-based menus */
public class App {

    private static final Scanner IN = new Scanner(System.in);
    private static final Map<String, User> USERS = new HashMap<>();
    private static final List<Course> COURSES = new ArrayList<>();
    private static final List<News> NEWS = new ArrayList<>();
    private static final List<ResearchJournal> JOURNALS = new ArrayList<>();
    private static final List<SupportRequest> ALL_REQUESTS = new ArrayList<>();
    private static final List<Researcher> RESEARCHERS = new ArrayList<>();

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        seed();
        banner();
        while (true) {
            User u = promptLogin();
            if (u == null) {
                println("bye");
                return;
            }
            menuFor(u);
        }
    }

    // ---------- seed ----------
    private static void seed() {
        Admin admin = new Admin(1, "Root", "root@uni.kz", "admin", "admin",
                Language.EN, 100000, LocalDate.now());
        Manager dean = new Manager(2, "Dean Smith", "dean@uni.kz", "dean", "dean",
                Language.EN, 90000, LocalDate.now(), ManagerType.DEPARTMENT);
        Teacher teacher = new Teacher(3, "John Doe", "jdoe@uni.kz", "jdoe", "jdoe",
                Language.EN, 80000, LocalDate.now(), TeacherType.LECTURER);
        Professor prof = new Professor(4, "Dr David", "david@uni.kz", "david", "david",
                Language.EN, 200000, LocalDate.now());
        Student timur = new Student(5, "Timur", "timur@uni.kz", "timur", "timur",
                Language.EN, "ST5", "SITE", "CS", 1);
        GraduateStudent danil = new GraduateStudent(6, "Danil", "danil@uni.kz", "danil", "danil",
                Language.EN, "ST6", "SITE", "CS", 1, DegreeType.MASTER);
        TechSupportSpecialist superman = new TechSupportSpecialist(7, "Superman", "superman@uni.kz",
                "superman", "superman", Language.EN, 70000, LocalDate.now());

        for (User u : List.of(admin, dean, teacher, prof, timur, danil, superman)) {
            USERS.put(u.getUsername(), u);
            admin.addUser(u);
        }

        // seed courses
        COURSES.add(new Course("CS101", "Intro CS", 6, CourseType.MAJOR, "CS", 1));
        COURSES.add(new Course("CS102", "Algorithms", 6, CourseType.MAJOR, "CS", 1));
        COURSES.add(new Course("MATH201", "Calculus", 5, CourseType.MAJOR, "CS", 1));
        COURSES.add(new Course("PHIL101", "Philosophy", 3, CourseType.FREE_ELECTIVE, "ANY", 1));

        // seed papers for prof
        prof.publishPaper(paper("LMS Logs analysis", 12, 8, LocalDate.of(2024, 5, 1)));
        prof.publishPaper(paper("Retake influence", 5, 12, LocalDate.of(2025, 1, 15)));
        prof.publishPaper(paper("Student perf clustering", 30, 20, LocalDate.of(2023, 11, 30)));
        RESEARCHERS.add(prof);

        // seed news
        NEWS.add(new News("Welcome", "Semester started", NewsTopic.GENERAL));
        NEWS.add(new News("David publishes new paper", "see journal", NewsTopic.RESEARCH));

        // seed journal w/ timur + danil subscribed
        ResearchJournal j = new ResearchJournal("SITE Research Quarterly");
        j.subscribe(timur);
        j.subscribe(danil);
        JOURNALS.add(j);
    }

    private static ResearchPaper paper(String t, int cit, int pg, LocalDate d) {
        ResearchPaper p = new ResearchPaper(t, Arrays.asList("David T."),
                "Journal of Edu Tech", pg, d, "10.1000/" + Math.abs(t.hashCode()));
        p.setCitations(cit);
        return p;
    }

    // ---------- login ----------
    private static void banner() {
        println("=== University Research System ===");
        println("seeded users (login = password):");
        println("  admin    - sys admin: manage users, view action logs");
        println("  dean     - manager: assign courses, news, academic reports");
        println("  jdoe     - teacher: put marks, complaints, messages, support requests");
        println("  david    - professor (researcher): publish papers, h-index, BibTeX cite");
        println("  timur    - undergrad student: register for courses, view marks, rate teachers");
        println("  danil    - grad student (master): + pick supervisor (h-index >= 3)");
        println("  superman - tech support: process request queue (NEW -> VIEWED -> DONE/REJECTED)");
        println("type 'q' as username to quit\n");
    }

    private static User promptLogin() {
        while (true) {
            String name = ask("username");
            if ("q".equalsIgnoreCase(name))
                return null;
            User u = USERS.get(name);
            if (u == null) {
                println("no such user");
                continue;
            }
            String pwd = ask("password");
            if (!u.login(pwd)) {
                println("wrong password");
                continue;
            }
            println("logged in as " + u + "\n");
            return u;
        }
    }

    // ---------- dispatch ----------
    private static void menuFor(User u) {
        if (u instanceof Admin a) adminMenu(a);
        else if (u instanceof Manager m) managerMenu(m);
        else if (u instanceof Professor p) professorMenu(p);
        else if (u instanceof Teacher t) teacherMenu(t);
        else if (u instanceof GraduateStudent g) graduateMenu(g);
        else if (u instanceof Student s) studentMenu(s);
        else if (u instanceof TechSupportSpecialist ts) supportMenu(ts);
        else println("no menu for this role");
        println("logged out\n");
    }

    // ---------- admin ----------
    private static void adminMenu(Admin a) {
        while (true) {
            switch (menu("ADMIN", List.of(
                    "list users", "view logs", "remove user", "logout"))) {
                case 1 -> a.getUsers().forEach(u -> println("  " + u));
                case 2 -> a.viewLogs().forEach(l -> println("  " + l));
                case 3 -> {
                    String name = ask("username to remove");
                    User u = USERS.remove(name);
                    if (u != null) {
                        a.removeUser(u);
                        println("removed");
                    } else println("not found");
                }
                case 0 -> { return; }
            }
        }
    }

    // ---------- manager ----------
    private static void managerMenu(Manager m) {
        while (true) {
            switch (menu("MANAGER (" + m.getManagerType() + ")", List.of(
                    "list courses", "assign course to teacher",
                    "add course to registration pool", "publish news",
                    "list students sorted by gpa", "create academic report",
                    "view requests", "logout"))) {
                case 1 -> COURSES.forEach(c -> println("  " + c));
                case 2 -> {
                    Course c = pickCourse();
                    Teacher t = pickTeacher();
                    if (c != null && t != null) {
                        m.assignCourse(c, t, LessonType.LECTURE);
                        println("assigned " + c + " -> " + t.getUsername());
                    }
                }
                case 3 -> {
                    Course c = pickCourse();
                    if (c != null) {
                        m.addCourseForRegistration(c, c.getMajor(), c.getYearOfStudy());
                        println("added to pool");
                    }
                }
                case 4 -> {
                    String title = ask("title");
                    String body = ask("body");
                    NewsTopic topic = pickEnum(NewsTopic.values());
                    News n = new News(title, body, topic);
                    NEWS.add(n);
                    m.manageNews(n);
                    println("published: " + n);
                }
                case 5 -> {
                    List<Student> studs = studentsSortedByGpa();
                    studs.forEach(s -> println(String.format("  %s gpa=%.2f", s.getUsername(), s.getGpa())));
                }
                case 6 -> {
                    var r = m.createAcademicReport();
                    r.export();
                }
                case 7 -> ALL_REQUESTS.forEach(r -> println("  " + r));
                case 0 -> { return; }
            }
        }
    }

    // ---------- teacher / professor ----------
    private static void teacherMenu(Teacher t) {
        while (true) {
            switch (menu("TEACHER " + t.getUsername(), List.of(
                    "view assigned courses", "put mark", "send complaint",
                    "send message to employee", "create support request", "logout"))) {
                case 1 -> t.viewCourses().forEach(c -> println("  " + c));
                case 2 -> putMarkFlow(t);
                case 3 -> complaintFlow(t);
                case 4 -> messageFlow(t);
                case 5 -> requestFlow(t);
                case 0 -> { return; }
            }
        }
    }

    private static void professorMenu(Professor p) {
        while (true) {
            switch (menu("PROFESSOR " + p.getUsername(), List.of(
                    "publish paper", "print papers (sorted)", "h-index",
                    "print citation", "put mark", "create support request",
                    "logout"))) {
                case 1 -> publishPaperFlow(p);
                case 2 -> printPapersFlow(p);
                case 3 -> println("h-index = " + p.calculateHIndex());
                case 4 -> {
                    if (p.getPapers().isEmpty()) { println("no papers"); break; }
                    ResearchPaper paper = p.getPapers().get(0);
                    Format f = pickEnum(Format.values());
                    println(paper.getCitation(f));
                }
                case 5 -> putMarkFlow(p);
                case 6 -> requestFlow(p);
                case 0 -> { return; }
            }
        }
    }

    private static void publishPaperFlow(Professor p) {
        String t = ask("title");
        int pg = askInt("pages");
        LocalDate d = LocalDate.now();
        ResearchPaper paper = paperOf(t, 0, pg, d);
        p.publishPaper(paper);
        // notify all journals
        JOURNALS.forEach(j -> j.publishPaper(paper));
        // auto-news
        NEWS.add(new News("New paper by " + p.getUsername(),
                "'" + t + "' published", NewsTopic.RESEARCH));
        println("published");
    }

    private static ResearchPaper paperOf(String t, int cit, int pg, LocalDate d) {
        ResearchPaper p = new ResearchPaper(t, Arrays.asList("(self)"),
                "Uni Journal", pg, d, "10.1000/" + Math.abs(t.hashCode()));
        p.setCitations(cit);
        return p;
    }

    private static void printPapersFlow(Professor p) {
        switch (menu("sort by", List.of("citations", "date", "pages"))) {
            case 1 -> p.printPapers(new PaperByCitationComparator());
            case 2 -> p.printPapers(new PaperByDateComparator());
            case 3 -> p.printPapers(new PaperByPagesComparator());
            default -> {}
        }
    }

    // ---------- student ----------
    private static void studentMenu(Student s) {
        while (true) {
            switch (menu("STUDENT " + s.getUsername() + " (gpa=" + s.getGpa() + ")", List.of(
                    "view available courses", "register for course",
                    "view my registered courses", "view marks / transcript",
                    "rate teacher", "subscribe to journal",
                    "view news", "logout"))) {
                case 1 -> COURSES.forEach(c -> println("  " + c));
                case 2 -> registerFlow(s);
                case 3 -> s.viewCourses().forEach(c -> println("  " + c));
                case 4 -> {
                    s.viewMarks().forEach(m -> println("  " + m));
                    println("GPA = " + s.getTranscript().calculateGPA());
                }
                case 5 -> {
                    Teacher t = pickTeacher();
                    if (t != null) {
                        int r = askInt("rating 1..5");
                        s.rateTeacher(t, r);
                        println("rated");
                    }
                }
                case 6 -> {
                    ResearchJournal j = pickJournal();
                    if (j != null) {
                        j.subscribe(s);
                        println("subscribed");
                    }
                }
                case 7 -> printNews();
                case 0 -> { return; }
            }
        }
    }

    private static void graduateMenu(GraduateStudent g) {
        while (true) {
            switch (menu("GRAD " + g.getUsername() + " (" + g.getDegreeType() + ")", List.of(
                    "view courses", "register", "view marks",
                    "choose supervisor", "view supervisor",
                    "view news", "logout"))) {
                case 1 -> COURSES.forEach(c -> println("  " + c));
                case 2 -> registerFlow(g);
                case 3 -> g.viewMarks().forEach(m -> println("  " + m));
                case 4 -> chooseSupervisorFlow(g);
                case 5 -> println("supervisor: " + g.getSupervisor());
                case 6 -> printNews();
                case 0 -> { return; }
            }
        }
    }

    private static void chooseSupervisorFlow(GraduateStudent g) {
        if (RESEARCHERS.isEmpty()) { println("no researchers"); return; }
        for (int i = 0; i < RESEARCHERS.size(); i++)
            println("  " + (i + 1) + ") " + RESEARCHERS.get(i)
                    + " h-index=" + RESEARCHERS.get(i).calculateHIndex());
        int idx = askInt("choice") - 1;
        if (idx < 0 || idx >= RESEARCHERS.size()) return;
        try {
            g.chooseSupervisor(RESEARCHERS.get(idx));
            println("ok");
        } catch (LowHIndexException e) {
            println("rejected: " + e.getMessage());
        }
    }

    // ---------- support ----------
    private static void supportMenu(TechSupportSpecialist t) {
        // pull global queue -> assigned
        for (SupportRequest r : ALL_REQUESTS) {
            if (r.getAssignee() == null) {
                r.assign(t);
                t.getAssignedRequests().add(r);
            }
        }
        while (true) {
            switch (menu("SUPPORT " + t.getUsername(), List.of(
                    "view all", "view new", "process next (view->accept->done)",
                    "reject one", "logout"))) {
                case 1 -> t.getAssignedRequests().forEach(r -> println("  " + r));
                case 2 -> t.viewNewRequests().forEach(r -> println("  " + r));
                case 3 -> {
                    SupportRequest r = pickRequest(t);
                    if (r != null) {
                        t.markViewed(r);
                        t.acceptRequest(r);
                        t.markDone(r);
                        println("done: " + r);
                    }
                }
                case 4 -> {
                    SupportRequest r = pickRequest(t);
                    if (r != null) {
                        t.markViewed(r);
                        t.rejectRequest(r);
                        println("rejected: " + r);
                    }
                }
                case 0 -> { return; }
            }
        }
    }

    private static SupportRequest pickRequest(TechSupportSpecialist t) {
        List<SupportRequest> open = t.getAssignedRequests().stream()
                .filter(r -> r.getStatus() != RequestStatus.DONE
                        && r.getStatus() != RequestStatus.REJECTED)
                .toList();
        if (open.isEmpty()) { println("none open"); return null; }
        for (int i = 0; i < open.size(); i++)
            println("  " + (i + 1) + ") " + open.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < open.size()) ? open.get(idx) : null;
    }

    // ---------- shared flows ----------
    private static void registerFlow(Student s) {
        Course c = pickCourse();
        if (c == null) return;
        try {
            s.registerForCourse(c);
            println("registered");
        } catch (CreditLimitExceededException | TooManyFailsException e) {
            println("denied: " + e.getMessage());
        }
    }

    private static void putMarkFlow(Teacher t) {
        Student s = pickStudent();
        Course c = pickCourse();
        if (s == null || c == null) return;
        int a1 = askInt("att1");
        int a2 = askInt("att2");
        int fin = askInt("final");
        t.putMark(s, c, new Mark(a1, a2, fin));
        s.recomputeGpa();
        println("mark recorded, gpa=" + s.getGpa());
    }

    private static void complaintFlow(Teacher t) {
        Student s = pickStudent();
        if (s == null) return;
        Manager dean = (Manager) USERS.get("dean");
        UrgencyLevel u = pickEnum(UrgencyLevel.values());
        String txt = ask("text");
        var c = t.sendComplaint(s, dean, u, txt);
        println("sent: " + c);
    }

    private static void messageFlow(Employee from) {
        Employee to = pickEmployee();
        if (to == null) return;
        String txt = ask("text");
        var m = from.sendMessage(to, txt);
        println("sent: " + m);
    }

    private static void requestFlow(Employee from) {
        String d = ask("describe issue");
        var r = from.createRequest(d);
        ALL_REQUESTS.add(r);
        println("request created: " + r);
    }

    private static void printNews() {
        List<News> sorted = new ArrayList<>(NEWS);
        java.util.Collections.sort(sorted);
        sorted.forEach(n -> println("  " + n));
    }

    // ---------- pickers ----------
    private static Course pickCourse() {
        if (COURSES.isEmpty()) { println("no courses"); return null; }
        for (int i = 0; i < COURSES.size(); i++) println("  " + (i + 1) + ") " + COURSES.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < COURSES.size()) ? COURSES.get(idx) : null;
    }

    private static Teacher pickTeacher() {
        List<Teacher> ts = USERS.values().stream()
                .filter(u -> u instanceof Teacher).map(u -> (Teacher) u).toList();
        if (ts.isEmpty()) { println("no teachers"); return null; }
        for (int i = 0; i < ts.size(); i++) println("  " + (i + 1) + ") " + ts.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < ts.size()) ? ts.get(idx) : null;
    }

    private static Student pickStudent() {
        List<Student> ss = USERS.values().stream()
                .filter(u -> u instanceof Student).map(u -> (Student) u).toList();
        if (ss.isEmpty()) { println("no students"); return null; }
        for (int i = 0; i < ss.size(); i++) println("  " + (i + 1) + ") " + ss.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < ss.size()) ? ss.get(idx) : null;
    }

    private static Employee pickEmployee() {
        List<Employee> es = USERS.values().stream()
                .filter(u -> u instanceof Employee).map(u -> (Employee) u).toList();
        if (es.isEmpty()) { println("no employees"); return null; }
        for (int i = 0; i < es.size(); i++) println("  " + (i + 1) + ") " + es.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < es.size()) ? es.get(idx) : null;
    }

    private static ResearchJournal pickJournal() {
        if (JOURNALS.isEmpty()) { println("no journals"); return null; }
        for (int i = 0; i < JOURNALS.size(); i++) println("  " + (i + 1) + ") " + JOURNALS.get(i));
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < JOURNALS.size()) ? JOURNALS.get(idx) : null;
    }

    private static List<Student> studentsSortedByGpa() {
        List<Student> ss = new ArrayList<>(USERS.values().stream()
                .filter(u -> u instanceof Student).map(u -> (Student) u).toList());
        ss.sort(Comparator.naturalOrder()); // by gpa desc, see Student.compareTo
        return ss;
    }

    private static <E extends Enum<E>> E pickEnum(E[] values) {
        for (int i = 0; i < values.length; i++) println("  " + (i + 1) + ") " + values[i]);
        int idx = askInt("choice") - 1;
        return (idx >= 0 && idx < values.length) ? values[idx] : values[0];
    }

    // ---------- io helpers ----------
    private static int menu(String title, List<String> items) {
        println("\n--- " + title + " ---");
        for (int i = 0; i < items.size() - 1; i++) println("  " + (i + 1) + ") " + items.get(i));
        println("  0) " + items.get(items.size() - 1));
        return askInt("> ");
    }

    private static String ask(String label) {
        System.out.print(label + ": ");
        System.out.flush();
        return IN.nextLine().trim();
    }

    private static int askInt(String label) {
        try {
            return Integer.parseInt(ask(label));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }

    // make LocalizationManager init eagerly so unused-warning shrinks
    @SuppressWarnings("unused")
    private static final LocalizationManager LOC = LocalizationManager.getInstance();
}
