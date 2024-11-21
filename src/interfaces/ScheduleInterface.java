package interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import user.Doctor;

/**
 * This interface defines methods related to managing a doctor's schedule.
 * It includes functionality for viewing the schedule, setting a new schedule, 
 * adding or removing specific time slots from the doctor's calendar.
 * <p>
 * The methods in this interface are intended to be implemented by classes that provide 
 * functionality for managing and updating a doctor's schedule, including time management.
 * </p>
 */
public interface ScheduleInterface {

    /**
     * Displays the schedule of a specific doctor.
     * 
     * @param doctor The {@link Doctor} whose schedule is to be viewed.
     */
    public void viewSchedule(Doctor doctor);

    /**
     * Allows the doctor to set their schedule for appointments.
     * 
     * @param sc     A {@link Scanner} object for reading user input.
     * @param doctor The {@link Doctor} who is setting their schedule.
     */
    public void setSchedule(Scanner sc, Doctor doctor);

    /**
     * Adds a new time slot to the doctor's schedule.
     * 
     * @param date   The {@link LocalDate} of the appointment.
     * @param time   The {@link LocalTime} of the appointment.
     * @param doctor The {@link Doctor} whose schedule is being updated.
     */
    public void addSchedule(LocalDate date, LocalTime time, Doctor doctor);

    /**
     * Removes a specific time slot from the doctor's schedule.
     * 
     * @param date   The {@link LocalDate} of the appointment to be removed.
     * @param time   The {@link LocalTime} of the appointment to be removed.
     * @param doctor The {@link Doctor} whose schedule is being updated.
     */
    public void removeSchedule(LocalDate date, LocalTime time, Doctor doctor);
}
