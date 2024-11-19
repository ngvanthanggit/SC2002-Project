package interfaces;

import java.util.Scanner;
import user.Patient;

public interface PatientApptInterface {

    public void viewAvailableApptSlots(Scanner sc);
    public void scheduleAppointment(Scanner sc, Patient patient);
    public void rescheduleAppointment(Scanner sc, Patient patient);
    public void cancelAppointment(Scanner sc, Patient patient);
    public void viewScheduledAppointments(Patient patient);
    public void viewPastApptOutcomes(Patient patient);
}
