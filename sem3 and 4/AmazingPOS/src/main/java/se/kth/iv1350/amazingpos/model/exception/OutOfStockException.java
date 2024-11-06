package se.kth.iv1350.amazingpos.model.exception;

/**
 * Thrown when a product is out of stock.
 */
public class OutOfStockException extends Exception {
    /**
     * Creates an exception for when a product is out of stock.
     * 
     * @param productID The ID of the product.
     * @param requested The requested quantity.
     * @param available The available quantity.
     */
    public OutOfStockException(String productID, int requested, int available) {
        super("Out of stock for item ID " + productID + ". Requested: " + requested + ", Available: " + available);
    }
}
