package kz.edu.university.research;

import java.util.Comparator;
import java.util.List;

/** research-capable role */
public interface Researcher {

    List<ResearchPaper> getPapers();

    void publishPaper(ResearchPaper paper);

    void joinProject(ResearchProject project);

    /** sort papers by given comparator and print */
    default void printPapers(Comparator<ResearchPaper> comparator) {
        getPapers().stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    /** h-index = max i where papers[i].cits >= i+1, sorted desc */
    default int calculateHIndex() {
        List<ResearchPaper> sorted = getPapers().stream()
                .sorted((a, b) -> Integer.compare(b.getCitations(), a.getCitations()))
                .toList();

        int h = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }
}
