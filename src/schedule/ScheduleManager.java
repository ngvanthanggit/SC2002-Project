package schedule;

import java.util.*;
import io.*;

public class ScheduleManager {
    private static List<Schedule> schedules = new ArrayList<>();
    private static String originalPath = "../Data//Original/Schedule_List.csv";
    private static String updatedPath = "../Data//Updated/Schedule_List(Updated).csv";

    public static void removeInvalidSchedules() {
        // remove schedules that are in the past
        schedules.removeIf(schedule -> schedule.getDate().isBefore(java.time.LocalDate.now()));
        schedules.removeIf(schedule -> schedule.getDate().isEqual(java.time.LocalDate.now())
                && schedule.getTimeSlots().stream().allMatch(time -> time.isBefore(java.time.LocalTime.now())));
    }

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

    public static void duplicateSchedule() {
        removeInvalidSchedules();
        CSVwrite.writeCSVList(updatedPath, schedules);
    }

    public static void displaySchedules() {
        removeInvalidSchedules();
        if (schedules.isEmpty()) {
            System.out.println("The schedules is currently empty.");
        } else {
            System.out.println("The Schedules in the CSV file are:");
            for (Schedule schedule : schedules) {
                System.out.println(schedule.getScheduleDetails());
            }
        }
    }

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

    public static List<Schedule> getSchedules() {
        removeInvalidSchedules();
        return schedules;
    }

    public static void addSchedule(Schedule schedule) {
        removeInvalidSchedules();
        schedules.add(schedule);
        duplicateSchedule();
    }
}

// Doctor will have to add, remove their schedule
// in the add() or remove() we will have to loop through
// the list of schedules to find the doctorID and the date matched