package kz.edu.university;

import kz.edu.university.course.Course;
import kz.edu.university.course.Mark;
import kz.edu.university.enums.CourseType;
import kz.edu.university.enums.DegreeType;
import kz.edu.university.enums.Format;
import kz.edu.university.enums.Language;
import kz.edu.university.enums.LessonType;
import kz.edu.university.enums.NewsTopic;
import kz.edu.university.enums.UrgencyLevel;
import kz.edu.university.exception.CreditLimitExceededException;
import kz.edu.university.exception.LowHIndexException;
import kz.edu.university.exception.NotResearcherException;
import kz.edu.university.exception.TooManyFailsException;
import kz.edu.university.message.Complaint;
import kz.edu.university.news.News;
import kz.edu.university.research.Professor;
import kz.edu.university.research.ResearchJournal;
import kz.edu.university.research.ResearchPaper;
import kz.edu.university.research.ResearchProject;
import kz.edu.university.research.comparator.PaperByCitationComparator;
import kz.edu.university.storage.DataStore;
import kz.edu.university.support.Report;
import kz.edu.university.support.SupportRequest;
import kz.edu.university.user.GraduateStudent;
import kz.edu.university.user.Manager;
import kz.edu.university.user.Student;
import kz.edu.university.user.Teacher;
import kz.edu.university.user.TechSupportSpecialist;
import kz.edu.university.util.LocalizationManager;
import kz.edu.university.util.UserFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** smoke main, proves models work — run with -Dexec.mainClass=kz.edu.university.Demo */
public class Demo {

