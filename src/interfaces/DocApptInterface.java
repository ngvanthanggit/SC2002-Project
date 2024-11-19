package interfaces;

import java.util.Scanner;

import user.Doctor;

public interface DocApptInterface {
    public void viewAppointments(Doctor doctor);
    public void AppointmentRequestHandler(Scanner sc, Doctor doctor);
    public void recordAppointmentOutcome(Scanner sc, Doctor doctor);
}
