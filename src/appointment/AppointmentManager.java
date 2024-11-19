package appointment;

import utility.*;

import java.time.*;
import java.util.*;

import user.*;
import userInterface.ScheduleUI;
import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import interfaces.ScheduleInterface;

public class AppointmentManager {
    private static List<Appointment> appointments = new ArrayList<>();
    private static ScheduleInterface scheduleInterface = new ScheduleUI();
    private static String originalPath = "Data//Original/Appt_List.csv";
    private static String updatedPath = "Data//Updated/Appt_List(Updated).csv";

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

    public static Appointment getAppointment(String appointmentID) {
        for (Appointment appt : appointments) {
            if (appt.getAppointmentID().equals(appointmentID)) {
                return appt;
            }
        }
        return null;
    }

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

    public static void duplicateAppointments() {
        CSVwrite.writeCSVList(updatedPath, appointments);
    }
    
    // getting appointments by patient
    public static List<Appointment> getAppointmentsByPatient(String patientID) {
        List<Appointment> patientAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatient().getHospitalID().equals(patientID)) {
                patientAppts.add(appt);
            }
        }
        return patientAppts;
    }

    public static List<Appointment> getAppointmentsByPatient(String patientID, ApptStatus status) {
        List<Appointment> patientAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatient().getHospitalID().equals(patientID) && appt.getStatus() == status) {
                patientAppts.add(appt);
            }
        }
        return patientAppts;
    }

    // getting appointments by doctor
    public static List<Appointment> getAppointmentsByDoctor(String doctorID) {
        List<Appointment> doctorAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctor().getHospitalID().equals(doctorID)) {
                doctorAppts.add(appt);
            }
        }
        return doctorAppts;
    }

    public static List<Appointment> getAppointmentsByDoctor(String doctorID, ApptStatus status) {
        List<Appointment> doctorAppts = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getDoctor().getHospitalID().equals(doctorID) && appt.getStatus() == status) {
                doctorAppts.add(appt);
            }
        }
        return doctorAppts;
    }

    public static void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

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

    public static void requestAppointment(String doctorID, String patientID, LocalDate date, LocalTime time) {
        String appointmentID = IDGenerator.generateID("AP", appointments, Appointment::getAppointmentID, 3);
        Doctor doctor = DoctorsAcc.findDoctorById(doctorID);
        Patient patient = (Patient) PatientsAcc.findPatientById(patientID);

        Appointment appt = new Appointment(doctor, patient, date, time, appointmentID, ApptStatus.PENDING);
        addAppointment(appt);
        duplicateAppointments();
    }

    //called by doctor when writing medicalRecord  
    public static void completeAppointment(Appointment appointment){
        appointment.completeAppointment();
        // save to file
        duplicateAppointments();
        System.out.println("Appointment Outcome Recorded Successfully.");
    }
}
