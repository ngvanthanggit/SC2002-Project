package interfaces;

import java.util.Scanner;
import user.Role;
import inventory.Medicine;

/**
 * This interface defines methods related to managing the inventory of medications.
 * It includes functionality for viewing the inventory, handling stock levels, and managing 
 * individual medicines, including adding, deducting, and updating stock.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for managing the medication inventory, including stock management and alerts.
 * </p>
 */
public interface InvenManageInterface {

    /** Displays the full medication inventory. */
    public void viewMedicationInven();

    /** Displays medications that are low in stock. */
    public void viewLowStockInventory();

    /** Displays the entire inventory of medications. */
    public void viewInventory();

    /**
     * Allows the user to choose a medicine based on their role.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param role The {@link Role} of the user selecting the medicine.
     */
    public void chooseMedicine(Scanner sc, Role role);

    /**
     * Manages the details of a specific medicine in the inventory.
     * 
     * @param sc  A {@link Scanner} object for reading user input.
     * @param medicine The {@link Medicine} to be managed.
     */
    public void manageMedicine(Scanner sc, Medicine medicine);

    /**
     * Adds stock to a specific medicine in the inventory.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param medicine The {@link Medicine} to which stock will be added.
     */
    public void addStock(Scanner sc, Medicine medicine);

    /**
     * Deducts stock from a specific medicine in the inventory.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param medicine The {@link Medicine} from which stock will be deducted.
     */
    public void deductStock(Scanner sc, Medicine medicine);

    /**
     * Updates the stock level of a specific medicine in the inventory.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param medicine The {@link Medicine} whose stock will be updated.
     */
    public void updateStock(Scanner sc, Medicine medicine);

    /**
     * Updates the alert threshold for stock levels of a specific medicine.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param medicine The {@link Medicine} for which the alert threshold is to be updated.
     */
    public void updateLevelAlert(Scanner sc, Medicine medicine);
}
