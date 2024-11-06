package se.kth.iv1350.amazingpos.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CustomerManagementSystem class to verify customer management functionality.
 */
public class CustomerManagementSystemTest {

    private CustomerManagementSystem customerSystem;

    @BeforeEach
    public void setUp() {
        customerSystem = new CustomerManagementSystem();
    }

    /**
     * Test retrieving a customer with a valid ID.
     */
    @Test
    public void testGetCustomerValidId() {
        CustomerDTO customer = customerSystem.getCustomer("12345");
        assertNotNull(customer, "Customer should not be null for a valid ID");
        assertEquals("12345", customer.getCustomerId(), "Customer ID should match");
        assertTrue(customer.isMember(), "Customer should be a member");
    }

    /**
     * Test retrieving a customer with an invalid ID.
     */
    @Test
    public void testGetCustomerInvalidId() {
        CustomerDTO customer = customerSystem.getCustomer("invalidID");
        assertNull(customer, "Customer should be null for an invalid ID");
    }

    /**
     * Test registering a new customer and retrieving it.
     */
    @Test
    public void testRegisterAndRetrieveCustomer() {
        CustomerDTO newCustomer = new CustomerDTO("67890", false);
        customerSystem.registerCustomer(newCustomer);

        CustomerDTO retrievedCustomer = customerSystem.getCustomer("67890");
        assertNotNull(retrievedCustomer, "Newly registered customer should be retrievable");
        assertEquals("67890", retrievedCustomer.getCustomerId(), "Customer ID should match");
        assertFalse(retrievedCustomer.isMember(), "Customer should not be a member");
    }

    /**
     * Test registering a customer with an existing ID.
     */
    @Test
    public void testRegisterCustomerWithExistingId() {
        CustomerDTO duplicateCustomer = new CustomerDTO("12345", true);
        customerSystem.registerCustomer(duplicateCustomer);

        // Retrieve customers with ID "12345" and check for duplicates
        CustomerDTO retrievedCustomer = customerSystem.getCustomer("12345");
        assertNotNull(retrievedCustomer, "Customer should be retrievable");
        assertEquals("12345", retrievedCustomer.getCustomerId(), "Customer ID should match");

     
    }
}
