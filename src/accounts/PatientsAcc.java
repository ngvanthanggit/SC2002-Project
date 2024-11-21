package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.SystemInitialisation;
import user.*;
import utility.*;

/**
 * This class is responsible for managing patient accounts.
 * <p>
 * This includes loading data from CSV files, displaying, adding, updating,
 * finding, and removing patients,
 * as well as managing password updates. The class interacts with utility
 * classes like {@code CSVread},
 * {@code CSVwrite}, and {@code CSVclear} to handle file operations.
 */
public class PatientsAcc {

     /** A list to store all {@link Patient} objects. */
    private static List<Patient> patients = new ArrayList<>();
    /** The file path to the original patient CSV file. */
    private static String originalPath;
    /** The file path to the updated patient CSV file. */
    private static String updatedPath;

    /**
     * Updates the file paths for loading and saving patient data by retrieving them from 
     * the {@link SystemInitialisation} class. 
     * <p>
     * This method centralizes the file path management, ensuring that the file paths 
     * are dynamically retrieved rather than hardcoded, improving maintainability and flexibility.
     * <p>
     * File paths updated:
     * <ul>
     *   <li><b>originalPath</b>: Path to the original CSV file containing admin data.</li>
     *   <li><b>updatedPath</b>: Path to the updated CSV file for saving admin data.</li>
     * </ul>
     * 
     * @see SystemInitialisation#getFilePath(String)
     */
    public static void setFilePaths() {
        originalPath = SystemInitialisation.getFilePath("PatientsOriginal");
        updatedPath = SystemInitialisation.getFilePath("PatientsUpdated");
    }

    /**
     * Loads patient accounts from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the
     * updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first
     *                   time;
     *                   {@code false} otherwise.
     */
    public static void loadPatients(boolean isFirstRun) {
        // Load data from the file
        String filePath = isFirstRun ? originalPath : updatedPath;

        // clear the list to avoid having duplicate data
        patients.clear();

        Map<String, Integer> patientMapping = new HashMap<>();
        patientMapping.put("hospitalID", 0);
        patientMapping.put("name", 1);
        patientMapping.put("role", 2);
        patientMapping.put("gender", 3);
        patientMapping.put("age", 4);
        patientMapping.put("password", 5);
        patientMapping.put("dateOB", 6);
        patientMapping.put("bloodType", 7);
        patientMapping.put("contactInfo", 8);

        List<Object> patientMapList = CSVread.readCSV(filePath, patientMapping, "Patient");

        // add the data from CSV into patientsList
        for (Object user : patientMapList) {
            if (user instanceof Patient) {
                patients.add((Patient) user);
            }
        }

        if (patients.isEmpty()) {
            System.out.println("No patients were loaded.");
        } else {
            System.out.println("Patients successfully loaded: " + patients.size());
        }
    }

    /**
     * Returns a copy of the list of all patients.
     * 
     * @return A list of {@link User} objects representing patients.
     */
    public static List<User> getPatients() {
        return new ArrayList<>(patients);
    }

    /** Displays all patients currently in the list. */
    public static void displayPatients() {
        System.out.println("\nThe Patient in the CSV file are: ");
        for (User patient : patients) {
            System.out.println(patient.userInfo());
        }
    }

    /** Duplicates the current patient list to the updated CSV file. */
    public static void duplicatePatient() {
        CSVwrite.writeCSVList(updatedPath, patients);
    }

    /**
     * Finds a patient by their hospital ID.
     * 
     * @param hospitalID The hospital ID of the patient to find.
     * @return The {@link Patient} object if found; {@code null} otherwise.
     */
    public static Patient findPatientById(String hospitalID) {
        for (Patient patient : patients) {
            if (patient.getHospitalID().equals(hospitalID)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Gets patient name by their hospital ID.
     * 
     * @param hospitalID The hospital ID of the patient to get name of.
     */
    public String getPatientName(String hospitalID) {
        for (User patient : patients) {
            if (patient.getHospitalID().equals(hospitalID)) {
                return (patient != null) ? patient.getName() : "Patient not found";
            }
        }
        return null;
    }

    /**
     * Adds a new patient to the list and saves the updated list to the CSV file.
     */
    public static void addPatient(Scanner sc) {
        System.out.println("\n|---- Creating New Patient Account ----|");
        System.out.printf("%s\n", "-".repeat(40));
        Patient newCreatedUser = NewAccount.createNewAccount(sc, patients, Role.Patient);

        if (newCreatedUser != null) {
            patients.add(newCreatedUser);
            CSVwrite.writeCSV(updatedPath, newCreatedUser);
            System.out.println("Patient " + newCreatedUser.getName() + " created!");
        } else {
            System.out.println("Account creation failed!");
        }
    }

    /**
     * Updates a patient's details based on their hospital ID.
     * <p>
     * Prompts the user to enter updated details for the patient.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public static void updatePatient(Scanner sc, Patient patient) {
        Patient patientToUpdate;
        String hospitalID;
        String setEmail;
        // no patient object passed, from admin
        if (patient == null) {
            displayPatients();
            System.out.print("Enter the Patient ID to update: ");
            hospitalID = sc.nextLine();
            patientToUpdate = findPatientById(hospitalID);
        } else {
            // else use the patient object passed from patient updating themselves
            hospitalID = patient.getHospitalID();
            patientToUpdate = patient;
        }

        if (patientToUpdate != null) {
            // catch any invalid types, date format and age
            try {
                System.out.print("Enter your Contact Info: ");
                setEmail = sc.nextLine();
                if (!setEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    System.out.println("Invalid email format. Please provide a valid email address.");
                    return;
                }
                patientToUpdate.setEmail(setEmail);
                System.out.println("Patient " + patientToUpdate.getName() + "'s details has been updated.");
                duplicatePatient(); // rewrite the CSV file with updated version
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type detected. Please enter the correct type for each field.");
                sc.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Pharmacist with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Removes a patient from the list based on their hospital ID.
     * <p>
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public static void removePatient(Scanner sc) {
        System.out.print("Enter the Patient ID to remove: ");
        String hospitalID = sc.nextLine();
        Patient patientToRemove = findPatientById(hospitalID);

        if (patientToRemove != null) {
            patients.remove(patientToRemove); // remove Data from pharmacist List
            System.out.println("Patient with Hospital ID " + hospitalID + " has been removed.");
            displayPatients(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Patient with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Updates the password of a patient based on their hospital ID.
     * 
     * @param hospitalID  The hospital ID of the patient whose password is to be
     *                    updated.
     * @param newPassword The new password to set for the patient.
     */
    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        User patientPWToUpdate = findPatientById(hospitalID);

        if (patientPWToUpdate != null) {
            patientPWToUpdate.setPassword(newPassword);
            duplicatePatient();
            return;
        }
    }
}