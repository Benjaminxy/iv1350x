package se.kth.iv1350.amazingpos.model.exception;

/**
 * Thrown when there is a database failure.
 */
public class DatabaseFailureException extends Exception {
    /**
     * Creates a new instance with a specified error message.
     * 
     * @param errorMessage The error message.
     */
    public DatabaseFailureException(String errorMessage) {
        super(errorMessage);
    }
}
