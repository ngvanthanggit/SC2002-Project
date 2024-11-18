package userInterface;

import java.util.Scanner;

import inventory.ReplenishManager;
import menus.PharmacistMenu;
import user.Pharmacist;

public class PharmacistUI implements PharmacistMenu {
    private Pharmacist pharmacist;

    public PharmacistUI(Pharmacist pharmacist){
        this.pharmacist = pharmacist;
    }

    //implementing interfaces
    @Override
    public void logout(){
        System.out.println("Pharmacist Logging Out.");
        return;
    }

    //Pharmist Main Menu
    @Override
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nPharmacist Menus are listed Below");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Appointment Outcome");
            System.out.println("2. View Inventory");
            System.out.println("3. View Replenish Request List");
            System.out.println("4. Submit Replenish Request");
            System.out.println("5. Logout");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume line
            
            switch (choice) {
                case 1:
                    System.out.println("1. View Appointment Outcome");
                    break;
                case 2:
                    viewInventory(sc);
                    break;
                case 3:
                    ReplenishManager.displayReplenishList();
                    break;
                case 4:
                    submitReplenish(sc);
                    break;
                case 5:
                    logout(); // logout
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice != 5);
    }

    //show all items & low level warning
    public void viewInventory(Scanner sc) {
        int choice;
        do{
            System.out.println("\n|---- Medication Inventory Menu ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Medication Inventory");
            System.out.println("2. View Medication Inventory");
            System.out.println("3. View Low Stock Inventory");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    InvenManageUI.viewInventory();
                    break;
                case 2:
                    InvenManageUI.viewMedicationInven();
                    break;
                case 3:
                    InvenManageUI.viewLowStockInventory();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }while(choice!=4);
    }

    public void submitReplenish(Scanner sc){
        ReplenishManageUI.viewReplenishRequest();
        InvenManageUI.chooseMedicine(sc, pharmacist.getRole());
    }
}
