package userInterface;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import leave.*;
import user.Doctor;
import interfaces.LeaveInterface;

public class LeaveUI implements LeaveInterface {

    //pass in the logged in doctor object
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

    public void displayAllLeaveRequests(){
        LeaveManager.displayLeaves();
    }

    public void displayPendingLeave(Scanner sc, Doctor doctor){
        LeaveManager.displayPendingLeave(doctor);
    }

    public void displayLeaveOutcome(Scanner sc, Doctor doctor){
        LeaveManager.displayLeaveOutcome(doctor);
    }

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

    public void approveLeaveRequests(String leaveID){
        LeaveManager.approveLeave(leaveID);
    }

    public void rejectLeaveRequests(String leaveID){
        LeaveManager.rejectLeave(leaveID);
    }

    public void updateLeave(Scanner sc, Doctor doctor){
        displayPendingLeave(sc, doctor);
        //find the leave they doctor wants to update
        System.out.println("\nEnter the ID of the Leave Request you would like to update");
        System.out.print("Leave ID: ");
        String leaveID = sc.nextLine().trim();
        LeaveManager.updateLeave(sc, leaveID, doctor);
    }

}
