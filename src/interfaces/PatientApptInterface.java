package interfaces;

import java.util.Scanner;
import user.Patient;

/**
 * This interface defines methods related to managing a patient's appointments.
 * It includes functionality for viewing available appointment slots, scheduling or rescheduling 
 * appointments, canceling appointments, and viewing past appointment outcomes.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for a patient to interact with their appointment details.
 * </p>
 */
public interface PatientApptInterface {

    /**
     * Displays the available appointment slots for the patient to choose from.
     * 
     * @param sc A {@link Scanner} object for reading user input.
     */
    public void viewAvailableApptSlots(Scanner sc);

    /**
     * Allows the patient to schedule an appointment.
     * 
     * @param sc     A {@link Scanner} object for reading user input.
     * @param patient The {@link Patient} scheduling the appointment.
     */
    public void scheduleAppointment(Scanner sc, Patient patient);

    /**
     * Allows the patient to reschedule a previously scheduled appointment.
     * 
     * @param sc     A {@link Scanner} object for reading user input.
     * @param patient The {@link Patient} rescheduling the appointment.
     */
    public void rescheduleAppointment(Scanner sc, Patient patient);

    /**
     * Allows the patient to cancel a scheduled appointment.
     * 
     * @param sc     A {@link Scanner} object for reading user input.
     * @param patient The {@link Patient} canceling the appointment.
     */
    public void cancelAppointment(Scanner sc, Patient patient);

    /**
     * Displays the appointments that have already been scheduled for the patient.
     * 
     * @param patient The {@link Patient} whose scheduled appointments are to be viewed.
     */
    public void viewScheduledAppointments(Patient patient);

    /**
     * Displays the outcomes of past appointments for the patient.
     * 
     * @param patient The {@link Patient} whose past appointment outcomes are to be viewed.
     */
    public void viewPastApptOutcomes(Patient patient);
}
