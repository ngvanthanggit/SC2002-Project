package inventory;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import io.*;

public class InventoryManager {
    //List of inventory items
    private static List<InventoryItem> inventory = new ArrayList<>();
    private static String originalPath = "../Data//Original/Medicine_List.csv";
    private static String updatedPath = "../Data//Updated/Medicine_List(Updated).csv";

    public static void loadInventory(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }
        inventory.clear();

        Map<String, Integer> inventoryColumnMapping = new HashMap<>();
        inventoryColumnMapping.put("Medicine Name", 0);
        inventoryColumnMapping.put("Initial Stock", 1);
        inventoryColumnMapping.put("Low Stock Level Alert", 2);

        inventory = CSVread.readitemCSV(filePath, inventoryColumnMapping);

        if (inventory.isEmpty()) {
            System.out.println("No items were loaded.");
        } else {
            System.out.println("Inventory successfully loaded: " + inventory.size());
        }

    }

    public InventoryItem getItem(String itemName) {
        for (InventoryItem item : inventory) {
            if(item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
    //return all items in a list
    public List<InventoryItem> getInventory() {
        return inventory;
    }
    //Add new item to inventory
    public void addItem(String itemName, int quantity, int minimumQuantity) {
        //Check for valid parameters
        if (itemName == null || itemName.isEmpty()) {
            System.out.println("Item name cannot be null or empty.");
            return;
        }
        if (quantity < 0) {
            System.out.println("Invalid quantity value.");
            return;
        }
        //Add item to list only if there are no duplicates
        if (getItem(itemName) == null) {
            InventoryItem newItem = new InventoryItem(itemName, quantity, minimumQuantity);
            inventory.add(newItem);
            CSVwrite.writeCSV(updatedPath, newItem);
            System.out.println(quantity + " units of " + itemName + " have been added to the inventory.");
        } else {
            System.out.println("That item already exists in the inventory.");
        }

    }
    //Delete item from inventory
    public void removeItem(String itemName) {
        //Check for valid parameters
        if (itemName == null || itemName.isEmpty()) {
            System.out.println("Item name cannot be null or empty.");
            return;
        }
        //Remove only if item exist in inventory
        InventoryItem item = getItem(itemName);
        if (item != null) {
            inventory.remove(item);
            //need to remove in csv file also
            System.out.println(item + " has been removed from the inventory");
        } else {
            System.out.println("The item does not exist in the inventory.");
        }

    }
    //Update item quantity
    public void updateItem(String itemName, int quantity) {
        //Check for valid parameters
        if (itemName == null || itemName.isEmpty()) {
            System.out.println("Item name cannot be null or empty.");
            return;
        }
        if (quantity < 0) {
            System.out.println("Invalid quantity value.");
            return;
        }
        InventoryItem item = getItem(itemName);
        if(item != null) {
            item.setQuantity(quantity);
            CSVwrite.writeCSV(updatedPath, item);
            System.out.println("There are now " + quantity +" "+ itemName + " in the inventory.");
        }
        else {
            System.out.println("The item does not exist in the inventory.");
        }
    }







    
}
