package se.kth.iv1350.amazingpos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents a receipt for a sale.
 */
public class Receipt {
    private List<PurchasedItem> items;
    private double total;
    private double paid;
    private double discountRate;
    private String paymentMethod;
    private double totalPriceAfterVAT;
    private double totalDiscountPercentage;

    /**
     * Creates a new receipt with the given transaction details.
     * 
     * @param data The transaction details.
     */
    public Receipt(TransactionDetails data) {
        this.items = data.getItems();
        this.total = data.getTotal();
        this.paid = data.getPaid();
        this.discountRate = data.getDiscountRate();
        this.paymentMethod = data.getPaymentMethod();
        this.totalPriceAfterVAT = data.getTotalPriceAfterVAT();
        this.totalDiscountPercentage = data.getTotalDiscountPercentage();
    }

    /**
     * Creates a string representation of the receipt.
     * 
     * @return The receipt as a string.
     */
    public String createReceiptString() {
        StringBuilder builder = new StringBuilder();
        builder.append("----- ----------------------------------------\n");
        builder.append("----- -----------------------------------------\n");
        builder.append("----- Receipt -----\n");
        builder.append("Date/Time: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        for (PurchasedItem item : items) {
            builder.append(item.getDescription()).append(" (").append(item.getItemId()).append(") x")
                    .append(item.getQuantity()).append(" - $").append(String.format("%.2f", item.getPrice()))
                    .append(" each, VAT: ").append(String.format("%.2f%%", item.getVatRate() * 100)).append("\n");
        }
        builder.append("Total price after VAT: $").append(String.format("%.2f", totalPriceAfterVAT)).append("\n");
        builder.append("Discount: $").append(String.format("%.2f", discountRate)).append("\n");
        builder.append("Discount Percentage: ").append(String.format("%.2f", totalDiscountPercentage)).append("%\n");
        builder.append("Total: $").append(String.format("%.2f", total)).append("\n");
        builder.append("Paid: $").append(String.format("%.2f", paid)).append(" via ").append(paymentMethod).append("\n");
        builder.append("Change: $").append(String.format("%.2f", paid - total)).append("\n");
        builder.append("----- Thank You! -----\n");
        builder.append("----- ----------------------------------------\n");
        builder.append("----- -----------------------------------------\n");
        return builder.toString();
    }
}
