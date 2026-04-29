package kz.edu.university.research.comparator;

import kz.edu.university.research.ResearchPaper;

import java.io.Serializable;
import java.util.Comparator;

/** newest first */
public class PaperByDateComparator implements Comparator<ResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        if (a.getPublishDate() == null && b.getPublishDate() == null)
            return 0;
        if (a.getPublishDate() == null)
            return 1;
        if (b.getPublishDate() == null)
            return -1;
        return b.getPublishDate().compareTo(a.getPublishDate());
    }
}
