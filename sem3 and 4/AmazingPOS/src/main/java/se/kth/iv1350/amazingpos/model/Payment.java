package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.model.exception.PaymentFailedException;

/**
 * Handles payment processing.
 */
public class Payment {

    /**
     * Processes a payment.
     * 
     * @param amountPaid The amount paid by the customer.
     * @param totalSaleAmount The total amount of the sale.
     * @param paymentMethod The payment method used.
     * @return The result of the payment.
     * @throws PaymentFailedException If the payment fails.
     */
    public PaymentResult processPayment(double amountPaid, double totalSaleAmount, String paymentMethod) throws PaymentFailedException {
        boolean paymentAccepted = amountPaid >= totalSaleAmount;
        double change;

        if (paymentAccepted) {
            change = amountPaid - totalSaleAmount;
        } else {
            change = 0;
            throw new PaymentFailedException("Payment failed. Not enough money provided.");
        }

        return new PaymentResult(paymentAccepted, totalSaleAmount, change, paymentMethod);
    }
}
