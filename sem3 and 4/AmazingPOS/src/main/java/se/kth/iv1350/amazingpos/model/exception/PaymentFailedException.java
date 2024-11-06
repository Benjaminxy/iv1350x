package se.kth.iv1350.amazingpos.model.exception;

/**
 * Exception thrown when a payment fails.
 */
public class PaymentFailedException extends Exception {

    /**
     * Creates a new instance with the specified detail message.
     *
     * @param message The detail message.
     */
    public PaymentFailedException(String message) {
        super(message);
    }
}
