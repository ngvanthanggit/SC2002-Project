package userInterface;

import java.util.Scanner;
import inventory.InventoryManager;
import inventory.Medicine;
import user.Role;

/**
 * This class provides a user interface for managing the inventory of medications.
 * <p>
 * It includes methods for viewing inventory, managing stock levels, and handling low-level alerts.
 * This class is designed to be used by administrators and pharmacists.
 */
public class InvenManageUI {

    /** Displays the full list of medications and their stock levels. */
    public static void viewMedicationInven(){
        InventoryManager.displayInventory();
    }

    /** Displays a list of medications with stock levels below the low-level alert threshold. */
    public static void viewLowStockInventory(){
        InventoryManager.displayLowItem();
    }

    /** Displays a combination of {@link InvenManageUI#viewMedicationInven} and {@link InvenManageUI#viewLowStockInventory} */
    public static void viewInventory(){
        InventoryManager.displayInventory();
        InventoryManager.displayLowItem();
    }

    /**
     * Allows the user to select a medication for further actions based on their role.
     * <p>
     * Administrators can manage the stock, while pharmacists can submit replenish requests.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the user (Administrator or Pharmacist).
     */
    public static void chooseMedicine(Scanner sc, Role role){
        int choice;
        do {
            int counter = 1;
            System.out.println("\nChoose the Medicine");
            System.out.printf("%s\n", "-".repeat(27));
            for(Medicine medicine : Medicine.values()){
                System.out.println(counter + ". " + medicine);
                counter++;
            }
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            Medicine medicine = null;

            switch(choice) {
                case 1:
                    medicine = Medicine.Paracetamol;
                    break;
                case 2:
                    medicine = Medicine.Ibuprofen;
                    break;
                case 3:
                    medicine = Medicine.Amoxicillin;
                    break;
                case 4:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

            //handle null case
            if(medicine!=null){
                if(role == Role.Administrator){
                    manageMedicine(sc, medicine); //admin method
                } else if (role == Role.Pharmacist) {
                    ReplenishManageUI.submitReplenish(sc, medicine);//pharmacist method
                }
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
        while(choice!=4);
    }

    /**
     * Provides administrators with options to manage a specific medication's stock.
     * 
     * @param sc       A {@link Scanner} object for user input.
     * @param medicine The selected {@link Medicine} to manage.
     */
    public static void manageMedicine(Scanner sc, Medicine medicine){
        int choice;
        do {
            System.out.println("\nManage " + medicine);
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Add " + medicine);
            System.out.println("2. Deduct " + medicine);
            System.out.println("3. Update " + medicine);
            System.out.println("4. Change " + medicine + " Low Level Stock Alert");
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    addStock(sc, medicine);
                    break;
                case 2:
                    deductStock(sc, medicine);
                    break;
                case 3:
                    updateStock(sc, medicine);
                    break;
                case 4:
                    updateLevelAlert(sc, medicine);
                    break;
                case 5:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=5);
    }

    /**
     * Adds user-input stock to the current stock of the specified medication.
     * 
     * @param sc       A {@link Scanner} object for user input.
     * @param medicine The {@link Medicine} to update.
     */
    public static void addStock(Scanner sc, Medicine medicine){
        InventoryManager.addItemStock(sc, medicine);
    }

    /**
     * Deducts user-input stock from the current stock of the specified medication.
     * 
     * @param sc       A {@link Scanner} object for user input.
     * @param medicine The {@link Medicine} to update.
     */
    public static void deductStock(Scanner sc, Medicine medicine){
        InventoryManager.deductItemStock(sc, medicine);
    }

    /**
     * Updates the stock level of the specified medication directly.
     * 
     * @param sc       A {@link Scanner} object for user input.
     * @param medicine The {@link Medicine} to update.
     */
    public static void updateStock(Scanner sc, Medicine medicine){
        InventoryManager.updateItemStock(sc, medicine);
    }

    /**
     * Updates the low-level alert threshold for the specified medication.
     * 
     * @param sc       A {@link Scanner} object for user input.
     * @param medicine The {@link Medicine} to update.
     */
    public static void updateLevelAlert(Scanner sc, Medicine medicine){
        InventoryManager.updateItemLowLevelAlert(sc, medicine);
    }
}
