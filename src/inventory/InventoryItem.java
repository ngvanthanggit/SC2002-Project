package inventory;

public class InventoryItem {
    private Medicine itemName;
    private int quantity;
    private int minimumQuantity;

    //might not need default constructor
    public InventoryItem() {
        this.itemName = null;
        this.quantity = 0;
        this.minimumQuantity = 0;
    }

    public InventoryItem(Medicine itemName, int quantity, int minimumQuantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    public void setItemName(Medicine itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Medicine getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinimumQualtity() {
        return minimumQuantity;
    }

    //convert into a CSV readable and writeable format
    public String toCSVRow() {
        return String.format("%s,%d,%d", itemName, quantity, minimumQuantity);
    }

    public String getItemInfo() {
        return String.format("[Medicine = %s, Initial Stock = %d, Low Stock Alert = %d]",
        itemName, quantity, minimumQuantity);
    }
}

