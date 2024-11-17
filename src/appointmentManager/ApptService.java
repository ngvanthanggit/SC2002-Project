// package appointmentManager;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.List;

// import user.*;

// import accounts.PatientsAcc;

// //might delete
// public class ApptService {
// private ApptScheduler scheduler;
// private ApptManager repository;

// public ApptService(ApptScheduler scheduler, ApptManager repository){
// this.scheduler = scheduler;
// this.repository = repository;
// }

// public void scheduleAppointment(String patientID, String doctorID, LocalDate
// date, LocalTime time){
// Appointment appointment = scheduler.scheduleAppointment(patientID, doctorID,
// date, time);
// repository.saveToCSV(appointment);
// }

// public void rescheduleAppointment(int appointmentID, LocalDate newDate,
// LocalTime newTime){
// Appointment appointment = scheduler.rescheduleAppointment(appointmentID,
// newDate, newTime);
// appointment.setStatus(Appointment.Status.PENDING);
// repository.removeFromCSV(appointment);
// repository.saveToCSV(appointment);
// }

// public void cancelAppointment(int appointmentID){
// Appointment appointment = repository.findAppointmentByID(appointmentID);
// scheduler.cancel(appointment);
// repository.removeFromCSV(appointment);
// }

// public void viewScheduledAppointment(String patientID){
// List<Appointment> appt = repository.loadConfirmedAppointments();
// boolean hasAppointments = false; // To track if any upcoming appointments are
// found

// System.out.println("----------------------------------------");
// for (Appointment appointment : appt){
// if (appointment.getPatient().getHospitalID().equals(patientID) &&
// appointment.getDate().isAfter(LocalDate.now())) {
// hasAppointments = true;
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Appointment with doctor " +
// appointment.getDoctor().getName());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// // Add a separator between appointments
// System.out.println("----------------------------------------");
// }
// }
// if (!hasAppointments) {
// System.out.println("No upcoming appointments found for Patient ID: " +
// patientID);
// }
// }

// public void getAllOutcomeRecords(String hospitalID){
// User patient = PatientsAcc.findPatientById(hospitalID);
// List<Appointment> pastAppointments;
// if (patient == null) {
// System.out.println("Patient not found for hospital ID: " + hospitalID);
// return;
// }
// try {
// pastAppointments = repository.getPastAppointments(patient);
// if (pastAppointments == null || pastAppointments.isEmpty()){
// throw new Exception ("No outcome record found for patient: " + hospitalID);
// }
// } catch (Exception e) {
// System.out.println(e.getMessage());
// return;
// }
// System.out.println("----------------------------------------");
// for (Appointment appointment : pastAppointments){
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Appointment with doctor " +
// appointment.getDoctor().getName());
// System.out.println("Status: " + appointment.getStatus());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// System.out.println("Consultation notes: " +
// appointment.getConsultationNotes());
// System.out.println("Prescribed medications: " +
// appointment.getPrescribedMedications());
// System.out.println("Type of service provided: " +
// appointment.getServiceType());
// // Add a separator between appointments
// System.out.println("----------------------------------------");
// }
// }

// public void viewPersonalSched(String doctorID){
// List<Appointment> allAppointments;
// try {
// allAppointments = repository.getAllAppointments(doctorID);
// if (allAppointments == null || allAppointments.isEmpty()){
// throw new Exception ("No record found for: " + doctorID);
// }
// } catch (Exception e) {
// System.out.println(e.getMessage());
// return;
// }
// System.out.println("----------------------------------------");
// for (Appointment appointment : allAppointments){
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Appointment with patient " +
// appointment.getPatient().getName());
// System.out.println("Status: " + appointment.getStatus());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// // Add a separator between appointments
// System.out.println("----------------------------------------");
// }
// }

// public void setAvailability(){

// }

// public void acceptAppointment(int appointmentID){
// Appointment appointment;
// try {
// appointment = repository.findAppointmentByID(appointmentID);
// if (appointment == null){
// throw new Exception ("No appointment found: " + appointmentID);
// }
// appointment.confirmAppointment();
// if (appointment.getStatus().equals(Appointment.Status.CONFIRMED)) {
// repository.updateCSV(appointment);
// System.out.println("Appointment accepted succesfully.");
// return;
// } else {
// System.out.println("Appointment acception failed. Please try again.");
// return;
// }
// } catch (Exception e) {
// System.out.println(e.getMessage());
// return;
// }

