package kz.edu.university.user;

import kz.edu.university.auth.User;
import kz.edu.university.course.Course;
import kz.edu.university.course.Mark;
import kz.edu.university.course.StudentOrganization;
import kz.edu.university.course.Transcript;
import kz.edu.university.enums.Language;
import kz.edu.university.exception.CreditLimitExceededException;
import kz.edu.university.exception.TooManyFailsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** undergrad student */
public class Student extends User implements Comparable<Student> {
    private static final long serialVersionUID = 1L;
    private static final int CREDIT_CAP = 21;
    private static final int FAIL_CAP = 3;

    private String studentId;
    private String school;
    private String major;
    private int yearOfStudy;
    private double gpa;
    private int currentCredits;
    private int failedCoursesCount;
    private Transcript transcript = new Transcript();
    private List<Course> registeredCourses = new ArrayList<>();
    private List<StudentOrganization> organizations = new ArrayList<>();
    /** teacher -> rating, for rateTeacher demo */
    private Map<Teacher, Integer> teacherRatings = new HashMap<>();

    public Student() {
    }

    public Student(int id, String name, String email, String username, String pwd, Language lang,
            String studentId, String school, String major, int year) {
        super(id, name, email, username, pwd, lang);
        this.studentId = studentId;
        this.school = school;
        this.major = major;
        this.yearOfStudy = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getFailedCoursesCount() {
        return failedCoursesCount;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public List<StudentOrganization> getOrganizations() {
        return organizations;
    }

    public Map<Teacher, Integer> getTeacherRatings() {
        return teacherRatings;
    }

    public void setSchool(String s) {
        this.school = s;
    }

    public void setMajor(String m) {
        this.major = m;
    }

    public void setYearOfStudy(int y) {
        this.yearOfStudy = y;
    }

    public void setFailedCoursesCount(int n) {
        this.failedCoursesCount = n;
    }

    public List<Course> viewCourses() {
        return registeredCourses;
    }

    /** register w/ credit + fail caps */
    public void registerForCourse(Course course)
            throws CreditLimitExceededException, TooManyFailsException {
        // Registration is blocked globally once fail threshold is reached, regardless of course load.
        if (failedCoursesCount >= FAIL_CAP) {
            throw new TooManyFailsException("Failed " + failedCoursesCount + " courses, can't register");
        }
        // Enforce total semester load limit before mutating student state.
        if (currentCredits + course.getCredits() > CREDIT_CAP) {
            throw new CreditLimitExceededException(
                    "Cant exceed " + CREDIT_CAP + " credits, has " + currentCredits + "+" + course.getCredits());
        }
        registeredCourses.add(course);
        currentCredits += course.getCredits();
    }

    /** marks from transcript */
    public List<Mark> viewMarks() {
        return new ArrayList<>(transcript.getMarks().values());
    }

    public Transcript getTranscriptCopy() {
        return transcript;
    }

    /** lecture instructor of given course */
    public Teacher viewTeacherInfo(Course course) {
        return course.getLectureInstructor();
    }

    /** rate teacher 1..5 */
    public void rateTeacher(Teacher teacher, int rating) {
        teacherRatings.put(teacher, rating);
    }

    public void joinOrganization(StudentOrganization org) {
        organizations.add(org);
        org.addMember(this);
    }

    /** recompute gpa from transcript */
    public void recomputeGpa() {
        // GPA is derived data; we recompute from transcript instead of storing incremental deltas.
        this.gpa = transcript.calculateGPA();
    }

    /** sort by gpa desc */
    @Override
    public int compareTo(Student other) {
        return Double.compare(other.gpa, this.gpa);
    }
}
