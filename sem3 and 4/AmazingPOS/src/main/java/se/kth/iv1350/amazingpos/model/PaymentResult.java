package se.kth.iv1350.amazingpos.model;

public class PaymentResult {
    private final boolean paymentAccepted;
    private final double totalSaleAmount;
    private final double change;
    private final String paymentMethod;

    public PaymentResult(boolean paymentAccepted, double totalSaleAmount, double change, String paymentMethod) {
        this.paymentAccepted = paymentAccepted;
        this.totalSaleAmount = totalSaleAmount;
        this.change = change;
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentAccepted() {
        return paymentAccepted;
    }

    public double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public double getChange() {
        return change;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Add this method if needed
    public boolean isSuccessful() {
        return paymentAccepted;
    }
}
