package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.model.dto.CustomerDTO;

/**
 * Represents a customer.
 */
public class Customer {
    private final CustomerDTO customerDTO;

    /**
     * Creates a new instance of Customer.
     * 
     * @param customerDTO The customer data.
     */
    public Customer(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
