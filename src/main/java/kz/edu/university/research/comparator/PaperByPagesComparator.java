package kz.edu.university.research.comparator;

import kz.edu.university.research.ResearchPaper;

import java.io.Serializable;
import java.util.Comparator;

/** longest first */
public class PaperByPagesComparator implements Comparator<ResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getPages(), a.getPages());
    }
}
