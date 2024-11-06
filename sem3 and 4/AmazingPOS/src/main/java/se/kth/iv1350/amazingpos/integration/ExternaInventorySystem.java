package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.dto.ItemDTO;
import se.kth.iv1350.amazingpos.model.exception.DatabaseFailureException;
import java.util.ArrayList;

/**
 * Handles inventory management.
 */
public class ExternaInventorySystem {
    private ArrayList<InventoryEntry> inventory = new ArrayList<>();

    /**
     * Creates an instance and initializes the inventory.
     */
    public ExternaInventorySystem() {
        initializeInventory();
    }

    /**
     * Initializes the inventory with items.
     */
    private void initializeInventory() {
        registerItem(new ItemDTO("0003", "kiwi", 2.0, 0.15), 10);
        registerItem(new ItemDTO("0005", "tomato", 1.0, 0.10), 20);
    }

    /**
     * Registers a new item in the inventory.
     * 
     * @param itemDTO The item to add.
     * @param initialQuantity The quantity of the item.
     */
    public void registerItem(ItemDTO itemDTO, int initialQuantity) {
        inventory.add(new InventoryEntry(itemDTO, initialQuantity));
    }

    /**
     * Retrieves an item from the inventory.
     * 
     * @param id The ID of the item.
     * @return The item if found, otherwise null.
     * @throws DatabaseFailureException If the database connection fails.
     */
    public ItemDTO getItem(String id) throws DatabaseFailureException {
        if (id.equals("999")) {
            throw new DatabaseFailureException("Database connection failed for item ID: " + id);
        }

        for (InventoryEntry entry : inventory) {
            if (entry.item.getId().equals(id)) {
                return entry.item;
            }
        }
        return null;
    }

    /**
     * Updates the inventory for a specific item.
     * 
     * @param id The ID of the item.
     * @param quantity The quantity to update.
     * @return True if the update is successful, otherwise false.
     */
    public boolean updateInventory(String id, int quantity) {
        for (InventoryEntry entry : inventory) {
            if (entry.item.getId().equals(id) && entry.stock >= quantity) {
                entry.stock -= quantity;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the available stock for a specific item.
     * 
     * @param id The ID of the item.
     * @return The available stock.
     */
    public int getAvailableStock(String id) {
        for (InventoryEntry entry : inventory) {
            if (entry.item.getId().equals(id)) {
                return entry.stock;
            }
        }
        return 0;
    }

    /**
     * Represents an entry in the inventory.
     */
    private static class InventoryEntry {
        ItemDTO item;
        int stock;

        InventoryEntry(ItemDTO item, int stock) {
            this.item = item;
            this.stock = stock;
        }
    }
}
