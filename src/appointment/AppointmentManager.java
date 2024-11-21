package appointment;

import utility.*;

import java.time.*;
import java.util.*;

import user.*;
import userInterface.ScheduleUI;
import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import interfaces.ScheduleInterface;

/**
 * This class is responsible for managing appointments.
 * <p>
 * This includes loading data from CSV files, displaying, adding, updating, and
 * removing appointments,
 * as well as managing appointment statuses and linking appointments with
 * doctors and patients.. The class interacts with utility classes like
 * {@code CSVread},
 * {@code CSVwrite}, and {@code CSVclear} to handle file operations.
 */
public class AppointmentManager {
    /** The list of appointments managed by the AppointmentManager. */
    private static List<Appointment> appointments = new ArrayList<>();
    /** The interface used for interacting with the schedule. */
    private static ScheduleInterface scheduleInterface = new ScheduleUI();
    /** The path to the original appointments CSV file. */
    private static String originalPath = "Data//Original/Appt_List.csv";
    /** The path to the updated appointments CSV file. */
    private static String updatedPath = "Data//Updated/Appt_List(Updated).csv";

    /**
     * Loads appointments from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the
     * updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first
     *                   time;
     *                   {@code false} otherwise.
     */
    public static void loadAppointments(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

        appointments.clear();

        Map<String, Integer> apptColumnMapping = new HashMap<>();
        apptColumnMapping.put("AppointmentID", 0);
        apptColumnMapping.put("PatientID", 1);
        apptColumnMapping.put("DoctorID", 2);
        apptColumnMapping.put("Date", 3);
        apptColumnMapping.put("Time", 4);
        apptColumnMapping.put("Status", 5);
        apptColumnMapping.put("ConsultationNotes", 6);
        apptColumnMapping.put("PrescribedMedications", 7);
        apptColumnMapping.put("ServiceType", 8);

        List<Appointment> apptMapList = CSVread.readApptCSV(filePath, apptColumnMapping);

        for (Appointment appt : apptMapList) {
            if (appt instanceof Appointment) {
                appointments.add(appt);
            }
        }

        if (appointments.isEmpty()) {
            System.out.println("No appointments were loaded.");
        } else {
            System.out.println("Appointments successfully loaded: " + appointments.size());
        }
    }

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param appointmentID The ID of the appointment to retrieve.
     * @return The {@link Appointment} object, or {@code null} if not found.
     */
    public static Appointment getAppointment(String appointmentID) {
        for (Appointment appt : appointments) {
            if (appt.getAppointmentID().equals(appointmentID)) {
                return appt;
            }
        }
        return null;
    }

    /** Displays all appointments currently in the list. */
    public static void displayAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("The appointment list is currently empty.");
        } else {
            System.out.println("\nThe Appointments in the CSV file are: ");
            for (Appointment appt : appointments) {
                System.out.println(appt.getApptInfo());
            }
        }
    }

    /**
     * Duplicates the current appointments list to the updated appointments CSV
     * file.
     */
    public static void duplicateAppointments() {
        CSVwrite.writeCSVList(updatedPath, appointments);
    }

    /**
     * Retrieves a list of appointments for a given patient.
     * 
     * @param patientID The hospital ID of the patient whose appointments are to be
     *                  retrieved.
     * @return A list of {@link Appointment} objects associated with the given
     *         patient.
     */
    public static List<Appointment> getAppointmentsByPatient(String patientID) {
        List<Appointment> patientAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatient().getHospitalID().equals(patientID)) {
                patientAppts.add(appt);
            }
        }
        return patientAppts;
    }

    /**
     * Retrieves a list of appointments for a given patient with a specified status.
     * 
     * @param patientID The hospital ID of the patient whose appointments are to be
     *                  retrieved.
     * @param status    The status of the appointments to retrieve.
     * @return A list of {@link Appointment} objects matching the patient and
     *         status.
     */

    public static List<Appointment> getAppointmentsByPatient(String patientID, ApptStatus status) {
        List<Appointment> patientAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatient().getHospitalID().equals(patientID) && appt.getStatus() == status) {
                patientAppts.add(appt);
            }
        }
        return patientAppts;
    }

    /**
     * Retrieves a list of appointments for a given doctor.
     * 
     * @param doctorID The hospital ID of the doctor whose appointments are to be
     *                 retrieved.
     * @return A list of {@link Appointment} objects associated with the given
     *         doctor.
     */
    public static List<Appointment> getAppointmentsByDoctor(String doctorID) {
        List<Appointment> doctorAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctor().getHospitalID().equals(doctorID)) {
                doctorAppts.add(appt);
            }
        }
        return doctorAppts;
    }

    /**
     * Retrieves a list of appointments for a given doctor with a specified status.
     * 
     * @param doctorID The hospital ID of the doctor whose appointments are to be
     *                 retrieved.
     * @param status   The status of the appointments to retrieve.
     * @return A list of {@link Appointment} objects matching the doctor and status.
     */
    public static List<Appointment> getAppointmentsByDoctor(String doctorID, ApptStatus status) {
        List<Appointment> doctorAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctor().getHospitalID().equals(doctorID) && appt.getStatus() == status) {
                doctorAppts.add(appt);
            }
        }
        return doctorAppts;
    }

    /**
     * Adds a new appointment to the appointments list.
     * 
     * @param appt The {@link Appointment} object to be added.
     */
    public static void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    /**
     * Removes an appointment from the appointments list.
     * If the appointment is scheduled, the doctor's time slot is returned to their
     * schedule.
     * 
     * @param appt The {@link Appointment} object to be removed.
     */
    public static void removeAppointment(Appointment appt) {
        // check if the appointment was scheduled
        if (appt.getStatus() == ApptStatus.SCHEDULED) {
            // add the time slot back to the doctor's available time slots
            Doctor doctor = DoctorsAcc.findDoctorById(appt.getDoctor().getHospitalID());
            scheduleInterface.addSchedule(appt.getDate(), appt.getTime(), doctor);
        }
        boolean removed = appointments.remove(appt);
        if (removed) {
            System.out.println("Appointment removed successfully.");
            duplicateAppointments();
        } else {
            System.out.println("appointmentManager::removeAppointment(): Appointment not found.");
        }
    }

    /**
     * Requests an appointment by creating a new appointment object with a pending
     * status.
     * 
     * @param doctorID  The hospital ID of the doctor for the appointment.
     * @param patientID The hospital ID of the patient requesting the appointment.
     * @param date      The date for the appointment.
     * @param time      The time for the appointment.
     */
    public static void requestAppointment(String doctorID, String patientID, LocalDate date, LocalTime time) {
        String appointmentID = IDGenerator.generateID("AP", appointments, Appointment::getAppointmentID, 3);
        Doctor doctor = DoctorsAcc.findDoctorById(doctorID);
        Patient patient = (Patient) PatientsAcc.findPatientById(patientID);

        Appointment appt = new Appointment(doctor, patient, date, time, appointmentID, ApptStatus.PENDING);
        addAppointment(appt);
        duplicateAppointments();
    }

    /**
     * Marks an appointment as completed and records the appointment outcome.
     * 
     * @param appointment The {@link Appointment} object to be completed.
     */
    public static void completeAppointment(Appointment appointment) {
        appointment.completeAppointment();
        // save to file
        duplicateAppointments();
        System.out.println("Appointment Outcome Recorded Successfully.");
    }
}
