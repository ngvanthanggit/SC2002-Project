package interfaces;

import java.util.Scanner;

import user.Doctor;


/**
 * The {@code LeaveInterface} defines methods for managing leave requests
 * within the system. It provides functionality for doctors to apply for leave,
 * view the status of their leave requests, and for administrators or managers
 * to manage and update leave requests.
 * <p>
 * This interface can be implemented by classes responsible for handling leave-related
 * operations in the system.
 */
public interface LeaveInterface {

    /**
     * Allows a doctor to apply for leave.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The {@link Doctor} applying for leave.
     */
    public void applyLeave(Scanner sc, Doctor doctor);

    /**
     * Displays all leave requests in the system.
     * <p>
     * This method is intended for administrators or managers to view
     * all leave requests, regardless of their status.
     */
    public void displayAllLeaveRequests();

    /**
     * Displays pending leave requests for a specific doctor.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The {@link Doctor} whose pending leave requests are to be displayed.
     */
    public void displayPendingLeave(Scanner sc, Doctor doctor);
    
    /**
     * Displays the outcome of leave requests for a specific doctor, rejected or approved.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The {@link Doctor} whose leave outcomes are to be displayed.
     */
    public void displayLeaveOutcome(Scanner sc, Doctor doctor);
    
    /**
     * Manages all leave requests in the system.
     * <p>
     * This method is intended for administrators or managers to approve,
     * reject, or modify leave requests.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageLeaveRequests(Scanner sc);
    
    /**
     * Updates the leave status for a specific doctor.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The {@link Doctor} whose leave status is to be updated.
     */
    public void updateLeave(Scanner sc, Doctor doctor);

    /**
     * removes leave request for a specific doctor.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The {@link Doctor} whose leave request is to be removed.
     */
    public void removeLeave(Scanner sc, Doctor doctor);
}
