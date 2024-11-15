package user;

import java.util.List;
import java.util.Scanner;

import inventory.*;
import menus.PharmacistMenu;

public class Pharmacist extends User implements PharmacistMenu {

    public Pharmacist(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public void logout() {
        System.out.println("Pharmacist Logging Out.");
        return;
    }

    // Pharmist Menus, implemented from User class interface
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("1. View Appointment Outcome");
            System.out.println("2. View Inventory");
            System.out.println("3. View Replenish Request List");
            System.out.println("4. Submit Replenish Request");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            handleSelection(choice, sc);
        } while (choice != 5);
    }

    public void handleSelection(int option, Scanner scanner) {
        switch (option) {
            case 1:
                System.out.println("1. View Appointment Outcome");
                break;
            case 2:
                viewInventory();
                break;
            case 3:
                ReplenishManager.displayReplenishList();
                break;
            case 4:
                submitReplenish(scanner);
                break;
            case 5:
                logout(); // logout
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    // show all items & low level warning
    public void viewInventory() {
        // List<InventoryItem> inventory = InventoryManager.getInventory();
        InventoryManager.displayInventory();
        InventoryManager.displayLowItem();
    }

    public void submitReplenish(Scanner scanner) {
        String itemName = null;
        int quantity = 0;
        boolean valid = false;
        InventoryManager.displayLowItem();

        while (!valid) {
            System.out.println("Please enter the item you want to replenish");
            System.out.print("Choice: ");
            itemName = scanner.next().trim(); // only can read a word
            if (InventoryManager.getItem(itemName) == null) {
                System.out.println("Invalid item, please enter the correct item name.");
            } else {
                valid = true;
            }
        }
        valid = false;
        while (!valid) {
            System.out.println("Please enter the amount you want to replenish");
            System.out.print("Choice: ");
            if (scanner.hasNextInt()) { // check for int input
                quantity = scanner.nextInt();
                if (quantity > 0) {
                    valid = true;
                } else {
                    System.out.println("Invalid input, please enter a number.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
            }

        }
        ReplenishManager.generateReplenish(itemName, quantity);
    }
}
