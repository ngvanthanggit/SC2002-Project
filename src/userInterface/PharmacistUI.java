package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import interfaces.ApptOutcomeInterface;
import interfaces.CommonMenu;
import interfaces.InvenManageInterface;
import interfaces.PharmacistMenu;
import interfaces.ReplenishManageInterface;
import inventory.ReplenishManager;
import user.Pharmacist;

public class PharmacistUI implements PharmacistMenu {
    private final Pharmacist pharmacist;
    private final InvenManageInterface invenManageInterface;
    private final ReplenishManageInterface replenishManageInterface;
    private final ApptOutcomeInterface apptOutcomeInterface;

    public PharmacistUI(Pharmacist pharmacist, InvenManageInterface invenManageInterface, 
                        ReplenishManageInterface replenishManageInterface, ApptOutcomeInterface apptOutcomeInterface){
        this.pharmacist = pharmacist;
        this.invenManageInterface = invenManageInterface;
        this.replenishManageInterface = replenishManageInterface;
        this.apptOutcomeInterface = apptOutcomeInterface;
    }

    /**
     * Logs out Pharmacist 
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout(){
        System.out.println("Pharmacist Logging Out.");
        return;
    }

    /**
     * Display main menu for Patients
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu() {
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nPharmacist Menus are listed Below");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Appointment Outcome");
            System.out.println("2. Update Appointment Prescription Status");
            System.out.println("3. View Inventory");
            System.out.println("4. View Replenish Request List");
            System.out.println("5. Submit Replenish Request");
            System.out.println("6. Logout");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }
            
            switch (choice) {
                case 1:
                    apptOutcomeInterface.viewAllApptOutcomes();
                    break;
                case 2:
                    apptOutcomeInterface.updateApptMedication(sc);
                    break;
                case 3:
                    viewInventory(sc);
                    break;
                case 4:
                    ReplenishManager.displayReplenishList();
                    break;
                case 5:
                    replenishManageInterface.submitReplenish(sc, pharmacist, replenishManageInterface, invenManageInterface);
                    break;
                case 6:
                    logout(); // logout
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice != 6);
    }

    public void viewAppointmentOutcomes(Scanner sc){

    }

    //show all items & low level warning
    public void viewInventory(Scanner sc) {
        int choice=-1;
        do{
            System.out.println("\n|---- Medication Inventory Menu ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Medication Inventory");
            System.out.println("2. View Medication Inventory");
            System.out.println("3. View Low Stock Inventory");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch (choice) {
                case 1:
                    invenManageInterface.viewInventory();
                    break;
                case 2:
                    invenManageInterface.viewMedicationInven();
                    break;
                case 3:
                    invenManageInterface.viewLowStockInventory();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }while(choice!=4);
    }
}
