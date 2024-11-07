package menus;

import user.Doctor;
import java.util.Scanner;

public class DoctorMenu {
    private Doctor doctor;

    public DoctorMenu() {
        this.doctor = null;
    }

    public DoctorMenu(Doctor doctor) {
        this.doctor = doctor;
    }

    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nDoctor Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Patient Records");
            System.out.println("2. Update Patient Records");
            System.out.println("3. View Schedule");
            System.out.println("4. Set Schedule");
            System.out.println("5. View Appointments");
            System.out.println("6. Record Appointments Outcome");
            System.out.println("7. Exit");

            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    // viewPatientRecords(sc);
                    viewAppointments(sc);
                    break;
                case 2:
                    // updatePatientRecords(sc);
                    updatePatientRecords(sc);
                    break;
                case 3:
                    // viewSchedule(sc);
                    viewAppointments(sc);
                    break;
                case 4:
                    // setSchedule(sc);
                    setSchedule(sc);
                    break;
                case 5:
                    // viewAppointments(sc);
                    viewAppointments(sc);
                    break;
                case 6:
                    // recordAppointmentsOutcome(sc);
                    recordAppointmentsOutcome(sc);
                    break;
                default:
                    break;
            }
        } while (choice < 7);
    }

    public void viewPatientRecords(Scanner sc) {
        System.out.println("View Patient Records");
    }

    public void updatePatientRecords(Scanner sc) {
        System.out.println("Update Patient Records");
    }

    public void viewSchedule(Scanner sc) {
        System.out.println("View Schedule");
    }

    public void setSchedule(Scanner sc) {
        System.out.println("Set Schedule");
    }

    public void viewAppointments(Scanner sc) {
        System.out.println("View Appointments");
    }

    public void recordAppointmentsOutcome(Scanner sc) {
        System.out.println("Record Appointments Outcome");
    }
}
