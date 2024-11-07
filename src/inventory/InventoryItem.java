package inventory;

public class InventoryItem {
    private String itemName;
    private int quantity;
    private int minimumQuantity;

    //might not need default constructor
    public InventoryItem() {
        this.itemName = null;
        this.quantity = 0;
        this.minimumQuantity = 0;
    }

    public InventoryItem(String itemName, int quantity, int minimumQuantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMinimumQualtity() {
        return minimumQuantity;
    }

}

