package interfaces;
import java.util.Scanner;
import user.Role;

/**
 * The interface extends {@link CommonMenus} to provide additional administrative functionalities 
 * It defines methods specific for Administators like managing various aspects of the system,
 * such as hospital staff, appointments, inventory, and replenish requests.
 */
public interface AdminMenu extends CommonMenu{

    /** 
     * Manages hospital staff based on their role
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to manage (Doctor, Pharmacist, Administator).
     */
    public void manageHospitalStaff(Scanner sc, Role role);

    /**
     * Manages appointments, allowing the administrator to view and modify appointment details.
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageAppointments(Scanner sc);

    /**
     * Manages the medication inventory, enabling the administrator to view and update stock levels.
     * 
     * @param sc A {@link Scanner} object for user input.
     */    
    public void manageInventory(Scanner sc);

    /**
     * Manages replenish requests for inventory, including approval or rejection of requests.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageReplenishRequest(Scanner sc);
}

