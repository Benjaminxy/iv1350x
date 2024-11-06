package se.kth.iv1350.amazingpos.model.dto;

/**
 * Data Transfer Object for item details.
 */
public class ItemDTO {
    private String id;
    private String description;
    private double price;
    private double vatRate;

    public ItemDTO(String id, String description, double price, double vatRate) {
        this.id = id.trim();  // Ensure IDs are trimmed of whitespace
        this.description = description;
        this.price = price;
        this.vatRate = vatRate;
    }

    /**
     * Returns the item's ID.
     * @return The item's unique identifier.
     */
    public String getId() {
        return id;
    }


    /**
     * Returns the item's description.
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the item's price.
     * @return The price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the VAT rate for the item.
     * @return The VAT rate applicable to the item.
     */
    public double getVatRate() {
        return vatRate;
    }
    
    
}
