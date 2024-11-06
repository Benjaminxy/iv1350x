package se.kth.iv1350.amazingpos.model.exception;

/**
 * Thrown when a product is not found in the inventory.
 */
public class ProductNotFoundException extends Exception {
    /**
     * Creates an exception for when a product is not found.
     * 
     * @param productID The ID of the product.
     */
    public ProductNotFoundException(String productID) {
        super("The item " + productID + " is not found.");
    }
}
