package interfaces;

import java.util.Scanner;

/**
 * This interface defines methods for filtering appointments and their outcomes.
 * This interface is intended for use by administrators to manage and query appointments 
 * within the system.
 */
public interface AdminApptInterface {

    /**
     * Filters the list of appointments based on criteria provided by the user.
     * This method is intended to allow administrators to search or filter appointments.
     * 
     * @param sc  A {@link Scanner} object for user input.
     */
    public void filterAppointments(Scanner sc);

    /**
     * Filters the outcomes of appointments based on criteria provided by the user.
     * This method is intended to allow administrators to search or filter appointment outcomes.
     * 
     * @param sc  A {@link Scanner} object for user input.
     */
    public void filterOutcomes(Scanner sc);
}
