package kz.edu.university.research.comparator;

import kz.edu.university.research.ResearchPaper;

import java.io.Serializable;
import java.util.Comparator;

/** most cited first */
public class PaperByCitationComparator implements Comparator<ResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getCitations(), a.getCitations());
    }
}
