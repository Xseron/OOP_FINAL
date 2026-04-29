package kz.edu.university.user;

import kz.edu.university.enums.DegreeType;
import kz.edu.university.enums.Language;
import kz.edu.university.exception.LowHIndexException;
import kz.edu.university.research.ResearchPaper;
import kz.edu.university.research.ResearchProject;
import kz.edu.university.research.Researcher;

import java.util.ArrayList;
import java.util.List;

/** master / phd student */
public class GraduateStudent extends Student implements Researcher {
    private static final long serialVersionUID = 1L;

    private DegreeType degreeType;
    /** diagram names this 'supervisors' but types it singular Researcher */
    private Researcher supervisors;
    private List<ResearchPaper> diplomaPapers = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();

    public GraduateStudent() {
    }

    public GraduateStudent(int id, String name, String email, String username, String pwd, Language lang,
            String studentId, String school, String major, int year, DegreeType degree) {
        super(id, name, email, username, pwd, lang, studentId, school, major, year);
        this.degreeType = degree;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public Researcher getSupervisor() {
        return supervisors;
    }

    public List<ResearchPaper> getDiplomaPapers() {
        return diplomaPapers;
    }

    /** assign supervisor — h-index >= 3 required */
    public void chooseSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor.calculateHIndex() < 3) {
            throw new LowHIndexException(
                    "Supervisor h-index=" + supervisor.calculateHIndex() + " < 3");
        }
        this.supervisors = supervisor;
    }

    public void addDiplomaPaper(ResearchPaper p) {
        diplomaPapers.add(p);
    }

    // --- Researcher interface ---

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        diplomaPapers.add(paper);
    }

    @Override
    public void joinProject(ResearchProject project) {
        projects.add(project);
    }
}