// }

// public void declineAppointment(int appointmentID){
// Appointment appointment;
// try {
// appointment = repository.findAppointmentByID(appointmentID);
// if (appointment == null){
// throw new Exception ("No appointment found: " + appointmentID);
// }
// appointment.cancelAppointment();
// if (appointment.getStatus().equals(Appointment.Status.CANCELLED)) {
// repository.updateCSV(appointment);
// System.out.println("Appointment cancelled succesfully.");
// return;
// } else {
// System.out.println("Appointment cancellation failed. Please try again.");
// return;
// }
// } catch (Exception e) {
// System.out.println(e.getMessage());
// return;
// }
// }

// public void viewUpcomingAppointment(String doctorID){
// List<Appointment> appt = repository.loadConfirmedAppointments();
// boolean hasAppointments = false; // To track if any upcoming appointments are
// found

// System.out.println("----------------------------------------");
// for (Appointment appointment : appt){
// if (appointment.getDoctor().getHospitalID().equals(doctorID) &&
// appointment.getDate().isAfter(LocalDate.now())) {
// hasAppointments = true;
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Appointment with patient " +
// appointment.getPatient().getName());
// System.out.println("Status: " + appointment.getStatus());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// // Add a separator between appointments
// System.out.println("----------------------------------------");
// }
// }
// if (!hasAppointments) {
// System.out.println("No upcoming appointments found for Doctor ID: " +
// doctorID);
// }
// }

// public void recordAppointmentOutcomes(int appointmentID, String
// consultationNotes, String prescribedMedications, String serviceType) {
// Appointment appointment = repository.findAppointmentByID(appointmentID);
// if (appointment == null) {
// System.out.println("Appointment not found.");
// return;
// }

// appointment.setConsultationNotes(consultationNotes);
// appointment.setPrescribedMedications(prescribedMedications);
// appointment.setServiceType(serviceType);

// // Update the appointment in the CSV file
// repository.updateCSV(appointment);
// System.out.println("Appointment outcomes recorded successfully.");
// }

// public void completedAppointment(int appointmentID){
// Appointment appointment = repository.findAppointmentByID(appointmentID);
// if (appointment == null) {
// System.out.println("Appointment not found.");
// return;
// }
// appointment.completeAppointment();
// repository.updateCSV(appointment);
// }

// public void viewAppointmentUpdates() {
// List<Appointment> appt = repository.loadAllAppointments();
// if (appt.isEmpty()) {
// System.out.println("No appointments available.");
// } else {
// System.out.println("Real-time updates for all appointments:");
// System.out.println("----------------------------------------");
// for (Appointment appointment : appt) {
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Patient: " + appointment.getPatient().getName() + ", " +
// appointment.getPatient().getHospitalID());
// System.out.println("Doctor: " + appointment.getDoctor().getName()+ ", " +
// appointment.getDoctor().getHospitalID());
// System.out.println("Status: " + appointment.getStatus());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// System.out.println("Consultation notes: " +
// appointment.getConsultationNotes());
// System.out.println("Prescribed medications: " +
// appointment.getPrescribedMedications());
// System.out.println("Type of service provided: " +
// appointment.getServiceType());
// // Add a separator between appointments
// System.out.println("----------------------------------------");
// }
// }
// }

// public void getOutcomeRecord(int appointmentID) {
// Appointment appointment = repository.getOutcomeRecord(appointmentID);
// if (appointment != null) {
// System.out.println("Appointment ID: " + appointment.getAppointmentID());
// System.out.println("Patient: " + appointment.getPatient().getName());
// System.out.println("Doctor: " + appointment.getDoctor().getName());
// System.out.println("Status: " + appointment.getStatus());
// System.out.println("Date: " + appointment.getDate());
// System.out.println("Time: " + appointment.getTime());
// System.out.println("Consultation notes: " +
// appointment.getConsultationNotes());
// System.out.println("Prescribed medications: " +
// appointment.getPrescribedMedications());
// System.out.println("Type of service provided: " +
// appointment.getServiceType());
// }
// }
// }
