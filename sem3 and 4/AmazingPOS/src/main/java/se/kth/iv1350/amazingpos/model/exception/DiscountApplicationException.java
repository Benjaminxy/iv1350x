package se.kth.iv1350.amazingpos.model.exception;

/**
 * Thrown when there is an error applying discounts.
 */
public class DiscountApplicationException extends Exception {
    /**
     * Creates a new instance with a specified error message and cause.
     * 
     * @param errorMessage The error message.
     * @param error The cause of the error.
     */
    public DiscountApplicationException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
