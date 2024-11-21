package interfaces;

import java.util.Scanner;
import user.Patient;

/**
 * This interface extends {@link CommonMenu} to provide functionalities
 * specific to managing a patient's personal information. It defines a method 
 * for updating a patient's personal details within the system.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide the
 * actual functionality for handling the patient's personal information update.
 * </p>
 */
public interface PatientMenu extends CommonMenu{

    /**
     * Updates the personal information of a given patient.
     * 
     * @param sc the {@link Scanner} object used to read user input
     * @param patient the {@link Patient} object representing the patient whose personal information is being updated
     */
    public void updatePersonalInfo(Scanner sc, Patient patient);
}
