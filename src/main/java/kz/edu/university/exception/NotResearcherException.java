package kz.edu.university.exception;

/** only researchers can join projects */
public class NotResearcherException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotResearcherException(String msg) {
        super(msg);
    }
}
