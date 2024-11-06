package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.PurchasedItem;
import java.util.List;

/**
 * Handles the connection to the external accounting system.
 */
public class ExternalAccountingSystem {
    private final ExternaInventorySystem inventory;

    /**
     * Creates a new instance of ExternalAccountingSystem.
     * 
     * @param inventory The inventory system to use.
     */
    public ExternalAccountingSystem(ExternaInventorySystem inventory) {
        this.inventory = inventory;
    }

    /**
     * Registers a sale in the accounting system.
     * 
     * @param soldItems The list of items sold.
     * @param finalTotal The total amount of the sale.
     */
    public void registerSale(List<PurchasedItem> soldItems, double finalTotal) {
        for (PurchasedItem item : soldItems) {
            // Logic to register the sale can be added here
        }
    }
}
