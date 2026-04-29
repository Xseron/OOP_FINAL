package kz.edu.university.research;

import kz.edu.university.auth.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** uni research journal — Observer: notifies subscribers on publish */
public class ResearchJournal implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<User> subscribers = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();

    public ResearchJournal() {}

    public ResearchJournal(String name) { this.name = name; }

    public String getName() { return name; }
    public List<User> getSubscribers() { return subscribers; }
    public List<ResearchPaper> getPapers() { return papers; }

    public void subscribe(User user) {
        if (!subscribers.contains(user)) subscribers.add(user);
    }

    public void unsubscribe(User user) { subscribers.remove(user); }

    /** publish paper + notify everyone */
    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        notifySubscribers();
    }

    /** push to all subs */
    public void notifySubscribers() {
        if (papers.isEmpty()) return;
        ResearchPaper latest = papers.get(papers.size() - 1);
        for (User u : subscribers) {
            System.out.println("[" + name + "] notify " + u.getUsername()
                    + ": new paper '" + latest.getTitle() + "'");
        }
    }

    @Override public String toString() {
        return "Journal{" + name + ", " + papers.size() + " papers, "
                + subscribers.size() + " subs}";
    }
}
