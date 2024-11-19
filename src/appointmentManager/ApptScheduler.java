// package appointmentManager;
// import user.*;
// import accounts.DoctorsAcc;
// import accounts.PatientsAcc;

// import java.util.*;
// import java.time.LocalDate;
// import java.time.LocalTime;

// //might delete
// public class ApptScheduler {
// public List<Appointment> appointments;
// ApptManager repository = new ApptManager();

// public ApptScheduler() {
// this.appointments = new ArrayList<>();
// }

// public boolean isTimeSlotBooked(User doctor, LocalDate date, LocalTime time)
// {
// for (Appointment appointment : appointments) {
// if (appointment.getDoctor().equals(doctor) &&
// appointment.getDate().equals(date) &&
// appointment.getTime().equals(time)) {
// return true; // Time slot is already booked for this doctor
// }
// }
// return false; // Time slot is available
// }

// public void addAppointment(Appointment appointment) {
// appointments.add(appointment); // assuming appointments is a list storing all
// the appointments
// }

// public Appointment scheduleAppointment(String patientID, String doctorID,
// LocalDate date, LocalTime time) {
// //check whether returns doctor & patient type
// Doctor doctor = (Doctor) DoctorsAcc.findDoctorById(doctorID);
// Patient patient = (Patient) PatientsAcc.findPatientById(patientID);
// // Check if the time slot is available
// if (!isTimeSlotBooked(doctor, date, time)) {
// Appointment appointment = new Appointment(doctor, patient);
// appointment.setDate(date);
// appointment.setTime(time);
// appointment.createAppointmentID();
// appointment.createAppointment();
// appointment.setDoctor(doctor);
// addAppointment(appointment);
// System.out.println("Appointment scheduled successfully.");
// System.out.println("Your appointment ID is: " +
// appointment.getAppointmentID());
// return appointment;
// } else {
// System.out.println("The selected time slot is already booked.");
// return null;
// }
// }

// public Appointment rescheduleAppointment(int appointmentID, LocalDate
// newDate, LocalTime newTime) {
// Appointment appointment = repository.findAppointmentByID(appointmentID);
// if (appointment == null) {
// throw new IllegalArgumentException("Appointment not found");
// }

// User doctor = appointment.getDoctor();
// if (isTimeSlotBooked(doctor, newDate, newTime)) {
// throw new IllegalArgumentException("New time slot is not available.");
// }

// appointment.setDate(newDate);
// appointment.setTime(newTime);
// System.out.println("Appointment rescheduled successfully");
// return appointment;
// }

// public void cancel(Appointment appointment) {
// // mark the appointment as canceled to keep a record
// for (Appointment a : appointments) {
// if (a.getAppointmentID() == appointment.getAppointmentID()) {
// a.cancelAppointment();
// }
// }
// }
// }
