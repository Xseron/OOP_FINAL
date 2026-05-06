package kz.edu.university.research;

import kz.edu.university.enums.Language;
import kz.edu.university.enums.TeacherType;
import kz.edu.university.user.Teacher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** professor — always a researcher (per spec) */
public class Professor extends Teacher implements Researcher {
    private static final long serialVersionUID = 1L;

    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    public Professor() {
    }

    public Professor(int id, String name, String email, String username, String pwd, Language lang,
            double sal, LocalDate hireDt) {
        super(id, name, email, username, pwd, lang, sal, hireDt, TeacherType.PROFESSOR);
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    @Override
    public void joinProject(ResearchProject project) {
        projects.add(project);
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }
}
