package appointmentManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import user.*;


public class Appointment {
    public enum Status {
        SCHEDULED, CANCELLED, COMPLETED, NOT_SCHEDULED, PENDING, CONFIRMED
    }
    private Status status;
    private Doctor doctor;
    private Patient patient;
    private int appointmentID;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String consultationNotes;
    private String prescribedMedications;
    private String serviceType;

    private static int idCounter = 1000;

    // constructor
    public Appointment(){
        this.status = Status.NOT_SCHEDULED; 
        this.doctor = null; 
        this.patient = null;
        this.appointmentDate = null; 
        this.appointmentTime = null; 
        this.appointmentID = -1;
    }

    public Appointment(Doctor doctor, Patient patient){
        this.status = Status.NOT_SCHEDULED; // default values
        this.doctor = doctor; 
        this.patient = patient;
        // this.appointmentDate = LocalDate.now(); // set default date as today
        // this.appointmentTime = LocalTime.now(); // set default time as now
        this.appointmentID = -1;
    } 

    // with appointmentID
    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime, int appointmentID){
        this.status = Status.PENDING; 
        this.doctor = doctor; 
        this.patient = patient;
        this.appointmentDate = appointmentDate; 
        this.appointmentTime = appointmentTime; 
        this.appointmentID = appointmentID;
    }

    // without appointmentID
    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime){
        this.status = Status.PENDING; 
        this.doctor = doctor; 
        this.patient = patient;
        this.appointmentDate = appointmentDate; 
        this.appointmentTime = appointmentTime; 
        this.appointmentID = createAppointmentID();
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void confirmAppointment() {
        this.status = Status.CONFIRMED;
    }

    public void cancelAppointment() {
        this.status = Status.CANCELLED;
    }

    public void completeAppointment() {
        this.status = Status.COMPLETED;
    }

    public void createAppointment() {
        this.status = Status.PENDING;
    }

    public void assignDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public void assignPatient(Patient patient){
        this.patient = patient;
    }

    public void assignDate(LocalDate appointmentDate){
        if (appointmentDate.isAfter(LocalDate.now()))
            this.appointmentDate = appointmentDate;
        else 
            throw new IllegalArgumentException("Appointment date must be in the future");
    }

    public void assignTime(LocalTime appointmentTime){
        this.appointmentTime = appointmentTime;
    }

    public int createAppointmentID(){
        this.appointmentID = idCounter--; 
        return idCounter; // decrement and return
    }

    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }

    public int getAppointmentID(){
        return appointmentID;
    }

    public Status getStatus() {
        return status;
    }

    public User getDoctor() {
        return doctor;
    }

    public User getPatient() {
        return patient;
    }

    public LocalDate getDate(){
        return appointmentDate;
    }

    public LocalTime getTime(){
        return appointmentTime;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    // convert appointment to CSV format
    public String toCSVFormat() {
        return appointmentID + "," + patient.getHospitalID() + "," + doctor.getHospitalID() + "," + appointmentDate + "," + appointmentTime + "," + status + "," +
        consultationNotes + "," + prescribedMedications + "," + serviceType;
    }

    // convert CSV to appointment
    public static Appointment fromCSV(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid CSV line: " + csvLine);
        }
        String[] values = csvLine.split(",");
        try {
            int appointmentID = Integer.parseInt(values[0]);
            String patientID = values[1];
            String doctorID = values[2];
            LocalDate date = LocalDate.parse(values[3]);
            LocalTime time = LocalTime.parse(values[4]);
            Appointment.Status status = Appointment.Status.valueOf(values[5].toUpperCase());
            // Recreate Appointment object
            //check whether returns patient
            Patient patient = (Patient) PatientsAcc.findPatientById(patientID);
            //check whether returns doctor
            Doctor doctor = (Doctor) DoctorsAcc.findDoctorById(doctorID);
            Appointment appointment = new Appointment(doctor, patient, date, time);
            appointment.updateStatus(status);
            appointment.setAppointmentID(appointmentID);

            appointment.setConsultationNotes(values.length > 6 ? values[6] : "");
            appointment.setPrescribedMedications(values.length > 7 ? values[7] : "");
            appointment.setServiceType(values.length > 8 ? values[8] : "");
            
            return appointment;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number format in CSV line: " + csvLine);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date/time format in CSV line: " + csvLine, e.getParsedString(), e.getErrorIndex());
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV line: " + csvLine, e);
        }
    }
}