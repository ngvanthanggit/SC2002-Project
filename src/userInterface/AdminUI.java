package userInterface;

import java.util.Scanner;
import menus.AdminMenu;
import menus.CommonMenu;
import user.Administrator;
import user.Role;
import user.User;

/**
 * The class implements the {@link AdminMenu} interface to provide a UI for administrators. 
 * This class allows administrators to perform various administrative tasks, 
 * including managing hospital staff, appointments, inventory, and replenish requests.
 */
public class AdminUI implements AdminMenu{
    /** The administrator object associated with this UI. */
    private Administrator administrator;

    /**
     * Constructs an {@code AdminUI} with the specified administrator.
     * @param administrator The {@link Administrator} object associated with this UI.
     */
    public AdminUI(Administrator administrator){
        this.administrator = administrator;
    }

    /**
     * Logs out Administrator 
     * Overrides the {@link User#logout()} method
     */
    @Override
    public void logout(){
        System.out.println("Administrator Logging Out.");
        return;
    }

    /**
     * Display main menu for Administrators
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n|-------- Admin Menu --------|");
            System.out.printf("%s\n", "-".repeat(30));
            System.out.println("1. Manage Hospital Staff");
            System.out.println("2. Manage Appointment Details");
            System.out.println("3. Manage Medication Inventory");
            System.out.println("4. Manage Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Choice: ");   
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    chooseStaff(sc);
                    break;
                case 2:
                    manageAppointments(sc);
                    break;
                case 3:
                    manageInventory(sc);
                    break;
                case 4:
                    manageReplenishRequest(sc);
                    break;
                case 5:
                    logout(); 
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while(choice!=5);
    }

    /**
     * Prompts the administrator to choose a type of staff to manage.
     * @param sc A {@link Scanner} object for user input.
     */
    public void chooseStaff(Scanner sc){
        int choice;
        do {
            System.out.println("\nChoose the type of Staffs");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctors");
            System.out.println("2. Pharmacists");
            System.out.println("3. Administrators");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            Role role = null;

            switch (choice){
                case 1:
                    role = Role.Doctor;
                    break;
                case 2:
                    role = Role.Pharmacist;
                    break;
                case 3:
                    role = Role.Administrator;
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again."); 
                    continue;
            }

            //handle null case
            if(role!=null){
                manageHospitalStaff(sc, role);
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        } while(choice!=4);
    }

    /**
     * Manages hospital staff of the specified role.
     * Implements the {@link AdminMenu#manageHospitalStaff()} method
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to manage.
     */
    public void manageHospitalStaff(Scanner sc, Role role){
        int choice;
        do {
            System.out.println("\nView & Manage " + role + "s");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View " + role + " List");
            System.out.println("2. Add New " + role);
            System.out.println("3. Update " + role);
            System.out.println("4. Remove " + role);
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    StaffManageUI.viewStaff(sc, role);
                    break;
                case 2:
                    StaffManageUI.addStaff(sc, role);
                    break;
                case 3:
                    StaffManageUI.updateStaff(sc, role);
                    break;
                case 4:
                    StaffManageUI.removeStaff(sc, role);
                    break;
                case 5:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }
        while(choice!=5);
    }

    /**
     * Manages appointments. 
     * Implements the {@link AdminMenu#manageAppointments()} method
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageAppointments(Scanner sc){

    }

    /**
     * Manages the medication inventory.
     * Implements the {@link AdminMenu#manageInventory()} method
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageInventory(Scanner sc){
        int choice;
        do {
            System.out.println("\n|---- Medication Inventory Menu ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Medication Inventory");
            System.out.println("2. Choose Medication Stock to Manage");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    InvenManageUI.viewMedicationInven();
                    break;
                case 2:
                    InvenManageUI.chooseMedicine(sc, administrator.getRole());
                    break;
                case 3:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }
        while(choice!=3);
    }

    /**
     * Manages replenish requests for inventory.
     * Implements the {@link AdminMenu#manageReplenishRequest()} method
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageReplenishRequest(Scanner sc){
        int choice;
        do {
            System.out.println("\nReplenish Request Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Replenish Request List");
            System.out.println("2. Approve/Reject Request");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1:
                    ReplenishManageUI.viewReplenishRequest();
                    break;
                case 2:
                    ReplenishManageUI.manageReplenish(sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while(choice!=3);
    }
}
