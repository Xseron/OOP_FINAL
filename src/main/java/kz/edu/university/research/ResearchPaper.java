package kz.edu.university.research;

import kz.edu.university.enums.Format;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** research paper */
public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<String> authors = new ArrayList<>();
    private String journal;
    private int citations;
    private int pages;
    private LocalDate publishDate;
    private String doi;
    private String abstractText;

    public ResearchPaper() {
    }

    public ResearchPaper(String title, List<String> authors, String journal, int pages,
            LocalDate publishDate, String doi) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.publishDate = publishDate;
        this.doi = doi;
        this.citations = 0;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getCitations() {
        return citations;
    }

    public int getPages() {
        return pages;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getDoi() {
        return doi;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String s) {
        this.abstractText = s;
    }

    public void setCitations(int c) {
        this.citations = c;
    }

    /** +1 cit */
    public void addCitation() {
        this.citations++;
    }

    /** length = pages */
    public int getLength() {
        return pages;
    }

    /** plain or bibtex citation */
    public String getCitation(Format format) {
        int year = publishDate != null ? publishDate.getYear() : 0;
        String authorList = String.join(", ", authors);
        return switch (format) {
            case PLAIN_TEXT -> authorList + ". " + title + ". " + journal + ", "
                    + pages + " p, " + year + ".";
            case BIBTEX -> "@article{" + (doi != null ? doi.replaceAll("[^A-Za-z0-9]", "") : "key")
                    + ",\n  author = {" + authorList + "},\n"
                    + "  title = {" + title + "},\n"
                    + "  journal = {" + journal + "},\n"
                    + "  year = {" + year + "},\n"
                    + "  pages = {" + pages + "}\n}";
        };
    }

    /** natural order: newest first */
    @Override
    public int compareTo(ResearchPaper o) {
        if (publishDate == null && o.publishDate == null)
            return 0;
        if (publishDate == null)
            return 1;
        if (o.publishDate == null)
            return -1;
        return o.publishDate.compareTo(publishDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ResearchPaper p))
            return false;
        return Objects.equals(doi, p.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    @Override
    public String toString() {
        return "Paper{" + title + " [" + citations + " cits, " + pages + "p]}";
    }
}
