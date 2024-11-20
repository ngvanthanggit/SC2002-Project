package userInterface;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import interfaces.ScheduleInterface;
import schedule.Schedule;
import schedule.ScheduleManager;
import user.Doctor;

public class ScheduleUI implements ScheduleInterface{

    //constructor
    public ScheduleUI(){}
    
    public void viewSchedule(Doctor doctor) {
        System.out.println("\n|------- Doctor " +  doctor.getName() + "'s Schedule -------|");
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(doctor.getHospitalID());
        if (schedules.isEmpty()) {
            System.out.println("No schedules found.");
            return;
        }
        System.out.println("Your Schedules are:");
        for (Schedule schedule : schedules) {
            System.out.println(schedule.getScheduleDetails());
        }
        //schedules are set to be 1hr interval time slots
        System.out.println(
                "*The time slots are in 1h intervals. E.g. \"09:00\" means availability is from 9:00 to 10:00 etc.");

    }

    public void setSchedule(Scanner sc, Doctor doctor) {
        System.out.println("Set Schedule");
        String dateStr;

        LocalDate date;
        while (true) {
            //handles inconsistent date formatting
            try {
                System.out.print("\nEnter the date (yyyy-mm-dd): ");
                dateStr = sc.nextLine();
                date = LocalDate.parse(dateStr); // Try parsing the date
                
                // Check if the date is in the future
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Invalid date. Please enter a future date.");
                    continue; // Prompt user to enter the date again
                }
                break; // Exit loop if date is valid
            } catch (Exception e) {
                //catches incorrect date format
                System.out.println("Invalid date format. Please enter the date in the format yyyy-mm-dd.");
            }
        }

        while (true) {
            //catch any unexpected errors
            try {
                System.out.println("\nSet schedule for " + dateStr);
                System.out.println("Enter the time slots comma seperated (HH:mm, HH:mm 1-hour intervals)");
                System.out.print("Enter your available timeslot: ");

                //get user input in a line, then seperate time by commas (,)
                String timeSlots = sc.nextLine();
                List<String> newTimeSlotsStr = Arrays.asList(timeSlots.split(", "));
                List<LocalTime> newTimeSlots = new ArrayList<>();
                boolean isValidInput = true;

                //parse and validate each time slot
                for (String timeSlotStr : newTimeSlotsStr) {
                    try {
                        //parse time
                        LocalTime timeSlot = LocalTime.parse(timeSlotStr);

                        // Check for scheduling conflicts
                        if (!ScheduleManager.checkValidSchedule(date, timeSlot, doctor)) {
                            System.out.println("Time conflict or invalid interval: " + timeSlot + ". Please choose a different time.");
                            isValidInput = false;
                            break;
                        }

                        //add valid timeslots to the list
                        newTimeSlots.add(timeSlot);
                    } catch (Exception e) {
                        System.out.println("Invalid time format: " + timeSlotStr + ". Please use the format HH:mm (e.g., 09:00).");
                        isValidInput = false;
                        break; //exit loop because invalid time
                    }
                }

                // Ensure all time slots in the list are 1 hour apart
                if (isValidInput && !areTimeSlotsOneHourApart(newTimeSlots)) {
                    //System.out.println("Invalid time slots: Time slots must be 1 hour apart (e.g., 10:00, 11:00).");
                    continue;
                }

                //ensure at least one valid time slot was entered
                if (newTimeSlots.isEmpty()) {
                    //System.out.println("No valid time slots were entered. Please try again.");
                    continue;
                }

                // add the valid time slots to the schedule
                addSchedule(date, newTimeSlots, doctor);
                break; // exit the loop after successfully adding the schedule
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    public boolean areTimeSlotsOneHourApart(List<LocalTime> timeSlots) {
        // Sort the time slots to ensure order
        Collections.sort(timeSlots);
    
        // Check each pair of consecutive time slots
        for (int i = 1; i < timeSlots.size(); i++) {
            if (Duration.between(timeSlots.get(i - 1), timeSlots.get(i)).toMinutes() != 60) {
                System.out.println("Time slots are not 1 hour apart");
                return false; 
            }
        }
    
        return true; // All time slots are 1 hour apart
    }

    public void addSchedule(LocalDate date, LocalTime time, Doctor doctor) {
        if (ScheduleManager.checkValidTime(date, time)) {
            List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(doctor.getHospitalID());
            boolean isDateInSchedule = false;
            for (Schedule schedule : schedules) {
                if (schedule.getDate().isEqual(date)) {
                    isDateInSchedule = true;
                    schedule.addTimeSlot(time);
                    break;
                }
            }
            if (!isDateInSchedule) {
                Schedule newSchedule = new Schedule(doctor.getHospitalID(), date, List.of(time));
                ScheduleManager.addSchedule(newSchedule);
            }
            System.out.println("Time slot added successfully.");

            // save to file
            ScheduleManager.duplicateSchedule();
        }
    }

    public void addSchedule(LocalDate date, List<LocalTime> timeSlots, Doctor doctor) {
        // storing the valid time slots
        List<LocalTime> validTimeSlots = new ArrayList<>();

        // checking if the date is already in the schedule
        boolean isDateInSchedule = false;
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(doctor.getHospitalID());
        for (Schedule schedule : schedules) {
            if (schedule.getDate().isEqual(date)) {
                isDateInSchedule = true;
                // if the date is already in the schedule, add the new time slots to the
                // existing time slots
                for (LocalTime timeSlot : timeSlots) {
                    boolean isTimeSlotValid = true;

                    List<LocalTime> existingTimeSlots = schedule.getTimeSlots();
                    for (LocalTime existingTimeSlot : existingTimeSlots) {
                        if (timeSlot.equals(existingTimeSlot)) {
                            isTimeSlotValid = false; // existed
                            break;
                        }
                    }
                    if (isTimeSlotValid) {
                        schedule.addTimeSlot(timeSlot); // add the new time slot
                        validTimeSlots.add(timeSlot);
                    }
                }
            }
        }
        // if the date is not in the schedule, create a new schedule
        if (!isDateInSchedule) {
            Schedule newSchedule = new Schedule(doctor.getHospitalID(), date, timeSlots);
            ScheduleManager.addSchedule(newSchedule);
            validTimeSlots.addAll(timeSlots);
        }

        System.out.println("\nTime slots added successfully (excluding invalid time slots) are:");
        System.out.println(validTimeSlots);

        // save to file
        ScheduleManager.duplicateSchedule();
    }

    public void removeSchedule(LocalDate date, LocalTime time, Doctor doctor) {
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(doctor.getHospitalID());
        for (Schedule schedule : schedules) {
            if (schedule.getDate().isEqual(date)) {
                schedule.removeTimeSlot(time);
                System.out.println("Time slot removed successfully.");
                // save to file
                ScheduleManager.duplicateSchedule();
                return;
            }
        }
        System.out.println("No time slot found for this date.");
    }
}