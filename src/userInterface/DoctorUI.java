package userInterface;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import accounts.PatientsAcc;
import interfaces.CommonMenu;
import interfaces.DocApptInterface;
import interfaces.DoctorMenu;
import interfaces.LeaveInterface;
import interfaces.MedicalRecInterface;
import interfaces.ScheduleInterface;
import main.HMSApp;
import medicalrecord.MedicalRecord;
import user.Doctor;
import user.Patient;

/**
 * The class implements the {@link DoctorMenu} interface to provide a UI for
 * doctors.
 * This class allows the doctor to manage their medical records, schedule, and
 * appointments.
 * It implements the methods defined in {@link DoctorMenu}.
 */
public class DoctorUI implements DoctorMenu {
    private final Doctor doctor;
    private final MedicalRecInterface medicalRecInterface;
    private final ScheduleInterface scheduleInterface;
    private final DocApptInterface docApptInterface;
    private final LeaveInterface leaveInterface;

    /**
     * Constructs a DoctorUI instance for a specific doctor.
     * 
     * @param doctor              The doctor associated with this UI
     * @param medicalRecInterface Interface for medical record management
     * @param scheduleInterface   Interface for managing doctor schedules
     * @param docApptInterface    Interface for handling doctor appointments
     */
    public DoctorUI(Doctor doctor, MedicalRecInterface medicalRecInterface,
    ScheduleInterface scheduleInterface, DocApptInterface docApptInterface, LeaveInterface leaveInterface){
        this.doctor = doctor;
        this.medicalRecInterface = medicalRecInterface;
        this.scheduleInterface = scheduleInterface;
        this.docApptInterface = docApptInterface;
        this.leaveInterface = leaveInterface;
    }

    /**
     * Logs out Doctor
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout() {
        System.out.println("Doctor Logging Out.");
        HMSApp.resetSessionColor(); // Reset terminal color
        return;
    }

    /**
     * Display main menu for Administrators
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\nDoctor Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Medical Record Menu");
            System.out.println("2. Schedule Menu");
            System.out.println("3. Appointment Menu"); 
            System.out.println("4. Manage Leaves");
            System.out.println("5. Logout");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
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
                    manageLeave(sc);
                    break;
                case 5:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice!=5);
    }

    /**
     * Displays the Medical Record menu, allowing the doctor to view or update a
     * patient's records.
     * 
     * @param sc     The scanner object used to capture user input
     * @param doctor The doctor whose medical records are being accessed
     */
    public void medicalRecordMenu(Scanner sc, Doctor doctor) {
        int choice = -1;
        List<MedicalRecord> records = medicalRecInterface.choosePatient(sc, doctor);

        if (records == null) {
            return;
        }

        MedicalRecord record = records.get(0);
        Patient patient = PatientsAcc.findPatientById(record.getPatientID());

        do {
            System.out.println("\nMedical Record Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Patient " + patient.getHospitalID() + " Records");
            System.out.println("2. Update Patient " + patient.getHospitalID() + " Records");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch (choice) {
                case 1:
                    // null record handled earlier, a patient will be returned and passed
                    medicalRecInterface.viewPatientMedicalRecords(PatientsAcc.findPatientById(record.getPatientID()));
                    break;
                case 2:
                    medicalRecInterface.updatePatientRecords(sc, records);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice != 3);
    }

    /**
     * Displays the Schedule menu, allowing the doctor to view or set their
     * availability schedule.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void scheduleMenu(Scanner sc) {
        int choice = -1;

        do {
            // is remove schedule gonna be implemented for doctor
            // so that they can personally remove their own schedule?
            System.out.println("\nSchedule Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Availability Schedule");
            System.out.println("2. Set Schedule");
            System.out.println("3. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
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
        } while (choice != 3);
    }

    /**
     * Displays the Appointment menu, allowing the doctor to view appointments,
     * handle appointment requests,
     * or record appointment outcomes.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
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
            } catch (InputMismatchException e) {
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
        } while (choice != 4);
    }

    /**
     * Displays and manages the Leave Management menu.
     * <p>
     * This method provides options to apply for leave, update or remove leave requests, 
     * view pending requests, and check the outcome of leave requests.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public void manageLeave(Scanner sc){
        int choice = -1;
        do {
            System.out.println("\n|---- Leave Management ----|");
            System.out.printf("%s\n", "-".repeat(36));
            System.out.println("1. Apply Leave");
            System.out.println("2. Update Leave Requests");
            System.out.println("3. Remove Leave Request");
            System.out.println("4. View Pending Leave Requests");
            System.out.println("5. View Leave Request Outcome");
            System.out.println("6. Go Back");
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
                    leaveInterface.applyLeave(sc, doctor);
                    break;
                case 2:
                    leaveInterface.updateLeave(sc,doctor);
                    break;
                case 3:
                    leaveInterface.removeLeave(sc, doctor);
                    break;
                case 4:
                    leaveInterface.displayPendingLeave(sc,doctor);
                    break;
                case 5:
                    leaveInterface.displayLeaveOutcome(sc,doctor);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice!=6);
    }

}
