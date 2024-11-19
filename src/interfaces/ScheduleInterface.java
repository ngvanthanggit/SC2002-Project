package interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import user.Doctor;

public interface ScheduleInterface {
    public void viewSchedule(Doctor doctor);
    public void setSchedule(Scanner sc, Doctor doctor);
    public void addSchedule(LocalDate date, LocalTime time, Doctor doctor);
    public void removeSchedule(LocalDate date, LocalTime time, Doctor doctor);
}
