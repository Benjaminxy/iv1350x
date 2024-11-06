package se.kth.iv1350.amazingpos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.amazingpos.model.exception.PaymentFailedException;

public class PaymentTest {

    @Test
    public void testProcessPaymentSuccess() {
        Payment payment = new Payment();
        double amountPaid = 100.0;
        double totalSaleAmount = 50.0;
        String paymentMethod = "Credit Card";

        try {
            PaymentResult result = payment.processPayment(amountPaid, totalSaleAmount, paymentMethod);
            assertTrue(result.isPaymentAccepted());
            assertEquals(totalSaleAmount, result.getTotalSaleAmount());
            assertEquals(amountPaid - totalSaleAmount, result.getChange());
            assertEquals(paymentMethod, result.getPaymentMethod());
        } catch (PaymentFailedException ex) {
            fail("Payment should not fail with sufficient amount paid.");
        }
    }

    @Test
    public void testProcessPaymentFailure() {
        Payment payment = new Payment();
        double amountPaid = 30.0;
        double totalSaleAmount = 50.0;
        String paymentMethod = "Credit Card";

        assertThrows(PaymentFailedException.class, () -> {
            payment.processPayment(amountPaid, totalSaleAmount, paymentMethod);
        });
    }
}
