package kz.edu.university.exception;

/** supervisor must have h-index >= 3 */
public class LowHIndexException extends Exception {
    private static final long serialVersionUID = 1L;

    public LowHIndexException(String msg) {
        super(msg);
    }
}
