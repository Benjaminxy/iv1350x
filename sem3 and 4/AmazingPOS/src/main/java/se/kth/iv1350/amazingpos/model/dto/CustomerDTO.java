package se.kth.iv1350.amazingpos.model.dto;

/**
 * Data Transfer Object for customer details.
 */
public class CustomerDTO {
    private final String customerId;
    private final boolean isMember;

    /**
     * Constructs a CustomerDTO with specified customer ID and membership status.
     * @param customerId The unique identifier for the customer.
     * @param isMember Indicates whether the customer is a member.
     */
    public CustomerDTO(String customerId, boolean isMember) {
        this.customerId = customerId;
        this.isMember = isMember;
    }

    /**
     * Returns the customer's ID.
     * @return The unique identifier of the customer.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Checks if the customer is a member.
     * @return True if the customer is a member, otherwise false.
     */
    public boolean isMember() {
        return isMember;
    }
}
