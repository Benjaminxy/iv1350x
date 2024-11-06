package se.kth.iv1350.amazingpos.view;

import se.kth.iv1350.amazingpos.util.CurrentSaleObserver;

/**
 * Shows the total revenue on the user interface.
 */
public class TotalRevenueView implements CurrentSaleObserver {
    private double totalRevenue = 0;

    /**
     * Updates the total revenue with the latest sale amount.
     * 
     * @param totalAmount The total amount of the latest sale.
     */
    @Override
    public void addSaleAmount(double totalAmount) {
        totalRevenue += totalAmount;
        printCurrentRevenue();
    }

    /**
     * Prints the current total revenue.
     */
    private void printCurrentRevenue() {
        System.out.println("Total Revenue is: $" + totalRevenue);
    }
}
