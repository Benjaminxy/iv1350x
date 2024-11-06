package se.kth.iv1350.amazingpos.model;

import se.kth.iv1350.amazingpos.model.dto.ItemDTO;


/**
 * Represents an item within the system, encapsulating the item details.
 */
public class Item {
    private final ItemDTO itemDTO;

    /**
     * Constructs an Item using an ItemDTO which contains detailed information about the item.
     * @param itemDTO Data Transfer Object containing item details.
     */
    public Item(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
    }
}