    public static void main(String[] args) throws Exception {
        // utf-8 console (windows ru/kz)
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        // 1. Singleton + lang switch
        LocalizationManager loc = LocalizationManager.getInstance();
        loc.setLanguage(Language.EN);
        System.out.println("[Lang:EN] " + loc.getMessage("welcome"));
        loc.setLanguage(Language.RU);
        System.out.println("[Lang:RU] " + loc.getMessage("welcome"));
        loc.setLanguage(Language.KZ);
        System.out.println("[Lang:KZ] " + loc.getMessage("welcome"));

        // 2. Factory
        Manager dean = (Manager) UserFactory.create(UserFactory.Role.MANAGER,
                1, "Dean Smith", "dean@uni.kz", "dean", "pwd");
        Teacher teacher = (Teacher) UserFactory.create(UserFactory.Role.TEACHER,
                2, "John Doe", "jdoe@uni.kz", "jdoe", "pwd");
        Student stu = (Student) UserFactory.create(UserFactory.Role.STUDENT,
                3, "Alice", "alice@uni.kz", "alice", "pwd");
        System.out.println("[Factory] " + teacher);

        // login check
        System.out.println("[Auth] login ok? " + teacher.login("pwd"));
        System.out.println("[Auth] login bad? " + teacher.login("wrong"));

        // 3. Strategy + h-index
        Professor prof = new Professor(4, "Dr Bekov", "bekov@uni.kz", "bekov", "pwd",
                Language.EN, 200000, LocalDate.now());
        prof.publishPaper(makePaper("LMS Logs analysis", 12, 8, LocalDate.of(2024, 5, 1)));
        prof.publishPaper(makePaper("Retake influence", 5, 12, LocalDate.of(2025, 1, 15)));
        prof.publishPaper(makePaper("Student perf clustering", 30, 20, LocalDate.of(2023, 11, 30)));
        System.out.println("[Strategy] sorted by citations:");
        prof.printPapers(new PaperByCitationComparator());
        System.out.println("[h-index] " + prof.calculateHIndex());

        // 4. Observer
        ResearchJournal journal = new ResearchJournal("SITE Research Quarterly");
        journal.subscribe(stu);
        journal.subscribe(teacher);
        journal.publishPaper(makePaper("Brand new", 0, 5, LocalDate.now()));

        // 5. LowHIndexException
        GraduateStudent grad = new GraduateStudent(5, "Bob", "bob@uni.kz", "bob", "pwd",
                Language.EN, "ST5", "SITE", "CS", 1, DegreeType.MASTER);
        Professor noobProf = new Professor(6, "Noob", "noob@uni.kz", "noob", "pwd",
                Language.EN, 100000, LocalDate.now());
        // noobProf has 0 papers → h-index 0
        try {
            grad.chooseSupervisor(noobProf);
        } catch (LowHIndexException e) {
            System.out.println("[Exception] caught: " + e.getMessage());
        }
        // good supervisor
        grad.chooseSupervisor(prof);
        System.out.println("[Supervisor] assigned: " + grad.getSupervisor());

        // 6. NotResearcherException
        ResearchProject proj = new ResearchProject("Education ML");
        try {
            proj.addParticipant((Object) stu); // Student is not a Researcher
        } catch (NotResearcherException e) {
            System.out.println("[Exception] caught: " + e.getMessage());
        }
        proj.addParticipant(prof);

        // 7. CreditLimitExceededException
        Course c1 = new Course("CS101", "Intro CS", 12, CourseType.MAJOR, "CS", 1);
        Course c2 = new Course("CS102", "Algorithms", 12, CourseType.MAJOR, "CS", 1);
        try {
            stu.registerForCourse(c1);
            stu.registerForCourse(c2); // 12+12 > 21
        } catch (CreditLimitExceededException | TooManyFailsException e) {
            System.out.println("[Exception] caught: " + e.getMessage());
        }

        // 8. News pin
        News n1 = new News("Hello", "general news", NewsTopic.GENERAL);
        News n2 = new News("Big paper out", "research news", NewsTopic.RESEARCH);
        News n3 = new News("Event", "concert tonight", NewsTopic.EVENT);
        List<News> feed = new java.util.ArrayList<>(Arrays.asList(n1, n2, n3));
        Collections.sort(feed);
        System.out.println("[News] sorted (pinned first):");
        feed.forEach(n -> System.out.println("  " + n));

        // 9. putMark + report stats
        teacher.putMark(stu, c1, new Mark(85, 90, 88));
        Report report = dean.createAcademicReport();
        report.setContent("Stats: " + Report.marksStats(stu.viewMarks()));
        report.export();

        // 10. Complaint
        Complaint cpl = teacher.sendComplaint(stu, dean, UrgencyLevel.HIGH, "missed class 3 times");
        System.out.println("[Complaint] " + cpl);

        // 11. Serialization round-trip
        String path = "data.ser";
        DataStore.getInstance().save(prof, path);
        Professor loaded = DataStore.getInstance().load(path);
        System.out.println("[Serialization] loaded prof: " + loaded
                + ", papers=" + loaded.getPapers().size()
                + ", h-index=" + loaded.calculateHIndex());

        // 12. Tech support flow
        TechSupportSpecialist support = (TechSupportSpecialist) UserFactory.create(
                UserFactory.Role.TECH_SUPPORT, 7, "Sara", "sara@uni.kz", "sara", "pwd");
        SupportRequest req = teacher.createRequest("projector broken in room 305");
        support.getAssignedRequests().add(req);
        req.assign(support);
        System.out.println("[Support] new : " + req);
        support.markViewed(req);
        System.out.println("[Support] view: " + req);
        support.acceptRequest(req);
        support.markDone(req);
        System.out.println("[Support] done: " + req);

        // 13. Citation formats
        ResearchPaper anyPaper = prof.getPapers().get(0);
        System.out.println("[Cite:Plain ] " + anyPaper.getCitation(Format.PLAIN_TEXT));
        System.out.println("[Cite:BibTeX]\n" + anyPaper.getCitation(Format.BIBTEX));

        // 14. Manager assigns course + Student rates teacher
        dean.assignCourse(c1, teacher, LessonType.LECTURE);
        System.out.println("[Assign] " + teacher.getUsername()
                + " has " + teacher.viewCourses().size() + " course(s)");
        stu.rateTeacher(teacher, 5);
        System.out.println("[Rate] " + stu.getUsername()
                + " rated " + teacher.getUsername()
                + " = " + stu.getTeacherRatings().get(teacher));

        System.out.println("\nAll patterns + behaviors demonstrated. Bye.");
    }

    private static ResearchPaper makePaper(String title, int citations, int pages, LocalDate date) {
        ResearchPaper p = new ResearchPaper(title, Arrays.asList("Bekov A."),
                "Journal of Edu Tech", pages, date, "10.1000/" + title.hashCode());
        p.setCitations(citations);
        return p;
    }
}
