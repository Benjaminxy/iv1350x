package se.kth.iv1350.amazingpos.model;

/**
 * Represents an item that has been purchased in a transaction.
 */
public class PurchasedItem {
    private String itemId;
    private String description;
    private int quantity;
    private double price;
    private double vatRate;

    /**
     * Constructs a PurchasedItem with detailed item information.
     * @param itemId The unique identifier for the item.
     * @param description The description of the item.
     * @param quantity The quantity of the item purchased.
     * @param price The price per unit of the item.
     * @param vatRate The VAT rate applicable to the item.
     */
    public PurchasedItem(String itemId, String description, int quantity, double price, double vatRate) {
        this.itemId = itemId;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.vatRate = vatRate;
    }

    /**
     * Returns the item's ID.
     * @return The unique identifier of the item.
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Returns the description of the item.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the quantity of the item purchased.
     * @return The purchased quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item purchased.
     * @param quantity The new quantity to be set.
     */
    public void setQuantity(int quantity) {
    this.quantity = quantity;
    }

    /**
     * Returns the price per unit of the item.
     * @return The unit price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the VAT rate applicable to the item.
     * @return The VAT rate.
     */
    public double getVatRate() {
        return vatRate;
    }
}
