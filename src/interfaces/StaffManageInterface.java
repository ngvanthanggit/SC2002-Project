package interfaces;

import java.util.Scanner;
import user.Role;
import user.User;

/**
 * This interface defines methods related to managing hospital staff.
 * It includes functionality for viewing, filtering, adding, updating, and removing staff based on their roles.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for managing the details of hospital staff, including user roles and permissions.
 * </p>
 */
public interface StaffManageInterface {

    /**
     * Displays a list of staff members based on their role.
     * 
     * @param sc   A {@link Scanner} object for reading user input.
     * @param role The {@link Role} of the staff to view (Doctor, Pharmacist, Administrator, etc.).
     */
    public void viewStaff(Scanner sc, Role role);

    /**
     * Filters the staff list based on a specified role and displays the filtered list.
     * 
     * @param sc       A {@link Scanner} object for reading user input.
     * @param role     The {@link Role} to filter the staff list by.
     * @param userList The list of {@link User} objects representing the staff to be filtered.
     */
    public void filterStaff(Scanner sc, Role role, List<User> userList);

    /**
     * Allows the user to add a new staff member based on the specified role.
     * 
     * @param sc   A {@link Scanner} object for reading user input.
     * @param role The {@link Role} of the staff member being added.
     */
    public void addStaff(Scanner sc, Role role);

    /**
     * Allows the user to update details of an existing staff member based on their role.
     * 
     * @param sc   A {@link Scanner} object for reading user input.
     * @param role The {@link Role} of the staff member whose details are to be updated.
     */
    public void updateStaff(Scanner sc, Role role);

    /**
     * Allows the user to remove a staff member based on their role.
     * 
     * @param sc   A {@link Scanner} object for reading user input.
     * @param role The {@link Role} of the staff member to be removed.
     */
    public void removeStaff(Scanner sc, Role role);
}
