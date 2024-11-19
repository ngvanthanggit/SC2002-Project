package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import interfaces.AdminMenu;
import interfaces.AdminApptInterface;
import interfaces.CommonMenu;
import interfaces.InvenManageInterface;
import interfaces.ReplenishManageInterface;
import interfaces.StaffManageInterface;
import user.Administrator;
import user.Role;

/**
 * The class implements the {@link AdminMenu} interface to provide a UI for administrators. 
 * This class allows administrators to perform various administrative tasks, 
 * including managing hospital staff, appointments, inventory, and replenish requests.
 */
public class AdminUI implements AdminMenu{
    /** The administrator object associated with this UI. */
    private final Administrator administrator;
    private final StaffManageInterface staffManageInterface;
    private final AdminApptInterface adminApptInterface; 
    private final InvenManageInterface invenManageInterface;
    private final ReplenishManageInterface replenishManageInterface;

    /**
     * Constructs an {@code AdminUI} with the specified administrator.
     * @param administrator The {@link Administrator} object associated with this UI.
     */
    public AdminUI(Administrator administrator, StaffManageInterface staffManageInterface, AdminApptInterface adminApptInterface,
                    InvenManageInterface invenManageInterface, ReplenishManageInterface replenishManageInterface){
        this.administrator = administrator;
        this.staffManageInterface = staffManageInterface;
        this.adminApptInterface = adminApptInterface;
        this.invenManageInterface = invenManageInterface;
        this.replenishManageInterface = replenishManageInterface;
    }

    /**
     * Logs out Administrator 
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout(){
        System.out.println("Administrator Logging Out.");
        return;
    }

    /**
     * Display main menu for Administrators
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu() {
        int choice = -1;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n|-------- Admin Menu --------|");
            System.out.printf("%s\n", "-".repeat(30));
            System.out.println("1. Manage Hospital Staff & Patients");
            System.out.println("2. Manage Appointment Details");
            System.out.println("3. Manage Medication Inventory");
            System.out.println("4. Manage Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Choice: ");   
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

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
        //gonna include patient
        int choice = -1;
        do {
            System.out.println("\nChoose the type of Staffs or Patients");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctors");
            System.out.println("2. Pharmacists");
            System.out.println("3. Administrators");
            System.out.println("4. Patients");
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

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
                    role = Role.Patient;
                case 5:
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
        } while(choice!=5);
    }

    /**
     * Manages hospital staff of the specified role.
     * Implements the {@link AdminMenu#manageHospitalStaff()} method
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to manage.
     */
    public void manageHospitalStaff(Scanner sc, Role role){
        int choice = -1;
        do {
            System.out.println("\nView & Manage " + role + "s");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View " + role + " List");
            System.out.println("2. Add New " + role);
            System.out.println("3. Update " + role);
            System.out.println("4. Remove " + role);
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch(choice) {
                case 1:
                    staffManageInterface.viewStaff(sc, role);
                    break;
                case 2:
                    staffManageInterface.addStaff(sc, role);
                    break;
                case 3:
                    staffManageInterface.updateStaff(sc, role);
                    break;
                case 4:
                    staffManageInterface.removeStaff(sc, role);
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
        //display all appointmnets, status confirmed cancelled completed
        //display appointment outcome records for completed 

        int choice =-1;
        do {
            System.out.println("\n|---- Appointmnets Menu ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Appointments");
            System.out.println("2. View All Appointment Outcome Records");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch(choice){
                case 1:
                    adminApptInterface.filterAppointments(sc);
                    break;
                case 2:
                    adminApptInterface.filterOutcomes(sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        } while(choice!=3);
    }

    /**
     * Manages the medication inventory.
     * Implements the {@link AdminMenu#manageInventory()} method
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageInventory(Scanner sc){
        int choice = -1;
        do {
            System.out.println("\n|---- Medication Inventory Menu ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Medication Inventory");
            System.out.println("2. Choose Medication Stock to Manage");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch(choice) {
                case 1:
                    invenManageInterface.viewMedicationInven();
                    break;
                case 2:
                    invenManageInterface.chooseMedicine(sc, administrator.getRole());
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
        int choice = -1;
        do {
            System.out.println("\nReplenish Request Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Replenish Request List");
            System.out.println("2. Approve/Reject Request");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch(choice){
                case 1:
                    replenishManageInterface.viewReplenishRequest();
                    break;
                case 2:
                    replenishManageInterface.manageReplenish(sc);
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
