package userInterface;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import appointment.Appointment;
import appointment.AppointmentManager;
import interfaces.AdminApptInterface;
import interfaces.ApptOutcomeInterface;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import user.Doctor;
import user.Patient;

/**
 * The class implements {@link AdminApptInterface} to provide a UI for administering appointment-related tasks for admins.
 * This class provides functionality to filter and view appointments and outcomes
 * by doctor, patient, or all appointments. It also allows admin to view appointment
 * outcomes such as medical records for patients.
 */
public class AdminApptUI implements AdminApptInterface{

    /**
     * Displays the main menu to filter appointments and handles user input.
     * Allows the admin to view appointments by various criteria such as all, by doctor ID,
     * or by patient ID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void filterAppointments(Scanner sc){
        int choice = -1;
        do {
            System.out.println("\n|---- Select a way to View Appointments ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Exisiting Appointments");
            System.out.println("2. View by DoctorID");
            System.out.println("3. View by PatientID");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch (choice) {
                case 1:
                    viewAllAppointments();
                    break;
                case 2:
                    selectDocAppt(sc);
                    break;
                case 3:
                    selectPatientAppt(sc);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice!=4);
    }

    /**
     * Displays all existing appointments.
     * Calls the {@link AppointmentManager} to display all the appointments.
     */
    public void viewAllAppointments(){
        AppointmentManager.displayAppointments();
    }

    /**
     * Helper method to select a doctor based on their DoctorID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @return {@link Doctor} object if found, otherwise {@code null}.
     */
    public Doctor selectDoctorHelper(Scanner sc){
        System.out.print("Enter DoctorID: ");
        Doctor doctor = DoctorsAcc.findDoctorById(sc.nextLine().trim());
    
        if(doctor!=null){
            return doctor;
        } else {
            System.out.println("DoctorID not found.");
            return null;
        }
    }

    /**
     * Filters appointments by a specific doctor based on DoctorID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void selectDocAppt(Scanner sc){
        Doctor doctor = selectDoctorHelper(sc);
        if(doctor!=null){
            getDocAppt(sc, doctor);
        } 
    }

    /**
     * Retrieves and displays appointments for a specific doctor.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @param doctor {@link Doctor} whose appointments are to be fetched.
     */
    public void getDocAppt(Scanner sc, Doctor doctor){
        if(doctor!=null){
            List<Appointment> docAppt = new ArrayList<>();
            docAppt = AppointmentManager.getAppointmentsByDoctor(doctor.getHospitalID());
            displayDocAppt(docAppt, doctor);
        }
    }

    /**
     * Displays appointments for a specific doctor.
     * 
     * @param docAppt List of appointments for the doctor.
     * @param doctor Doctor whose appointments are being displayed.
     */
    public void displayDocAppt(List<Appointment> docAppt, Doctor doctor){
        if (docAppt.isEmpty()) {
            System.out.println("\nDoctor " + doctor.getName() + "currently has no appointmnets.");
        } else {
            System.out.println("\nDoctor " + doctor.getName() + "'s appointments are:");
            for (Appointment appt : docAppt) {
                System.out.println(appt.getApptInfo());
            }
        }
    }

     /**
     * Helper method to select a patient based on their PatientID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @return {@link Patient} object if found, otherwise {@code null}.
     */
    public Patient selectPatientHelper(Scanner sc){
        System.out.print("Enter PatientID: ");
        Patient patient = PatientsAcc.findPatientById(sc.nextLine().trim());
    
        if(patient!=null){
            return patient;
        } else {
            System.out.println("PatientID not found.");
            return null;
        }
    }

    /**
     * Filters appointments by a specific patient based on PatientID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void selectPatientAppt(Scanner sc){
        Patient patient = selectPatientHelper(sc);
        if(patient!=null){
            getPatientAppt(sc, patient);
        }
    }

    /**
     * Retrieves and displays appointments for a specific patient.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @param patient {@link Patient} whose appointments are to be fetched.
     */
    public void getPatientAppt(Scanner sc, Patient patient){
        if(patient!=null){
            List<Appointment> patientAppt = new ArrayList<>();
            patientAppt = AppointmentManager.getAppointmentsByPatient(patient.getHospitalID());
            displayPatientAppt(patientAppt, patient);
        }
    }

    /**
     * Displays appointments for a specific patient.
     * 
     * @param patientAppt List of appointments for the patient.
     * @param patient Patient whose appointments are being displayed.
     */
    public void displayPatientAppt(List<Appointment> patientAppt, Patient patient){
        if (patientAppt.isEmpty()) {
            System.out.println("\nPatient " + patient.getName() + " currently has no appointments.");
        } else {
            System.out.println("\nPatient " + patient.getName() + "'s appointments are:");
            for (Appointment appt : patientAppt) {
                System.out.println(appt.getApptInfo());
            }
        }
    }

    /**
     * Displays the main menu to filter appointment outcomes and handles user input.
     * Allows the admin to view outcomes by various criteria such as all outcomes or by patient ID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void filterOutcomes(Scanner sc){
        int choice = -1;
        do {
            System.out.println("\n|---- Select a way to View Appointment Outcomes ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. View All Exisiting Appointment Outcomes");
            System.out.println("2. View by PatientID");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch (choice) {
                case 1:
                    viewAllApptOutcomes();
                    break;
                case 2:
                    selectPatientOutcomes(sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice!=4);
    }

    /**
     * Displays all appointment outcomes (medical records).
     * Calls the {@link MedicalRecordManager} to display all the medical records (appointment outcomes).
     */
    public void viewAllApptOutcomes(){
        MedicalRecordManager.displayMedicalRecords();
    }

    /**
     * Filters appointment outcomes by a specific patient based on PatientID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void selectPatientOutcomes(Scanner sc){
        Patient patient = selectPatientHelper(sc);
        if(patient!=null){
            getPatientOutcomes(sc, patient);
        }
    }

    /**
     * Retrieves and displays appointment outcomes (medical records) for a specific patient.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @param patient Patient whose appointment outcomes are to be fetched.
     */
    public void getPatientOutcomes(Scanner sc, Patient patient){
        if(patient!=null){
            List<MedicalRecord> patientOutcomes = new ArrayList<>();
            patientOutcomes = MedicalRecordManager.getMedicalRecordsByPatient(patient.getHospitalID());
            displayPatientOutcomes(patientOutcomes, patient);
        }
    }

    /**
     * Displays appointment outcomes (medical records) for a specific patient.
     * 
     * @param patientOutcomes List of medical records (appointment outcomes) for the patient.
     * @param patient Patient whose appointment outcomes are being displayed.
     */
    public void displayPatientOutcomes(List<MedicalRecord> patientOutcomes, Patient patient){
        if (patientOutcomes.isEmpty()) {
            System.out.println("\nPatient " + patient.getName() + " currently has no appointments outcomes.");
        } else {
            System.out.println("\nPatient " + patient.getName() + "'s appointments outcomes are:");
            for (MedicalRecord outcomes : patientOutcomes) {
                System.out.println(outcomes.getRecordDetails());
            }
        }
    }
}
