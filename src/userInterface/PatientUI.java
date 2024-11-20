package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

import accounts.PatientsAcc;
import interfaces.CommonMenu;
import interfaces.PatientApptInterface;
import interfaces.PatientMenu;
import main.HMSApp;
import user.Patient;

public class PatientUI implements PatientMenu{
    private final Patient patient;
    private final PatientApptInterface patientApptInterface;
    private final MedicalRecordUI medicalRecordUI;

    //changes made here
    public PatientUI(Patient patient, PatientApptInterface patientApptInterface, MedicalRecordUI medicalRecordUI){
        this.patient = patient;
        //this.patientApptUI = new PatientApptUI();
        this.patientApptInterface = patientApptInterface;
        this.medicalRecordUI = medicalRecordUI;
    }

    /**
     * Logs out Patient 
     * Implements the {@link CommonMenu#logout()} method
     */
    public void logout(){
        System.out.println("Patient Logging Out.");
        HMSApp.resetSessionColor(); // Reset terminal color
        return;
    }

    /**
     * Display main menu for Patients
     * Implements the {@link CommonMenu#displayMenu()} method
     */
    public void displayMenu() {
        int choice=-1;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nPatient Menu ");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Medical Records");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointments Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointments");
            System.out.println("6. Cancel Appointments");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointments Outcomes Reports");
            System.out.println("9. Logout");
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
                    medicalRecordUI.viewPatientMedicalRecords(patient);
                    break;
                case 2:
                    updatePersonalInfo(sc, patient);
                    break;
                case 3:
                    patientApptInterface.viewAvailableApptSlots(sc);
                    break;
                case 4:
                    patientApptInterface.scheduleAppointment(sc, patient);
                    break;
                case 5:
                    patientApptInterface.rescheduleAppointment(sc, patient);
                    break;
                case 6:
                    patientApptInterface.cancelAppointment(sc, patient);
                    break;
                case 7:
                    patientApptInterface.viewScheduledAppointments(patient);
                    break;
                case 8:
                    patientApptInterface.viewPastApptOutcomes(patient);
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice > 0 && choice < 10);
    }

    public void updatePersonalInfo(Scanner sc, Patient patient) {
        PatientsAcc.updatePatient(sc, patient);
    }

}
