package kz.edu.university.research;

import kz.edu.university.exception.NotResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** research project - only researchers can join */
public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;
    private List<Researcher> participants = new ArrayList<>();
    /** field name is intentionally `publishPapers` per diagram (typo or not, follow it) */
    private List<ResearchPaper> publishPapers = new ArrayList<>();

    public ResearchProject() {
    }

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public List<ResearchPaper> getPublishPapers() {
        return publishPapers;
    }

    public void addParticipant(Researcher researcher) {
        participants.add(researcher);
    }

    /** overload for adding by raw object - throws if not Researcher */
    public void addParticipant(Object person) throws NotResearcherException {
        // Pattern matching keeps type-check and cast in one place.
        if (!(person instanceof Researcher r)) {
            throw new NotResearcherException(
                    person.getClass().getSimpleName() + " is not a Researcher");
        }
        // Safe to add only after validation succeeds.
        participants.add(r);
    }

    public void addPaper(ResearchPaper paper) {
        publishPapers.add(paper);
    }

    @Override
    public String toString() {
        return "Project{" + topic + ", " + participants.size() + " participants}";
    }
}
