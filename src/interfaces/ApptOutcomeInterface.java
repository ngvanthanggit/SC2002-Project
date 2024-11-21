package interfaces;

import java.util.Scanner;
import medicalrecord.MedicalRecord;

/**
 * This interface defines methods related to managing appointment outcomes.
 * It includes functionality for viewing all appointment outcomes, selecting medical records,
 * and updating medication information for appointments.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for handling appointment outcomes and related medical record actions.
 * </p>
 */
public interface ApptOutcomeInterface {

    /** Displays all appointment outcomes for review. */
    public void viewAllApptOutcomes();

    /**
     * Assists in selecting a medical record for a specific appointment.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     * @return The {@link MedicalRecord} selected by the user.
     */
    public MedicalRecord selectMRhelper(Scanner sc);

    /**
     * Updates the medication details for a specific appointment outcome.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     */
    public void updateApptMedication(Scanner sc);
}
