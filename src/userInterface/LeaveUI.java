package userInterface;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import leave.*;
import user.Doctor;
import interfaces.LeaveInterface;

/**
 * Handles the user interface for leave management.
 * <p>
 * This class implements {@link LeaveInterface} and provides functionality for doctors to
 * apply for leave, view leave requests, and manage leave-related operations such as 
 * approving, rejecting, updating, and removing leave requests.
 */
public class LeaveUI implements LeaveInterface {

    /**
     * Allows a doctor to apply for leave.
     * <p>
     * The method prompts the doctor to enter a future date and a reason for the leave.
     * The leave request is then submitted to the {@link LeaveManager}.
     *
     * @param sc      A {@link Scanner} object for user input.
     * @param doctor  The {@link Doctor} applying for the leave.
     */
    public void applyLeave(Scanner sc, Doctor doctor){
        String dateStr;
        LocalDate date;
        
        //enter Date
        while(true){
            try {
                System.out.print("\nEnter the date of leave (yyyy-mm-dd): ");
                dateStr = sc.nextLine().trim();
                date = LocalDate.parse(dateStr); // Try parsing the date
                    
                // Check if the date is in the future
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Invalid date. Please enter a future date.");
                    continue; // Prompt user to enter the date again
                }
                break; // Exit loop if date is valid
            } catch (Exception e) {
                //catches incorrect date format
                System.out.println("Invalid date format. Please enter the date in the format yyyy-mm-dd.");
            }
        }
        System.out.print("Enter your reason for applying leave: ");
        String reason = sc.nextLine();

        //pass new leave object to LeaveManager to add
        Leave leave = new Leave("", doctor, date, LeaveStatus.PENDING, reason);
        LeaveManager.addLeave(leave); 
    } 

    /**
     * Displays all leave requests in the system.
     * <p>
     * This method delegates the operation to the {@link LeaveManager}.
     */
    public void displayAllLeaveRequests(){
        LeaveManager.displayLeaves();
    }

    /**
     * Displays all pending leave requests for a specific doctor.
     *
     * @param sc      A {@link Scanner} object for user input.
     * @param doctor  The {@link Doctor} whose pending leave requests are displayed.
     */
    public void displayPendingLeave(Scanner sc, Doctor doctor){
        LeaveManager.displayPendingLeave(doctor);
    }

    /**
     * Displays the outcome of leave requests (approved or rejected) for a specific doctor.
     *
     * @param sc      A {@link Scanner} object for user input.
     * @param doctor  The {@link Doctor} whose leave outcomes are displayed.
     */
    public void displayLeaveOutcome(Scanner sc, Doctor doctor){
        LeaveManager.displayLeaveOutcome(doctor);
    }

    /**
     * Manages leave requests by allowing approval or rejection.
     * <p>
     * This method displays all leave requests, prompts the user to select a specific
     * request by ID, and provides options to approve or reject the request.
     *
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageLeaveRequests(Scanner sc){
        displayAllLeaveRequests();
        System.out.println("\nEnter the leave request ID you would like to manage");
        System.out.print("Leave ID: ");
        String leaveID = sc.nextLine().trim();
        int choice = -1;
        
        do {
            System.out.println("\nLeave Request: " + leaveID);
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Approve");
            System.out.println("2. Reject");
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

            switch (choice) {
                case 1:
                    approveLeaveRequests(leaveID);
                    break;
                case 2:
                    rejectLeaveRequests(leaveID);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=3);
    }

    /**
     * Approves a specific leave request.
     *
     * @param leaveID The ID of the leave request to approve.
     */
    public void approveLeaveRequests(String leaveID){
        LeaveManager.approveLeave(leaveID);
    }

    /**
     * Rejects a specific leave request.
     *
     * @param leaveID The ID of the leave request to reject.
     */
    public void rejectLeaveRequests(String leaveID){
        LeaveManager.rejectLeave(leaveID);
    }

    /**
     * Updates a pending leave request for a doctor.
     * <p>
     * The method allows the doctor to select a leave request and update its date and reason.
     *
     * @param sc      A {@link Scanner} object for user input.
     * @param doctor  The {@link Doctor} updating the leave request.
     */
    public void updateLeave(Scanner sc, Doctor doctor){
        displayPendingLeave(sc, doctor);
        //find the leave they doctor wants to update
        System.out.println("\nEnter the ID of the Leave Request you would like to update");
        System.out.print("Leave ID: ");
        String leaveID = sc.nextLine().trim();
        LeaveManager.updateLeave(sc, leaveID, doctor);
    }

    /**
     * Removes a pending leave request for a doctor.
     *
     * @param sc      A {@link Scanner} object for user input.
     * @param doctor  The {@link Doctor} removing the leave request.
     */
    public void removeLeave(Scanner sc, Doctor doctor){
        displayPendingLeave(sc, doctor);
        System.out.println("\nEnter Leave Request ID to be removed");
        System.out.print("Leave ID: ");
        String leaveID = sc.nextLine();

        LeaveManager.removeLeave(leaveID, doctor);
    }

}
