package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import interfaces.AdminMenu;
import interfaces.AdminApptInterface;
import interfaces.CommonMenu;
import interfaces.InvenManageInterface;
import interfaces.LeaveInterface;
import interfaces.ReplenishManageInterface;
import interfaces.StaffManageInterface;
import main.HMSApp;
import user.Administrator;
import user.Role;
import utility.PasswordResetManager;
import utility.PasswordResetRequest;

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
    private final LeaveInterface leaveInterface;

    /**
     * Constructs an {@code AdminUI} with the specified administrator.
     * @param administrator The {@link Administrator} object associated with this UI.
     */
    public AdminUI(Administrator administrator, StaffManageInterface staffManageInterface, AdminApptInterface adminApptInterface,
                    InvenManageInterface invenManageInterface, ReplenishManageInterface replenishManageInterface, LeaveInterface leaveInterface){
        this.administrator = administrator;
        this.staffManageInterface = staffManageInterface;
        this.adminApptInterface = adminApptInterface;
        this.invenManageInterface = invenManageInterface;
        this.replenishManageInterface = replenishManageInterface;
        this.leaveInterface = leaveInterface;
    }

    /**
     * Logs out Administrator 
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout(){
        System.out.println("Administrator Logging Out.");
        HMSApp.resetSessionColor(); // Reset terminal color
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
            System.out.println("5. Manage Leave Requests");
            System.out.println("6. Manage Password Reset Requests");
            System.out.println("7. Logout");
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
                    manageLeaveRequests(sc);
                    break;
                case 6:
                    managePasswordRequests(sc);
                    break;
                case 7:
                    logout(); 
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while(choice!=7);
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
                    break;
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

    /**
     * Manages leave requests through an interactive menu.
     * <p>
     * This method displays a menu for managing leave requests, including viewing the list 
     * of leave requests and approving or rejecting specific requests. 
     * It handles user input and ensures valid choices are entered.
     * 
     * @param sc A {@link Scanner} object for user input.
     * 
     * <p>
     * Menu Options:
     * <ul>
     *   <li><b>1:</b> View the list of all leave requests.</li>
     *   <li><b>2:</b> Approve or reject specific leave requests.</li>
     *   <li><b>3:</b> Exit the menu and return to the previous interface.</li>
     * </ul>
     * 
     * <p>
     * Error Handling:
     * <ul>
     *   <li>Catches invalid input types (non-integer) and prompts the user to try again.</li>
     *   <li>Displays a message for invalid menu choices.</li>
     * </ul>
     */
    public void manageLeaveRequests(Scanner sc){
        int choice = -1;
        do {
            System.out.println("\nLeave Request Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Leave Request List");
            System.out.println("2. Approve/Reject Leave Request");
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
                    leaveInterface.displayAllLeaveRequests();
                    break;
                case 2:
                    leaveInterface.manageLeaveRequests(sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while(choice!=3);
    }

    /**
     * Manages password reset requests through an interactive menu.
     * <p>
     * This method allows the administrator to view pending password reset requests, approve a specific request, 
     * and reset the associated user's password to a default value.
     * 
     * @param sc A {@link Scanner} object for user input.
     * 
     * <p>
     * Menu Options:
     * <ul>
     *   <li>Displays all pending password reset requests.</li>
     *   <li>Allows the administrator to approve a request by entering its index.</li>
     *   <li>Provides an option to exit the menu by entering {@code 0}.</li>
     * </ul>
     * 
     * <p>
     * Process:
     * <ol>
     *   <li>Displays the list of password reset requests.</li>
     *   <li>Prompts the administrator to enter the index of a request to approve.</li>
     *   <li>If a valid index is entered, the request is approved, and the user's password is reset to the default value.</li>
     *   <li>If {@code 0} is entered, the menu exits.</li>
     * </ol>
     * 
     * <p>
     * Error Handling:
     * <ul>
     *   <li>Catches invalid input types (non-integer) and prompts the user to try again.</li>
     *   <li>Handles invalid indices gracefully.</li>
     * </ul>
     */
    public void managePasswordRequests(Scanner sc) {
        int choice = -1;
    
        do {
            System.out.println("\n|--- Manage Password Reset Requests ---|");
            PasswordResetManager.displayRequests();
    
            System.out.println("\nEnter the index of the request to approve (or 0 to exit): ");
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }
    
            if (choice == 0) {
                System.out.println("Exiting request management.");
                break;
            }
    
            PasswordResetRequest approvedRequest = PasswordResetManager.approveRequest(choice - 1);
            if (approvedRequest != null) {
                System.out.println("Approved password reset request for: " + approvedRequest);
                PasswordResetManager.resetPasswordToDefault(approvedRequest.getUserId());
            }
        } while (choice != 0);
    }
    
}
