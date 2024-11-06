package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages customer information and registrations.
 */
public class CustomerManagementSystem {
    private List<CustomerDTO> registeredCustomers = new ArrayList<>();

    public CustomerManagementSystem() {
        initializeCustomers();
    }

    private void initializeCustomers() {
        registerCustomer(new CustomerDTO("12345", true));
    }

    /**
     * Registers a new customer into the system.
     * @param customerDTO The customer to register.
     */
    public void registerCustomer(CustomerDTO customerDTO) {
        registeredCustomers.add(customerDTO);
    }

    /**
     * Retrieves a customer by their ID.
     * @param customerId The ID of the customer to find.
     * @return The customer if found, null otherwise.
     */
    public CustomerDTO getCustomer(String customerId) {
        for (CustomerDTO customer : registeredCustomers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}
