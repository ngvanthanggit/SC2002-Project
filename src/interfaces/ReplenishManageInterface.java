package interfaces;

import java.util.Scanner;

import inventory.Medicine;
import user.Pharmacist;

/**
 * This interface defines methods related to managing replenish requests for inventory.
 * It includes functionality for viewing replenish requests, adding and managing replenishes,
 * and submitting replenish requests for approval or processing.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for managing the replenishment of inventory, including interacting with pharmacists
 * and inventory management systems.
 * </p>
 */
public interface ReplenishManageInterface {

    /** Displays the current replenish requests that need to be processed. */
    public void viewReplenishRequest();

    /**
     * Adds a new replenish request for a specific medicine.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @param medicineName The {@link Medicine} for which the replenish request is being added.
     */
    public void addReplenish(Scanner sc, Medicine medicineName);

    /**
     * Manages the replenish requests, allowing for review and processing of the requests.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     */
    public void manageReplenish(Scanner sc);

    /**
     * Submits a replenish request for approval or further processing.
     * 
     * @param sc                     A {@link Scanner} object for reading user input.
     * @param pharmacist             The {@link Pharmacist} submitting the replenish request.
     * @param replenishManageInterface The {@link ReplenishManageInterface} that provides replenish management functions.
     * @param invenManageInterface   The {@link InvenManageInterface} used for inventory management.
     */
    public void submitReplenish(Scanner sc, Pharmacist pharmacist, 
    ReplenishManageInterface replenishManageInterface, InvenManageInterface invenManageInterface);
}
