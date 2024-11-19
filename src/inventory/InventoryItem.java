package inventory;

/**
 * This class represents an item in the inventory system.
 * <p>
 * Each inventory item includes a name, quantity, and a minimum quantity for low-stock alerts.
 */
public class InventoryItem {
    private Medicine itemName;
    private int quantity;
    private int minimumQuantity;

    /** Default constructor for creating an empty inventory item. */
    public InventoryItem() {
        this.itemName = null;
        this.quantity = 0;
        this.minimumQuantity = 0;
    }

    /**
     * Constructs an inventory item with the specified name, quantity, and low-stock threshold.
     * 
     * @param itemName        The name of the medicine as a {@link Medicine} enum.
     * @param quantity        The current quantity of the item.
     * @param minimumQuantity The minimum quantity for low-stock alerts.
     */
    public InventoryItem(Medicine itemName, int quantity, int minimumQuantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    //Getter Methods()
    /**
     * Returns the itemName of the InventoryItem
     * @return the itemName
     */
    public Medicine getItemName() {
        return itemName;
    }

    /**
     * Returns the quantity of the InventoryItem
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the minimumQuantity of the InventoryItem
     * @return the minimumQuantity
     */
    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    //Setter Methods

    /**
     * Updates itemName of the InventoryItem
     * @param itemName
     */
    public void setItemName(Medicine itemName) {
        this.itemName = itemName;
    }

    /**
     * Updates quantity of the InventoryItem
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Updates minimumQuantity of the InventoryItem
     * @param minimumQuantity
     */
    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    /**
     * Converts the inventory item into a CSV row format.
     * @return A {@code String} representing the inventory item in CSV format.
     */
    public String toCSVRow() {
        return String.format("%s,%d,%d", itemName, quantity, minimumQuantity);
    }

    /**
     * Returns a formatted string containing information about the inventory item.
     * @return A {@code String} with the item's details.
     */
    public String getItemInfo() {
        return String.format("[Medicine = %s, Initial Stock = %d, Low Stock Alert = %d]",
        itemName, quantity, minimumQuantity);
    }
}

