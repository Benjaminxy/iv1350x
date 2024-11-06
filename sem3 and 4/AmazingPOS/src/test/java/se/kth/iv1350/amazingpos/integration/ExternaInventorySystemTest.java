package se.kth.iv1350.amazingpos.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.amazingpos.model.dto.ItemDTO;
import se.kth.iv1350.amazingpos.model.exception.DatabaseFailureException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ExternaInventorySystem class to verify inventory management functionality.
 */
public class ExternaInventorySystemTest {

    private ExternaInventorySystem inventorySystem;

    @BeforeEach
    public void setUp() {
        inventorySystem = new ExternaInventorySystem();
    }

    /**
     * Test retrieving an item with a valid ID.
     */
    @Test
    public void testGetItemValidId() throws DatabaseFailureException {
        ItemDTO item = inventorySystem.getItem("0003");
        assertNotNull(item, "Item should not be null for a valid ID");
        assertEquals("0003", item.getId(), "Item ID should match");
        assertEquals("kiwi", item.getDescription(), "Item description should match");
        assertEquals(2.0, item.getPrice(), 0.001, "Item price should match");
        assertEquals(0.15, item.getVatRate(), 0.001, "Item VAT rate should match");
    }

    /**
     * Test retrieving an item with an invalid ID.
     */
    @Test
    public void testGetItemInvalidId() throws DatabaseFailureException {
        ItemDTO item = inventorySystem.getItem("invalidID");
        assertNull(item, "Item should be null for an invalid ID");
    }

    /**
     * Test that getItem throws DatabaseFailureException for ID "999".
     */
    @Test
    public void testGetItemDatabaseFailureException() {
        assertThrows(DatabaseFailureException.class, () -> {
            inventorySystem.getItem("999");
        }, "DatabaseFailureException should be thrown for item ID '999'");
    }

    /**
     * Test updating inventory successfully.
     */
    @Test
    public void testUpdateInventorySuccess() {
        boolean result = inventorySystem.updateInventory("0005", 5);
        assertTrue(result, "Inventory should update successfully with sufficient stock");
        int remainingStock = inventorySystem.getAvailableStock("0005");
        assertEquals(15, remainingStock, "Remaining stock should be updated correctly");
    }

    /**
     * Test updating inventory with insufficient stock.
     */
    @Test
    public void testUpdateInventoryInsufficientStock() {
        boolean result = inventorySystem.updateInventory("0005", 25); // Exceeds available stock
        assertFalse(result, "Inventory update should fail with insufficient stock");
        int remainingStock = inventorySystem.getAvailableStock("0005");
        assertEquals(20, remainingStock, "Stock should remain unchanged after failed update");
    }

    /**
     * Test getAvailableStock with a valid item ID.
     */
    @Test
    public void testGetAvailableStockValidId() {
        int stock = inventorySystem.getAvailableStock("0003");
        assertEquals(10, stock, "Available stock should match initial quantity");
    }

    /**
     * Test getAvailableStock with an invalid item ID.
     */
    @Test
    public void testGetAvailableStockInvalidId() {
        int stock = inventorySystem.getAvailableStock("invalidID");
        assertEquals(0, stock, "Available stock should be zero for invalid item ID");
    }
}
