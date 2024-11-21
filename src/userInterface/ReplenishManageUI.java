package userInterface;

import java.util.Scanner;

import interfaces.InvenManageInterface;
import interfaces.ReplenishManageInterface;
import inventory.InventoryManager;
import inventory.Medicine;
import inventory.ReplenishManager;
import user.Pharmacist;

/**
 * This class implements {@link ReplenishManageInterface} to provide a user interface for managing inventory replenish requests. 
 * <p>
 * This class includes methods for viewing, submitting, and managing replenish requests
 * for inventory items. It is designed for use by administrators and pharmacists.
 */
public class ReplenishManageUI implements ReplenishManageInterface{

    /**
     * Default constructor for ReplenishManageUI.
     */
    public ReplenishManageUI(){}

    /** Displays the list of all replenish requests. */
    public void viewReplenishRequest(){
        ReplenishManager.displayReplenishList();
    }

    /**
     * Submits a replenish request for a specific medication.
     * <p>
     * This method is intended for pharmacists to request restocking of items.
     * It validates the item and ensures a valid quantity is entered before generating the request.
     * 
     * @param sc           A {@link Scanner} object for user input.
     * @param medicineName The {@link Medicine} for which the replenish request is to be submitted.
     */
    public void addReplenish(Scanner sc, Medicine medicineName){
        String itemName = medicineName.name();
        int quantity = 0;
        boolean valid = false;

        //check if item exists in Inventory
        if(InventoryManager.findItemByName(itemName) == null){
            System.out.println("The item " + itemName + " does not exist in the inventory.");
            return; 
        }

        //ask user for valid quantity to replenish
        while (!valid) {
            System.out.println("Please enter the amount you want to replenish for " + itemName + ": ");
            System.out.print("Choice: ");
            if (sc.hasNextInt()) { //validate input is an integer
                quantity = sc.nextInt();
                if (quantity > 0) {
                    valid = true; //exit loop if a valid quantity is entered
                } else {
                    System.out.println("Invalid input. Quantity must be greater than 0.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); //clear invalid input
            }
        }
        //generate replenish request
        ReplenishManager.generateReplenish(itemName, quantity);
        System.out.println("Replenish request submitted for " + itemName + " with quantity " + quantity + ".");
    }

    /**
     * Manages a specific replenish request.
     * <p>
     * This method is intended for administrators to review, approve, or reject replenish requests.
     * It prompts the user for the request ID and processes the request accordingly.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageReplenish(Scanner sc){
        viewReplenishRequest(); //display list of requests
        System.out.println("Enter the replenish request ID");
        System.out.print("Request ID: ");
        ReplenishManager.manageReplenish(sc.nextLine() ,sc); //pass reqID
    }

    /**
     * Submits a replenish request by first displaying the current replenish requests 
     * and then allowing the pharmacist to choose a medication.
     * 
     * @param sc The {@link Scanner} object to capture user input.
     * @param pharmacist The pharmacist submitting the replenish request.
     * @param replenishManageInterface The interface for managing replenish requests.
     * @param invenManageInterface The interface for managing inventory.
     */
    public void submitReplenish(Scanner sc, Pharmacist pharmacist, 
    ReplenishManageInterface replenishManageInterface, InvenManageInterface invenManageInterface){
        replenishManageInterface.viewReplenishRequest();
        invenManageInterface.chooseMedicine(sc, pharmacist.getRole());
    }
}
