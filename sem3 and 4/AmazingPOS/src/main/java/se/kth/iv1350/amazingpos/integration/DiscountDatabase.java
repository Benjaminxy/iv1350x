package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.CurrentSale;
import se.kth.iv1350.amazingpos.model.PurchasedItem;
import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;

import java.util.List;

/**
 * Handles the calculation and application of discounts.
 */
public class DiscountDatabase {

    /**
     * Calculates the total discounts for a list of purchased items and a customer.
     * 
     * @param items    The list of purchased items.
     * @param customer The customer making the purchase.
     * @return The total discount amount.
     */
    public double calculateTotalDiscounts(List<PurchasedItem> items, CustomerDTO customer) {
        double totalDiscount = 0;

        for (PurchasedItem item : items) {
            double itemPriceWithVAT = item.getPrice() * (1 + item.getVatRate());
            totalDiscount += calculateItemDiscount(itemPriceWithVAT, item) * item.getQuantity();
        }

        double totalAfterVAT = items.stream()
                                    .mapToDouble(item -> item.getPrice() * item.getQuantity() * (1 + item.getVatRate()))
                                    .sum();

        totalDiscount += calculateCustomerDiscount(totalAfterVAT, customer);
        return totalDiscount;
    }

    /**
     * Calculates the discount for a single item.
     * 
     * @param itemPriceWithVAT The price of the item including VAT.
     * @param item             The purchased item.
     * @return The discount amount for the item.
     */
    private double calculateItemDiscount(double itemPriceWithVAT, PurchasedItem item) {
        switch (item.getDescription().toLowerCase()) {
            case "kiwi":
                return itemPriceWithVAT * 0.07;  
            case "tomato":
                return itemPriceWithVAT * 0.05;  
            default:
                return 0;  // No discount for other items
        }
    }

    /**
     * Calculates the discount for a customer based on their membership status.
     * 
     * @param totalAfterVAT The total price after VAT.
     * @param customer      The customer making the purchase.
     * @return The discount amount for the customer.
     */
    private double calculateCustomerDiscount(double totalAfterVAT, CustomerDTO customer) {
        if (customer != null && customer.isMember()) {
            return totalAfterVAT * 0.05;  // 5% additional discount for members
        }
        return 0;  // No additional discount for non-members or if customer is null
    }

    /**
     * Applies discounts to the current sale and completes the sale.
     * 
     * @param currentSale The current sale.
     * @param customer    The customer making the purchase.
     * @param accounting  The external accounting system to register the sale.
     * @return The total amount after discounts are applied.
     */
    public double applyDiscountsAndCompleteSale(CurrentSale currentSale, CustomerDTO customer, ExternalAccountingSystem accounting) {
        double totalBeforeDiscount = currentSale.getTotalAmount();
        double totalDiscount = calculateTotalDiscounts(currentSale.getCurrentSaleItems(), customer);
        double totalAfterDiscount = totalBeforeDiscount - totalDiscount;
        currentSale.updatePriceAfterDiscounts(totalAfterDiscount);
        accounting.registerSale(currentSale.getCurrentSaleItems(), totalAfterDiscount);
        return totalAfterDiscount;
    }
}
