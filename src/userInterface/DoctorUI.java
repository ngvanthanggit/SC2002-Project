package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import accounts.PatientsAcc;
import interfaces.CommonMenu;
import interfaces.DocApptInterface;
import interfaces.DoctorMenu;
import interfaces.MedicalRecInterface;
import interfaces.ScheduleInterface;
import main.HMSApp;
import medicalrecord.MedicalRecord;
import user.Doctor;

public class DoctorUI implements DoctorMenu {
    private final Doctor doctor;
    private final MedicalRecInterface medicalRecInterface;
    private final ScheduleInterface scheduleInterface;
    private final DocApptInterface docApptInterface;

    public DoctorUI(Doctor doctor, MedicalRecInterface medicalRecInterface,
    ScheduleInterface scheduleInterface, DocApptInterface docApptInterface){
        this.doctor = doctor;
        this.medicalRecInterface = medicalRecInterface;
        this.scheduleInterface = scheduleInterface;
        this.docApptInterface = docApptInterface;
    }

    /**
     * Logs out Doctor 
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout(){
        System.out.println("Doctor Logging Out.");
        HMSApp.resetSessionColor(); // Reset terminal color
        return;
    }

    /**
     * Display main menu for Administrators
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu(){
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\nDoctor Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Medical Record Menu");
            System.out.println("2. Schedule Menu");
            System.out.println("3. Appointment Menu");
            System.out.println("4. Logout");
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
                    medicalRecordMenu(sc, doctor);
                    break;
                case 2:
                    scheduleMenu(sc);
                    break;
                case 3:
                    appointmentMenu(sc);
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=4);
    }

    public void medicalRecordMenu(Scanner sc, Doctor doctor) {
        int choice = -1;
        MedicalRecord record = medicalRecInterface.choosePatient(sc, doctor);

        if(record == null){
            return;
        }

        do {
            System.out.println("\nMedical Record Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Patient " + record.getPatientID() + " Records");
            System.out.println("2. Update Patient " + record.getPatientID() + " Records");
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
                    //null record handled earlier, a patient will be returned and passed
                    medicalRecInterface.viewPatientMedicalRecords(PatientsAcc.findPatientById(record.getPatientID()));
                    break;
                case 2:
                    medicalRecInterface.updatePatientRecords(sc, record);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=3);
    }

    public void scheduleMenu(Scanner sc) {
        int choice =-1;

        do {
            //is remove schedule gonna be implemented for doctor 
            //so that they can personally remove their own schedule?
            System.out.println("\nSchedule Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Availability Schedule");
            System.out.println("2. Set Schedule");
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
                    scheduleInterface.viewSchedule(doctor);
                    break;
                case 2:
                    scheduleInterface.setSchedule(sc, doctor);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=3);
    }

    public void appointmentMenu(Scanner sc) {
        int choice = -1;

        do {
            System.out.println("\nAppointment Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Appointments");
            System.out.println("2. Appointment Requests");
            System.out.println("3. Record Appointments Outcome");
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
                    docApptInterface.viewAppointments(doctor);
                    break;
                case 2:
                    docApptInterface.AppointmentRequestHandler(sc, doctor);
                    break;
                case 3:
                    docApptInterface.recordAppointmentOutcome(sc, doctor);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=4);
    }

}