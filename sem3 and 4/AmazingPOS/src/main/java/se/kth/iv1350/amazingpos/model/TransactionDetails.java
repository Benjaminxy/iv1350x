package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.integration.Printer;
import java.util.List;

/**
 * Represents the details of a transaction.
 */
public class TransactionDetails {
    private List<PurchasedItem> items;
    private double total;
    private double paid;
    private double discountRate;
    private String paymentMethod;
    private double totalPriceAfterVAT;
    private double totalDiscountPercentage; 

    /**
     * Creates a new instance with transaction details.
     * 
     * @param items The items purchased.
     * @param total The total amount.
     * @param paid The amount paid.
     * @param discountRate The discount rate applied.
     * @param paymentMethod The payment method used.
     * @param totalPriceAfterVAT The total price after VAT.
     * @param totalDiscountPercentage The total discount percentage.
     */
    public TransactionDetails(List<PurchasedItem> items, double total, double paid, double discountRate, String paymentMethod, double totalPriceAfterVAT, double totalDiscountPercentage) {
        this.items = items;
        this.total = total;
        this.paid = paid;
        this.discountRate = discountRate;
        this.paymentMethod = paymentMethod;
        this.totalPriceAfterVAT = totalPriceAfterVAT;
        this.totalDiscountPercentage = totalDiscountPercentage;
    }

    /**
     * Gets the total discount percentage.
     * 
     * @return The total discount percentage.
     */
    public double getTotalDiscountPercentage() {
        return totalDiscountPercentage;
    }

    /**
     * Gets the items purchased.
     * 
     * @return The list of items.
     */
    public List<PurchasedItem> getItems() {
        return items;
    }

    /**
     * Gets the total amount.
     * 
     * @return The total amount.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Gets the amount paid.
     * 
     * @return The amount paid.
     */
    public double getPaid() {
        return paid;
    }

    /**
     * Gets the discount rate.
     * 
     * @return The discount rate.
     */
    public double getDiscountRate() {
        return discountRate;
    }

    /**
     * Gets the payment method.
     * 
     * @return The payment method.
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Gets the total price after VAT.
     * 
     * @return The total price after VAT.
     */
    public double getTotalPriceAfterVAT() {
        return totalPriceAfterVAT;
    }

    /**
     * Prints the receipt using the given printer.
     * 
     * @param printer The printer to use.
     */
    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(this);
        printer.printReceipt(receipt);
    }
}
