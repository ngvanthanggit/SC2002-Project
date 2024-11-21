package schedule;

import java.time.*;
import java.util.*;

/**
 * The Schedule class represents the schedule of a doctor for a specific date.
 * It contains the doctor's ID, the date of the schedule, and a list of available time slots.
 * This class provides methods for adding, removing, and displaying time slots, as well as converting
 * the schedule to a CSV-compatible format.
 */
public class Schedule {

    /** The ID of the doctor associated with this schedule */
    String doctorID;

    /** The date of the schedule */
    LocalDate date;

    /** The list of available time slots for the doctor on the given date */
    List<LocalTime> timeSlots;

    /**
     * Default constructor that initializes the schedule with an empty doctor ID, the current date, and an empty list of time slots.
     */
    public Schedule() {
        this.doctorID = "";
        this.date = LocalDate.now();
        this.timeSlots = new ArrayList<>();
    }

    /**
     * Constructor that initializes the schedule with the provided doctor ID, date, and time slots.
     * 
     * @param doctorID The ID of the doctor for whom the schedule is created
     * @param date The date of the schedule
     * @param timeSlots A list of available time slots for the doctor on the given date
     */

    public Schedule(String doctorID, LocalDate date, List<LocalTime> timeSlots) {
        this.doctorID = doctorID;
        this.date = date;
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    // getter and setter methods

    /**
     * Gets the ID of the doctor associated with this schedule.
     * 
     * @return the doctor ID
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the ID of the doctor for this schedule.
     * 
     * @param doctorID The doctor ID to set
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the date of the schedule.
     * 
     * @return The date of the schedule
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date for this schedule.
     * 
     * @param date The date to set for the schedule
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the list of time slots for this schedule.
     * 
     * @return The list of available time slots
     */
    public List<LocalTime> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Sets the list of time slots for this schedule.
     * 
     * @param timeSlots The list of time slots to set
     */
    public void setTimeSlots(List<LocalTime> timeSlots) {
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    /**
     * Returns a string representation of the schedule details, including the doctor's ID,
     * the date of the schedule, and the available time slots.
     * 
     * @return The schedule details in a readable format
     */
    public String getScheduleDetails() {
        return "- Doctor ID: " + doctorID + '\n' +
                "  Date: " + date + '\n' +
                "  Time slots: " + timeSlots +
                "\n";
    }

    /**
     * Converts the schedule to a CSV-compatible row format.
     * The time slots are joined with semicolons, and the date is formatted as a string.
     * 
     * @return The schedule in CSV row format
     */
    public String toCSVRow() {
        String timeSlotsString = String.join(";",
                timeSlots.stream()
                        .map(LocalTime::toString)
                        .toArray(String[]::new));
        return String.format("%s,%s,\"%s\"", doctorID, date, timeSlotsString);
    }

    /**
     * Adds a time slot to the schedule. The time slots are automatically sorted after adding a new time slot.
     * 
     * @param timeSlot The time slot to add
     */
    public void addTimeSlot(LocalTime timeSlot) {
        this.timeSlots.add(timeSlot);
        // sort the time slots
        Collections.sort(this.timeSlots);
    }

    /**
     * Removes a time slot from the schedule.
     * 
     * @param timeSlot The time slot to remove
     */
    public void removeTimeSlot(LocalTime timeSlot) {
        this.timeSlots.remove(timeSlot);
    }
}
