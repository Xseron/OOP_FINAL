package kz.edu.university.exception;

/** thrown when student already failed 3+ courses */
public class TooManyFailsException extends Exception {
    private static final long serialVersionUID = 1L;

    public TooManyFailsException(String msg) {
        super(msg);
    }
}
