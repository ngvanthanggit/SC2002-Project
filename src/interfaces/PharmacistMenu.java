package interfaces;

import java.util.*;

/**
 * This interface extends {@link CommonMenu} to provide functionalities
 * specific to a pharmacist's operations. It defines methods for viewing appointment outcomes 
 * and managing the pharmacist's inventory.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide the
 * actual functionality for a pharmacist to view relevant information, such as appointment 
 * outcomes and the pharmacy's inventory.
 * </p>
 */
public interface PharmacistMenu extends CommonMenu{

    /**
     * Displays the appointment outcomes for the pharmacist to review.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void viewAppointmentOutcomes(Scanner sc);

    /**
     * Displays the pharmacist's inventory for management and review.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void viewInventory(Scanner sc);
}
