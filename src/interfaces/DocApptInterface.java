package interfaces;

import java.util.Scanner;

import user.Doctor;

/**
 * This interface defines methods related to managing doctor appointments.
 * It includes functionality for viewing appointments, handling appointment requests, 
 * and recording appointment outcomes.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide
 * functionality for a doctor to manage appointments and their outcomes.
 * </p>
 */
public interface DocApptInterface {

    /**
     * Displays the appointments for the specified doctor.
     * 
     * @param doctor The {@link Doctor} whose appointments are to be viewed.
     */
    public void viewAppointments(Doctor doctor);

    /**
     * Handles appointment requests for the specified doctor.
     * 
     * @param sc A {@link Scanner} object used for reading user input.
     * @param doctor The {@link Doctor} whose appointment requests are being handled.
     */
    public void AppointmentRequestHandler(Scanner sc, Doctor doctor);

    /**
     * Records the outcome of an appointment for the specified doctor.
     * 
     * @param sc A {@link Scanner} object used for reading user input.
     * @param doctor The {@link Doctor} whose appointment outcome is being recorded.
     */
    public void recordAppointmentOutcome(Scanner sc, Doctor doctor);
}
