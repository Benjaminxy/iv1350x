package se.kth.iv1350.amazingpos.util;

/**
 * A listener interface for receiving notifications about sales.
 */
public interface CurrentSaleObserver {
    /**
     * Invoked when a sale has been completed.
     * 
     * @param totalAmount The total amount of the completed sale.
     */
    void addSaleAmount(double totalAmount);
}
