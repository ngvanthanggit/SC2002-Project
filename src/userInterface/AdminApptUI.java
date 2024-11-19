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
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import user.Doctor;
import user.Patient;

public class AdminApptUI implements AdminApptInterface{

    //main menu for appointments
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

    //display all appointments
    public void viewAllAppointments(){
        AppointmentManager.displayAppointments();
    }

    //helper function for selecting doctor
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

    //filter by doctor
    public void selectDocAppt(Scanner sc){
        Doctor doctor = selectDoctorHelper(sc);
        if(doctor!=null){
            getDocAppt(sc, doctor);
        } 
    }

    //get specific doctor appt
    public void getDocAppt(Scanner sc, Doctor doctor){
        if(doctor!=null){
            List<Appointment> docAppt = new ArrayList<>();
            docAppt = AppointmentManager.getAppointmentsByDoctor(doctor.getHospitalID());
            displayDocAppt(docAppt, doctor);
        }
    }

    //display only specific doctor appt
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

    //helper function for selecting patient
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

    //filter by patientID
    public void selectPatientAppt(Scanner sc){
        Patient patient = selectPatientHelper(sc);
        if(patient!=null){
            getPatientAppt(sc, patient);
        }
    }

    //get specific patient appt
    public void getPatientAppt(Scanner sc, Patient patient){
        if(patient!=null){
            List<Appointment> patientAppt = new ArrayList<>();
            patientAppt = AppointmentManager.getAppointmentsByPatient(patient.getHospitalID());
            displayPatientAppt(patientAppt, patient);
        }
    }

    //display only specific patient appt
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

    //main menu for appointment outcomes
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

    //display all outcomes
    public void viewAllApptOutcomes(){
        MedicalRecordManager.displayMedicalRecords();
    }

    //filter by patientID
    public void selectPatientOutcomes(Scanner sc){
        Patient patient = selectPatientHelper(sc);
        if(patient!=null){
            getPatientOutcomes(sc, patient);
        }
    }

    //get only specific patient outcomes
    public void getPatientOutcomes(Scanner sc, Patient patient){
        if(patient!=null){
            List<MedicalRecord> patientOutcomes = new ArrayList<>();
            patientOutcomes = MedicalRecordManager.getMedicalRecordsByPatient(patient.getHospitalID());
            displayPatientOutcomes(patientOutcomes, patient);
        }
    }

    //display only specific patient outcomes
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
