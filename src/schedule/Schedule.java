package schedule;

import java.time.*;
import java.util.*;

public class Schedule {
    String doctorID;
    LocalDate date;
    List<LocalTime> timeSlots;

    public Schedule() {
        this.doctorID = "";
        this.date = LocalDate.now();
        this.timeSlots = new ArrayList<>();
    }

    public Schedule(String doctorID, LocalDate date, List<LocalTime> timeSlots) {
        this.doctorID = doctorID;
        this.date = date;
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    // get and set methods
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LocalTime> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<LocalTime> timeSlots) {
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    public String getScheduleDetails() {
        return "- Date: " + date + '\n' +
                "  Time slots: " + timeSlots +
                "\n";
    }

    public String toCSVRow() {
        String timeSlotsString = String.join(";",
                timeSlots.stream()
                        .map(LocalTime::toString)
                        .toArray(String[]::new));
        return String.format("%s,%s,\"%s\"", doctorID, date, timeSlotsString);
    }

    public void addTimeSlot(LocalTime timeSlot) {
        this.timeSlots.add(timeSlot);
        // sort the time slots
        Collections.sort(this.timeSlots);
    }
}
