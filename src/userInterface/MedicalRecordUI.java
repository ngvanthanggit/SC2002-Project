package userInterface;

import java.util.List;
import java.util.Scanner;

import interfaces.MedicalRecInterface;
import user.Doctor;
import user.Patient;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;

/**
 * The class implements implements {@link MedicalRecInterface} to provide a UI
 * for managing medical records.
 * It allows a doctor to view, update, and manage patient medical records
 * through various interactions.
 */
public class MedicalRecordUI implements MedicalRecInterface {

    /**
     * Default constructor for MedicalRecordUI.
     */
    public MedicalRecordUI() {
    }

    /**
     * Displays the patient's medical records.
     * 
     * @param patient The {@link Patient} whose records are to be viewed.
     */
    public void viewPatientMedicalRecords(Patient patient) {
        System.out.println("\n|-- Viewing Medical Records--|");
        System.out.printf("%s\n", "-".repeat(30));
        List<MedicalRecord> medicalRecords = MedicalRecordManager.getMedicalRecordsByPatient(patient.getHospitalID());
        if (medicalRecords.isEmpty()) {
            System.out.println("You have no medical records found.");
        } else {
            for (MedicalRecord record : medicalRecords) {
                System.out.println(record.getRecordDetails());
            }
        }
    }

    /**
     * Allows the doctor to choose a patient's medical record.
     * 
     * @param sc     A {@link Scanner} object for user input.
     * @param doctor The doctor attempting to access the medical record.
     * @return A list of medical records for the selected patient.
     */
    public List<MedicalRecord> choosePatient(Scanner sc, Doctor doctor) {
        System.out.println("Enter Patient ID of the Medical Records you would like manage");
        System.out.print("Enter Patient ID: ");
        String patientID = sc.nextLine();
        List<MedicalRecord> records = MedicalRecordManager.getMedicalRecords(doctor.getHospitalID(), patientID);

        if (records == null) {
            System.out.println("No medical record found for this patient.");
            return null;
        } else {
            return records;
        }
    }

    /**
     * Displays the details of a patient's medical record.
     * 
     * @param record The medical record of the patient to view.
     */
    public void viewPatientRecords(MedicalRecord record) {
        if (record == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }
        System.out.println(record.getRecordDetails());
    }

    /**
     * Allows the doctor to update a patient's medical record with diagnoses,
     * prescriptions, or treatment plans.
     * 
     * @param sc      A {@link Scanner} object for user input.
     * @param records The list of medical records to update.
     */
    public void updatePatientRecords(Scanner sc, List<MedicalRecord> records) {
        if (records == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }

        MedicalRecord record = null;

        while (true) {
            // Enter the recordID to update
            System.out.println("Enter the Medical Record ID you would like to update: ");
            String recordID = sc.nextLine();
            record = MedicalRecordManager.findMedicalRecordbyID(recordID);
            if (record == null) {
                System.out.println("No medical record found with the ID.");
                System.out.println("Would you like to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice: ");
                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine().trim()); // Use nextLine and parse input
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Please enter a number.");
                    continue;
                }

                if (choice == 2) {
                    return;
                }
                continue;
            }
            break;
        }

        boolean updated = false;
        while (!updated) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Diagnoses");
            System.out.println("2. Prescriptions");
            System.out.println("3. Treatment Plan");
            System.out.print("Choice: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim()); // Use nextLine and parse input
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.println("\nDo you want to add or clear it?");
            System.out.println("1. Add");
            System.out.println("2. Clear");
            System.out.print("Choice: ");
            int choice2;
            try {
                choice2 = Integer.parseInt(sc.nextLine().trim()); // Use nextLine and parse input
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            if (choice2 < 1 || choice2 > 2) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (choice2 == 1) {
                        System.out.println("Enter new diagnoses (separated by ';'): ");
                        String newDiagnose = sc.nextLine();
                        record.addDiagnose(newDiagnose);
                    } else if (choice2 == 2) {
                        record.clearDiagnoses();
                    }
                    updated = true;
                    break;
                case 2:
                    if (choice2 == 1) {
                        System.out.println("Enter new prescription in the format (Medication: Quantity) ");
                        System.out.print("Enter prescription: ");
                        String newPrescription = sc.nextLine().trim();

                        try {
                            // Add the prescription, validation happens inside addPrescription
                            record.addPrescription(newPrescription);
                            updated = true;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Failed to add prescription: " + e.getMessage());
                        }
                    } else if (choice2 == 2) {
                        record.clearPrescriptions();
                        updated = true;
                    }
                    break;
                case 3:
                    if (choice2 == 1) {
                        System.out.println("Enter new treatment plan (separated by ';'): ");
                        String newTreatmentPlan = sc.nextLine();
                        record.addTreatmentPlan(newTreatmentPlan);
                    } else if (choice2 == 2) {
                        record.clearTreatmentPlans();
                    }
                    updated = true;
                    break;
                default:
                    break;
            }
        }
        MedicalRecordManager.duplicateMedicalRecord();
        System.out.println("Record updated successfully.");
    }
}
