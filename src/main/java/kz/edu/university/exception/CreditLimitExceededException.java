package kz.edu.university.exception;

/** thrown when student tries >21 credits */
public class CreditLimitExceededException extends Exception {
    private static final long serialVersionUID = 1L;

    public CreditLimitExceededException(String msg) {
        super(msg);
    }
}
