package user;

import java.util.Scanner;

import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import menus.DoctorMenu;

public class Doctor extends User implements DoctorMenu {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    @Override
    public void logout() {
        System.out.println("Doctor Logging Out.");
        return;
    }

    public Doctor(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
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
                    viewPatientRecords();
                    break;
                case 2:
                    // updatePatientRecords();
                    updatePatientRecords();
                    break;
                case 3:
                    // viewhedule();
                    viewSchedule();
                    break;
                case 4:
                    // sethedule();
                    setSchedule();
                    break;
                case 5:
                    // viewAppointments();
                    viewAppointments();
                    break;
                case 6:
                    // recordAppointmentsOutcome();
                    recordAppointmentOutcome();
                    break;
                default:
                    break;
            }
        } while (choice > 0 && choice < 7);
    }

    public void viewPatientRecords() {
        System.out.println("View Patient Records");
        System.out.println("Enter Patient ID: ");
        Scanner sc = new Scanner(System.in);
        String patientID = sc.nextLine();
        MedicalRecord record = MedicalRecordManager.getMedicalRecord(this.getHospitalID(), patientID);
        if (record == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }
        System.out.println(record.getRecordDetails());
    }

    public void updatePatientRecords() {
        System.out.println("Update Patient Records");
        System.out.println("Enter Patient ID: ");
        Scanner sc = new Scanner(System.in);
        String patientID = sc.nextLine();
        MedicalRecord record = MedicalRecordManager.getMedicalRecord(this.getHospitalID(), patientID);
        if (record == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }

        boolean updated = false;
        while (!updated) {
            System.out.println("What would you like to update?");
            System.out.println("1. Diagnoses");
            System.out.println("2. Prescriptions");
            System.out.println("3. Treatment Plan");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.println("Do you want to add or clear it?");
            System.out.println("1. Add");
            System.out.println("2. Clear");
            System.out.print("Choice: ");
            int choice2 = sc.nextInt();

            if (choice2 < 1 || choice2 > 2) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (choice2 == 1) {
                        System.out.println("Enter new diagnoses: ");
                        sc.nextLine();
                        String newDiagnose = sc.nextLine();
                        record.addDiagnose(newDiagnose);
                    } else if (choice2 == 2) {
                        record.clearDiagnoses();
                    }
                    break;
                case 2:
                    if (choice2 == 1) {
                        System.out.println("Enter new prescription: ");
                        sc.nextLine();
                        String newPrescription = sc.nextLine();
                        record.addPrescription(newPrescription);
                    } else if (choice2 == 2) {
                        record.clearPrescriptions();
                    }
                    break;
                case 3:
                    if (choice2 == 1) {
                        System.out.println("Enter new treatment plan: ");
                        sc.nextLine();
                        String newTreatmentPlan = sc.nextLine();
                        record.addTreatmentPlan(newTreatmentPlan);
                    } else if (choice2 == 2) {
                        record.clearTreatmentPlans();
                    }
                    break;
                default:
                    break;
            }
            updated = true;
        }
        MedicalRecordManager.duplicateMedicalRecord();
        System.out.println("Record updated successfully.");
    }

    public void viewSchedule() {
        System.out.println("View Schedule");
    }

    public void setSchedule() {
        System.out.println("Set Schedule");
    }

    public void viewAppointments() {
        System.out.println("View Appointments");
    }

    public void updateAppointmentRequest() {
        System.out.println("Update Appointment Request");
    }

    public void recordAppointmentOutcome() {
        System.out.println("Record Appointments Outcome");
    }

    public void acceptAppointmentRequest() {
        System.out.println("Accept Appointment Request");
    }

    public void declineAppointmentRequest() {
        System.out.println("Decline Appointment Request");
    }
}
