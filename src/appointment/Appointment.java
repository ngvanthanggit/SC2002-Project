package appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import user.*;

/**
 * This class represents a medical appointment between a doctor and a patient. It stores details about the appointment,
 * such as the status, the doctor and patient involved, appointment date and time, consultation notes, prescribed medications,
 * and service type (e.g., X-ray).
 * <p>
 * The appointment status is managed using the ApptStatus enum, and the class provides methods to update and retrieve appointment details.
 */
public class Appointment {
    private ApptStatus status;
    private Doctor doctor;
    private Patient patient;
    private String appointmentID;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // for appointment outcome
    private String consultationNotes;
    private String prescribedMedications;
    private String serviceType; 

    /**
     * Default constructor that initializes the appointment with a status of {@code NOT_SCHEDULED} and {@code null} values for the doctor, patient,
     * appointment date, time, and ID.
     */
    public Appointment() {
        this.status = ApptStatus.NOT_SCHEDULED;
        this.doctor = null;
        this.patient = null;
        this.appointmentDate = null;
        this.appointmentTime = null;
        this.appointmentID = null;
    }

    /**
     * Constructor for creating a new appointment with specific doctor, patient, date, time, and appointment ID.
     * The appointment status is set to {@code PENDING}.
     * 
     * @param doctor The doctor assigned to the appointment.
     * @param patient The patient assigned to the appointment.
     * @param appointmentDate The date of the appointment.
     * @param appointmentTime The time of the appointment.
     * @param appointmentID The unique identifier for the appointment.
     */
    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime,
            String appointmentID) {
        this.status = ApptStatus.PENDING;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentID = appointmentID;
    }

    /**
     * Constructor for creating a new appointment with a specified status.
     * 
     * @param doctor The doctor assigned to the appointment.
     * @param patient The patient assigned to the appointment.
     * @param appointmentDate The date of the appointment.
     * @param appointmentTime The time of the appointment.
     * @param appointmentID The unique identifier for the appointment.
     * @param status The current status of the appointment (e.g., {@code PENDING}, {@code CONFIRMED}).
     */
    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime,
            String appointmentID, ApptStatus status) {
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentID = appointmentID;
    }

    // setter methods

    /**
     * Sets the doctor for the appointment.
     * 
     * @param doctor The doctor to be assigned to the appointment.
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Sets the patient for the appointment.
     * 
     * @param patient The patient to be assigned to the appointment.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Sets the date of the appointment. The date must be in the future.
     * 
     * @param appointmentDate The date to set for the appointment.
     * @throws IllegalArgumentException If the provided date is in the past.
     */
    public void setDate(LocalDate appointmentDate) {
        if (appointmentDate.isAfter(LocalDate.now()))
            this.appointmentDate = appointmentDate;
        else
            throw new IllegalArgumentException("Appointment date must be in the future");
    }

    /**
     * Sets the time of the appointment.
     * 
     * @param appointmentTime The time to set for the appointment.
     */
    public void setTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Sets the status of the appointment.
     * 
     * @param status The status to set for the appointment.
     */
    public void setStatus(ApptStatus status) {
        this.status = status;
    }

    /**
     * Sets the appointment ID.
     * 
     * @param appointmentID The unique identifier to set for the appointment.
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Sets the consultation notes for the appointment.
     * 
     * @param consultationNotes The consultation notes to set.
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Sets the service type for the appointment (e.g., X-ray).
     * 
     * @param serviceType The service type to set.
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Sets the prescribed medications for the appointment.
     * 
     * @param prescribedMedications The prescribed medications to set.
     */
    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    // getter methods

    /**
     * Returns the unique appointment ID.
     * 
     * @return The appointment ID.
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Returns the status of the appointment.
     * 
     * @return The status of the appointment.
     */
    public ApptStatus getStatus() {
        return status;
    }

    /**
     * Returns the doctor assigned to the appointment.
     * 
     * @return The doctor for the appointment.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Returns the patient assigned to the appointment.
     * 
     * @return The patient for the appointment.
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the date of the appointment.
     * 
     * @return The date of the appointment.
     */
    public LocalDate getDate() {
        return appointmentDate;
    }

    /**
     * Returns the time of the appointment.
     * 
     * @return The time of the appointment.
     */
    public LocalTime getTime() {
        return appointmentTime;
    }

    /**
     * Returns the consultation notes for the appointment.
     * 
     * @return The consultation notes.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Returns the prescribed medications for the appointment.
     * 
     * @return The prescribed medications.
     */
    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    /**
     * Returns the service type for the appointment (e.g., X-ray).
     * 
     * @return The service type.
     */
    public String getServiceType() {
        return serviceType;
    }

    // other methods

    /**
     * Returns a formatted string representing all details of the appointment.
     * 
     * @return A string containing all appointment details.
     */
    public String getApptInfo() {
        /*
         * return String.format(
         * "[AppointmentID = %s, PatientID = %s, DoctorID = %s, Date = %s, Time = %s, Status = %s, Consultation Notes = %s, Prescribed Medications = %s, Service Type = %s]"
         * ,
         * appointmentID, patient.getHospitalID(), doctor.getHospitalID(),
         * appointmentDate.toString(), appointmentTime.toString(), status,
         * consultationNotes, prescribedMedications, serviceType);
         */
        return "\nAppointment ID: " + appointmentID +
                "\nPatient: " + patient.getName() +
                "\nDoctor: " + doctor.getName() +
                "\nDate: " + appointmentDate +
                "\nTime: " + appointmentTime +
                "\nStatus: " + status +
                "\nConsultation Notes: " + consultationNotes +
                "\nPrescribed Medications: " + prescribedMedications +
                "\nService Type: " + serviceType;
    }

    /**
     * Returns the appointment details in CSV format.
     * 
     * @return A string in CSV format representing the appointment details.
     */
    /**
 * Returns the appointment details in CSV format.
 *
 * @return A string in CSV format representing the appointment details.
 */
public String toCSVFormat() {
    String patientId = (patient != null) ? patient.getHospitalID() : "NULL";
    String doctorId = (doctor != null) ? doctor.getHospitalID() : "NULL";
    String notes = (consultationNotes != null) ? consultationNotes : "";
    String medications = (prescribedMedications != null) ? prescribedMedications : "";
    String service = (serviceType != null) ? serviceType : "";

    return appointmentID + "," + patientId + "," + doctorId + "," + appointmentDate
            + "," + appointmentTime + "," + status + "," +
            notes + "," + medications + "," + service;
}


    /**
     * Confirms the appointment by setting its status to {@code CONFIRMED}.
     */
    public void confirmAppointment() {
        this.status = ApptStatus.CONFIRMED;
    }

    /**
     * Accepts the appointment by setting its status to {@code SCHEDULED}.
     */
    public void acceptAppointment() {
        this.status = ApptStatus.SCHEDULED;
    }

    /**
     * Cancels the appointment by setting its status to {@code CANCELLED}.
     */
    public void cancelAppointment() {
        this.status = ApptStatus.CANCELLED;
    }

    /**
     * Marks the appointment as completed by setting its status to {@code COMPLETED}.
     */
    public void completeAppointment() {
        this.status = ApptStatus.COMPLETED;
    }

    /**
     * Creates a new appointment by setting its status to {@code PENDING}.
     */
    public void createAppointment() {
        this.status = ApptStatus.PENDING;
    }
}