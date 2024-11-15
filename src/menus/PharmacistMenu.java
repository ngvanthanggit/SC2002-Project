package menus;
import inventory.*;
import java.util.*;

public class PharmacistMenu implements CommonMenus{
    private InventoryManager inventoryManager;

    public PharmacistMenu(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;  
    }

    public void displayMenu() {
        System.out.println("1. View Appointment Outcome");
        System.out.println("2. View Inventory");
        System.out.println("3. Submit Replenish Request");
        System.out.println("4. Logout");
    }

    public void handleSelection(int option, Scanner scanner) {
        switch(option) {
            case 1:
                System.out.println("1. View Appointment Outcome");
                break;
            case 2:
                viewInventory();
                break;
            case 3:
                System.out.println("3. Submit Replenish Request");
                break;
            case 4:
                System.out.println("4. Logout");
                break;
        }
    }
    //show all items & low level warning
    public void viewInventory() {
        List<InventoryItem> inventory = inventoryManager.getInventory();
        //Inventory is empty
        if(inventory.isEmpty()) {
            System.out.println("The inventory is currently empty.");
        }
        else {
            for(InventoryItem item : inventory) {
                if(item.getQuantity() < 5) { //need to change low level
                    System.out.print("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity());
                    System.out.println("Warning: " + item.getItemName() + " is low in stock.");
                }
                else {
                    System.out.println("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity() + ", Low Level Alert: " + item.getMinimumQualtity());
                }
            }
            
        }
    }
    
    
}

