package user;

import java.util.List;
import java.util.Scanner;

import inventory.*;
import menus.PharmacistMenu;

public class Pharmacist extends User implements PharmacistMenu{

    public Pharmacist(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public void logout(){
        System.out.println("Pharmacist Logging Out.");
        return;
    }

    //Pharmist Menus, implemented from User class interface
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);

        do{
            System.out.println("1. View Appointment Outcome");
            System.out.println("2. View Inventory");
            System.out.println("3. Submit Replenish Request");
            System.out.println("4. Logout");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            handleSelection(choice, sc);
        } while(choice!=4);
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
                logout(); //logout
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    //show all items & low level warning
    public void viewInventory() {
        List<InventoryItem> inventory = InventoryManager.getInventory();
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
