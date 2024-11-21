package schedule;

import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import utility.*;
import user.Doctor;

/**
 * The ScheduleManager class manages all the operations related to doctor schedules.
 * It handles loading schedules from a CSV file, checking for conflicts, adding, removing, 
 * and displaying schedules, as well as validating the schedules and their time slots.
 */
public class ScheduleManager {

    /** The list of schedules for all doctors */
    private static List<Schedule> schedules = new ArrayList<>();

    /** The file path for the original schedule list */
    private static String originalPath = "Data//Original/Schedule_List.csv";

    /** The file path for the updated schedule list */
    private static String updatedPath = "Data//Updated/Schedule_List(Updated).csv";

    /** Removes any schedules that are in the past. */
    public static void removeInvalidSchedules() {
        // remove schedules that are in the past
        schedules.removeIf(schedule -> schedule.getDate().isBefore(java.time.LocalDate.now()));
        schedules.removeIf(schedule -> schedule.getDate().isEqual(java.time.LocalDate.now())
                && schedule.getTimeSlots().stream().allMatch(time -> time.isBefore(java.time.LocalTime.now())));
    }

    /**
     * Loads the schedules from the CSV file (original or updated based on the first run).
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
    public static void loadSchedules(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }
        schedules.clear();

        Map<String, Integer> schedulesColumnMapping = new HashMap<>();
        schedulesColumnMapping.put("Doctor ID", 0);
        schedulesColumnMapping.put("Date", 1);
        schedulesColumnMapping.put("Time Slots", 2);

        List<Schedule> schedulesMapList = CSVread.readScheduleCSV(filePath, schedulesColumnMapping);

        for (Schedule schedule : schedulesMapList) {
            if (schedule instanceof Schedule) {
                schedules.add(schedule);
            }
        }

        if (schedules.isEmpty()) {
            System.out.println("No schedules were loaded.");
        } else {
            System.out.println("Schedules successfully loaded: " + schedules.size());
        }
        removeInvalidSchedules();
    }

    /** Duplicates the current list of schedules to the updated CSV file. */
    public static void duplicateSchedule() {
        removeInvalidSchedules();
        CSVwrite.writeCSVList(updatedPath, schedules);
    }

    /**
     * Displays the current list of schedules.
     * Removes any invalid schedules before displaying.
     */
    public static void displaySchedules() {
        removeInvalidSchedules();
        if (schedules.isEmpty()) {
            System.out.println("\nThe schedules is currently empty.");
        } else {
            System.out.println("\nThe Schedules in the CSV file are:");
            for (Schedule schedule : schedules) {
                System.out.println(schedule.getScheduleDetails());
            }
        }
    }

    /**
     * Retrieves the list of schedules for a specific doctor based on their ID.
     * 
     * @param doctorID The ID of the doctor whose schedules are to be retrieved
     * @return A list of schedules for the specified doctor
     */
    public static List<Schedule> getScheduleOfDoctor(String doctorID) {
        removeInvalidSchedules();
        List<Schedule> doctorSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equalsIgnoreCase(doctorID)) {
                doctorSchedules.add(schedule);
            }
        }
        return doctorSchedules;
    }

    /**
     * Checks if a specific doctor is available at a given date and time.
     * 
     * @param doctorID The ID of the doctor
     * @param date The date for the check
     * @param time The time for the check
     * @return {@code true} if the doctor is available, {@code false} otherwise
     */
    public static boolean isDoctorAvailable(String doctorID, LocalDate date, LocalTime time) {
        removeInvalidSchedules();
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equalsIgnoreCase(doctorID) && schedule.getDate().isEqual(date)
                    && schedule.getTimeSlots().contains(time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all the schedules in the system.
     * 
     * @return A list of all the schedules
     */
    public static List<Schedule> getSchedules() {
        removeInvalidSchedules();
        return schedules;
    }

    /**
     * Adds a new schedule to the system and duplicates the schedule list in the updated CSV file.
     * 
     * @param schedule The new schedule to add
     */
    public static void addSchedule(Schedule schedule) {
        removeInvalidSchedules();
        schedules.add(schedule);
        duplicateSchedule();
    }

    /**
     * Checks if there is a duplicate schedule (same doctor, same date, and same time).
     * 
     * @param date The date of the proposed schedule
     * @param time The time of the proposed schedule
     * @param doctor The doctor for whom the schedule is being checked
     * @return {@code true} if a conflict exists, {@code false} otherwise
     */
    public static boolean checkDuplicateSchedule(LocalDate date, LocalTime time, Doctor doctor) {
        // loop through the entire schedules list
        for (Schedule schedule : schedules) {
            // if a schedule for the Doctor with date passed is found
            if (schedule.getDoctorID().equals(doctor.getHospitalID()) && schedule.getDate().equals(date)) {
                // check the timeSlots
                if (schedule.getTimeSlots().contains(time)) {
                    return true; // Conflict found
                }
            }
        }
        return false; // no conflicts
    }

    /**
     * Checks if there is a conflict with the time slots (ensures there is a 1-hour interval between slots).
     * 
     * @param date The date of the proposed schedule
     * @param time The time of the proposed schedule
     * @param doctor The doctor for whom the schedule is being checked
     * @return {@code true} if the proposed schedule conflicts with an existing one, {@code false} otherwise
     */
    public static boolean checkInterval(LocalDate date, LocalTime time, Doctor doctor) {
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctor.getHospitalID()) &&
                    schedule.getDate().equals(date)) {
                // check whether incoming timeSlot has a 1hr difference between before and after
                // time
                // example before < incoming < after, 1hr interval
                for (LocalTime existingTime : schedule.getTimeSlots()) {
                    if (Math.abs(Duration.between(existingTime, time).toMinutes()) < 60) {
                        return true; // Conflict found
                    }
                }
            }
        }
        return false; // No conflict
    }

    /**
     * Validates a proposed schedule by checking for any duplicate schedules and interval conflicts.
     * 
     * @param date The date of the proposed schedule
     * @param time The time of the proposed schedule
     * @param doctor The doctor for whom the schedule is being checked
     * @return {@code true} if the schedule is valid (no conflicts), {@code false} otherwise
     */
    public static boolean checkValidSchedule(LocalDate date, LocalTime time, Doctor doctor) {
        boolean isDuplicate = checkDuplicateSchedule(date, time, doctor);
        boolean isIntervalConflict = checkInterval(date, time, doctor);

        if (isDuplicate || isIntervalConflict) {
            return false; // Conflict found
        }
        return true; // No conflicts
    }

    /**
     * Validates if the proposed time is in the future (i.e., the time is not in the past).
     * 
     * @param date The date of the proposed schedule
     * @param time The time of the proposed schedule
     * @return {@code true} if the time is in the future, {@code false} otherwise
     */
    public static boolean checkValidTime(LocalDate date, LocalTime time) {
        if ((date.isEqual(LocalDate.now()) && time.isAfter(LocalTime.now())) ||
                date.isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }

    public static void removeDaySchedule(LocalDate date, Doctor doctor){
        schedules.removeIf(schedule -> 
        schedule.getDoctorID().equalsIgnoreCase(doctor.getHospitalID()) && 
        schedule.getDate().isEqual(date));

        System.out.println("All availability for " + date + " has been removed for Doctor " + doctor.getHospitalID() + ".");
        // Save the updated schedule list to the file
        ScheduleManager.duplicateSchedule();
    }
}