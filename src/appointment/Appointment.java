package appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import io.IDGenerator;
import user.*;

public class Appointment {
    // will use enum ApptStatus class ltr
    private ApptStatus status;
    private Doctor doctor;
    private Patient patient;
    private String appointmentID;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // for appointment outcome
    private String consultationNotes;
    private String prescribedMedications;
    private String serviceType; // x-ray etc.

    private static int idCounter = 1000;

    // constructor
    public Appointment() {
        this.status = ApptStatus.NOT_SCHEDULED;
        this.doctor = null;
        this.patient = null;
        this.appointmentDate = null;
        this.appointmentTime = null;
        this.appointmentID = null;
    }

    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime,
            String appointmentID) {
        this.status = ApptStatus.PENDING;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentID = appointmentID;
    }

    // with status
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
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDate appointmentDate) {
        if (appointmentDate.isAfter(LocalDate.now()))
            this.appointmentDate = appointmentDate;
        else
            throw new IllegalArgumentException("Appointment date must be in the future");
    }

    public void setTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(ApptStatus status) {
        this.status = status;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    // getter methods
    public String getAppointmentID() {
        return appointmentID;
    }

    public ApptStatus getStatus() {
        return status;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return appointmentDate;
    }

    public LocalTime getTime() {
        return appointmentTime;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public String getServiceType() {
        return serviceType;
    }

    // other methods
    public String getApptInfo() {
        /*
         * return String.format(
         * "[AppointmentID = %s, PatientID = %s, DoctorID = %s, Date = %s, Time = %s, Status = %s, Consultation Notes = %s, Prescribed Medications = %s, Service Type = %s]"
         * ,
         * appointmentID, patient.getHospitalID(), doctor.getHospitalID(),
         * appointmentDate.toString(), appointmentTime.toString(), status,
         * consultationNotes, prescribedMedications, serviceType);
         */
        return "Appointment ID: " + appointmentID +
                "\nPatient: " + patient.getName() +
                "\nDoctor: " + doctor.getName() +
                "\nDate: " + appointmentDate +
                "\nTime: " + appointmentTime +
                "\nStatus: " + status +
                "\nConsultation Notes: " + consultationNotes +
                "\nPrescribed Medications: " + prescribedMedications +
                "\nService Type: " + serviceType +
                "\n";
    }

    // convert appointment to CSV format
    public String toCSVFormat() {
        return appointmentID + "," + patient.getHospitalID() + "," + doctor.getHospitalID() + "," + appointmentDate
                + "," + appointmentTime + "," + status + "," +
                consultationNotes + "," + prescribedMedications + "," + serviceType;
    }

    // Appointment
    public void confirmAppointment() {
        this.status = ApptStatus.CONFIRMED;
    }

    public void acceptAppointment() {
        this.status = ApptStatus.SCHEDULED;
    }

    public void cancelAppointment() {
        this.status = ApptStatus.CANCELLED;
    }

    public void completeAppointment() {
        this.status = ApptStatus.COMPLETED;
    }

    public void createAppointment() {
        this.status = ApptStatus.PENDING;
    }
}