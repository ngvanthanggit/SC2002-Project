package inventory;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import utility.*;

/**
 * This class manages the inventory of medicines.
 * <p>
 * This class provides methods for loading inventory data, displaying inventory
 * items,
 * updating stock levels, and managing low-stock alerts.
 */
public class InventoryManager {
    // List of inventory items
    private static List<InventoryItem> inventory = new ArrayList<>();
    private static String originalPath = "../../Data//Original/Medicine_List.csv";
    private static String updatedPath = "../../Data//Updated/Medicine_List(Updated).csv";

    /**
     * Loads inventory data from a CSV file.
     * <p>
     * If it's the first run, the data is loaded from the original file and the
     * updated file is cleared.
     * Otherwise, data is loaded from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first
     *                   time;
     *                   {@code false} otherwise.
     */
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

        List<InventoryItem> inventoryMapList = CSVread.readItemCSV(filePath, inventoryColumnMapping);

        // add the data from CSV into inventoryList
        for (InventoryItem item : inventoryMapList) {
            if (item instanceof InventoryItem) {
                inventory.add(item);
            }
        }

        if (inventory.isEmpty()) {
            System.out.println("No items were loaded.");
        } else {
            System.out.println("Inventory successfully loaded: " + inventory.size());
        }
    }

    /**
     * Finds an inventory item by its name.
     * 
     * @param itemName The name of the item as a {@code String}.
     * @return The {@link InventoryItem} object if found; {@code null} otherwise.
     */
    public static InventoryItem findItemByName(String itemName) {
        try {
            Medicine medicine = Medicine.valueOf(itemName);
            for (InventoryItem item : inventory) {
                if (item.getItemName() == medicine) {
                    return item;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid medicine name: " + itemName);
        }
        return null;
    }

    /**
     * Returns the list of all inventory items.
     * 
     * @return A {@code List} of {@link InventoryItem} objects.
     */
    public static List<InventoryItem> getInventory() {
        return inventory;
    }

    /** Displays all inventory items. */
    public static void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("The inventory is currently empty.");
        } else {
            System.out.println("\nThe Medication in the CSV file are: ");
            for (InventoryItem inventoryItem : inventory) {
                System.out.println(inventoryItem.getItemInfo());
            }
        }
    }

    /** Displays items with stock levels below the low-stock threshold. */
    public static void displayLowItem() {
        boolean lowStockFound = false;
        System.out.println("\nChecking for low-stock items...");
        for (InventoryItem inventoryItem : inventory) {
            if (inventoryItem.getQuantity() <= inventoryItem.getMinimumQuantity()) {
                System.out.println(inventoryItem.getItemInfo());
                lowStockFound = true;
            }
        }
        if (!lowStockFound) {
            System.out.println("All items are sufficiently stocked.");
        }
    }

    /** Duplicates the current inventory list to the updated CSV file. */
    public static void duplicateInventory() {
        CSVwrite.writeCSVList(updatedPath, inventory);
    }

    // Methods for modifying item quantities

    /**
     * Adds stock to a specified inventory item.
     * 
     * @param sc           A {@link Scanner} object for user input.
     * @param medicineName The {@link Medicine} to add stock for.
     */
    public static void addItemStock(Scanner sc, Medicine medicineName) {
        try {
            // pass the name as a string
            InventoryItem itemName = findItemByName(medicineName.name()); // returns itemName
            if (itemName != null) {
                int currentQuantity = itemName.getQuantity();
                System.out.println("\nThe current quantity of " + itemName.getItemName() + " is: " + currentQuantity);
                System.out.println("How much " + itemName.getItemName() + " are you adding?");
                System.out.print("Amount (ex. 5): ");
                int amount = sc.nextInt();
                sc.nextLine();// consume

                if (amount <= 0) {
                    System.out.println("Invalid amount. Must be greater than 0.");
                    return;
                }

                itemName.setQuantity(amount + currentQuantity); // set new quantity
                System.out.println("The new quantity of " + itemName.getItemName() + " is: " + itemName.getQuantity());
                duplicateInventory(); // update CSV file
            } else {
                System.out.println(medicineName + " does not exist in the inventory.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding stock: " + e.getMessage());
        }
    }

    /**
     * Deducts stock from a specified inventory item.
     * 
     * @param sc           A {@link Scanner} object for user input.
     * @param medicineName The {@link Medicine} to deduct stock from.
     */
    public static void deductItemStock(InventoryItem itemName, int quanity) {
        itemName.setQuantity(quanity);
        System.out.println("The new quantity of " + itemName.getItemName() + " is: " + itemName.getQuantity());
        duplicateInventory(); // update CSV file
    }

    /**
     * Updates the stock level of a specified inventory item.
     * 
     * @param sc           A {@link Scanner} object for user input.
     * @param medicineName The {@link Medicine} to update.
     */
    public static void updateItemStock(Scanner sc, Medicine medicineName) {
        try {
            // pass the name as a string
            String itemName = medicineName.name();
            InventoryItem item = findItemByName(itemName); // returns itemName

            if (item != null) {
                int currentQuantity = item.getQuantity();
                System.out.println("\nThe current quantity of " + item.getItemName() + " is: " + currentQuantity);
                System.out.println("Enter the new quanity for " + item.getItemName());
                System.out.print("Amount (ex. 5): ");
                int amount = sc.nextInt();
                sc.nextLine();// consume

                updateItemStockHelper(itemName, amount);
            } else {
                System.out.println(medicineName + " does not exist in the inventory.");
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding stock: " + e.getMessage());
        }
    }

    /**
     * Helper method to update the stock level of an inventory item
     * programmatically.
     * 
     * @param itemName The name of the item as a {@code String}.
     * @param quantity The new quantity to set for the item.
     */
    public static void updateItemStockHelper(String itemName, int quantity) {
        // Check for valid parameters
        if (itemName == null || itemName.isEmpty()) {
            System.out.println("Item name cannot be null or empty.");
            return;
        }
        if (quantity < 0) {
            System.out.println("Invalid quantity value.");
            return;
        }

        InventoryItem item = findItemByName(itemName); // returns itemName
        if (item != null) {
            item.setQuantity(quantity);
            System.out.println("The new quantity of " + item.getItemName() + " is: " + item.getQuantity());
            duplicateInventory(); // update CSV file
        } else {
            System.out.println("The item does not exist in the inventory.");
        }
    }

    /**
     * Updates the low-stock alert threshold for a specified inventory item.
     * 
     * @param sc           A {@link Scanner} object for user input.
     * @param medicineName The {@link Medicine} to update.
     */
    public static void updateItemLowLevelAlert(Scanner sc, Medicine medicineName) {
        try {
            // pass the name as a string
            InventoryItem itemName = findItemByName(medicineName.name()); // returns itemName
            if (itemName != null) {
                int currentLevel = itemName.getMinimumQuantity();
                System.out
                        .println("\nThe current low-level-alert of " + itemName.getItemName() + " is: " + currentLevel);
                System.out.println("Enter the new low-level-alert for" + itemName.getItemName());
                System.out.print("Amount (ex. 5): ");
                int amount = sc.nextInt();
                sc.nextLine();// consume

                itemName.setMinimumQuantity(amount);
                System.out.println("The new low-level-alert for " + itemName.getItemName() + " is: "
                        + itemName.getMinimumQuantity());
                duplicateInventory();
            } else {
                System.out.println("The item does not exist in the inventory.");
            }
        } catch (Exception e) {
            System.out.println(medicineName + " does not exist in the inventory.");
        }
    }
}
