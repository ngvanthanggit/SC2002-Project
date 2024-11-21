package interfaces;

import java.util.Scanner;
import user.Doctor;
/**
 * This interface extends {@link CommonMenu} to provide additional functionalities
 * specific to Doctors. It defines methods for managing medical records, schedules, and appointments.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide the actual
 * functionality for interacting with a doctor's medical records, schedule, and appointments.
 * </p>
 */
public interface DoctorMenu extends CommonMenu{

    /**
     * Displays the menu related to managing a doctor's medical records.
     * 
     * @param sc  A {@link Scanner} object for user input.
     * @param doctor A {@link Doctor} object representing the doctor whose medical records are managed
     */
    public void medicalRecordMenu(Scanner sc, Doctor doctor );

    /**
     * Displays the menu for managing a doctor's schedule.
     * 
     * @param sc  A {@link Scanner} object for user input.
     */
    public void scheduleMenu(Scanner sc);

    /**
     * Displays the menu for managing appointments related to the doctor.
     * 
     * @param sc  A {@link Scanner} object for user input.
     */
    public void appointmentMenu(Scanner sc);
}
