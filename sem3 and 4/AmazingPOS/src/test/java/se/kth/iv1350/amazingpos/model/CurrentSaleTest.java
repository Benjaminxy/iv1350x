package se.kth.iv1350.amazingpos.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.amazingpos.integration.ExternaInventorySystem;
import se.kth.iv1350.amazingpos.model.exception.DatabaseFailureException;
import se.kth.iv1350.amazingpos.model.exception.OutOfStockException;
import se.kth.iv1350.amazingpos.model.exception.ProductNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentSaleTest {
    private CurrentSale currentSale;
    private ExternaInventorySystem inventory;
    private ByteArrayOutputStream logContent;

    @BeforeEach
    public void setUp() {
        inventory = new ExternaInventorySystem();
        currentSale = new CurrentSale(inventory);

        // Redirect System.err to logContent for testing
        logContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(logContent));
    }

    @Test
    public void testAddItemThrowsProductNotFoundException() {
        String nonExistentItemId = "nonExistentItem";
        assertThrows(ProductNotFoundException.class, () -> {
            currentSale.addItem(nonExistentItemId, 1);
        });

        // Check log content
        String logOutput = logContent.toString();
        System.out.println(logOutput); // Debugging line to print log content
        assertTrue(logOutput.contains("ProductNotFoundException"), "Log should contain ProductNotFoundException");
        assertTrue(logOutput.contains("nonExistentItem"), "Log should contain the non-existent item ID");
    }

    @Test
    public void testAddItemThrowsOutOfStockException() {
        String itemId = "0005"; // Assuming this item exists with limited stock
        assertThrows(OutOfStockException.class, () -> {
            currentSale.addItem(itemId, 100); // Exceeding stock
        });

        // Check log content
        String logOutput = logContent.toString();
        System.out.println(logOutput); // Debugging line to print log content
        assertTrue(logOutput.contains("OutOfStockException"), "Log should contain OutOfStockException");
    }

    @Test
    public void testAddItemThrowsDatabaseFailureException() {
        String problematicItemId = "999"; // Hardcoded to trigger DatabaseFailureException
        assertThrows(DatabaseFailureException.class, () -> {
            currentSale.addItem(problematicItemId, 1);
        });

        // Check log content
        String logOutput = logContent.toString();
        System.out.println(logOutput); // Debugging line to print log content
        assertTrue(logOutput.contains("DatabaseFailureException"), "Log should contain DatabaseFailureException");
    }
}
