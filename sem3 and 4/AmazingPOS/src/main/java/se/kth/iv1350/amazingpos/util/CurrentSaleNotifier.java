package se.kth.iv1350.amazingpos.util;

/**
 * The interface for a subject in the observer pattern.
 */
public interface CurrentSaleNotifier {
    /**
     * Adds an observer to be notified when a sale is completed.
     * 
     * @param observer The observer to add.
     */
    void addObserver(CurrentSaleObserver observer);

    /**
     * Notifies all observers about a completed sale.
     * 
     * @param totalAmount The total amount of the completed sale.
     */
    void notifyObservers(double totalAmount);
}
