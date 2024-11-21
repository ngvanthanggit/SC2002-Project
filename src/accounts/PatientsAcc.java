package accounts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import user.*;
import utility.*;

/**
 * This class is responsible for managing patient accounts.
 * <p>
 * This includes loading data from CSV files, displaying, adding, updating, finding, and removing patients, 
 * as well as managing password updates. The class interacts with utility classes like {@code CSVread}, 
 * {@code CSVwrite}, and {@code CSVclear} to handle file operations.
 */
public class PatientsAcc {

    // store the list of patients
    private static List<Patient> patients = new ArrayList<>();
    private static String originalPath = "Data//Original/Patient_List.csv";
    private static String updatedPath = "Data//Updated/Patient_List(Updated).csv";

    /**
     * Loads patient accounts from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
    public static void loadPatients(boolean isFirstRun) {

        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

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

    /** Adds a new patient to the list and saves the updated list to the CSV file. */
    public static void addPatient(Scanner sc) {
        System.out.println("\n|---- Creating New Patient Account ----|");
        System.out.printf("%s\n", "-".repeat(40));
        Patient newCreatedUser = NewAccount.createNewAccount(sc, patients, Role.Patient);

        if(newCreatedUser!=null){
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
     * @param sc A {@link Scanner} object for user input.
     */
    public static void updatePatient(Scanner sc, Patient patient){
        String name, gender, password, bloodType;
        Patient patientToUpdate;
        String hospitalID;
        //no patient object passed, from admin
        if(patient==null){
            displayPatients();
            System.out.print("Enter the Patient ID to update: ");
            hospitalID = sc.nextLine();
            patientToUpdate = findPatientById(hospitalID);
        } else {
            // else use the patient object passed from patient updating themselves
            hospitalID = patient.getHospitalID();
            patientToUpdate = patient;
        }
        
        if(patientToUpdate!=null){
            //catch any invalid types, date format and age
            try {
                System.out.print("\nEnter your Name: ");
                name = sc.nextLine().trim();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                patientToUpdate.setName(name);

                System.out.print("Enter your Gender: ");
                gender = sc.nextLine().trim();
                gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
                patientToUpdate.setGender(gender);

                System.out.print("Enter your Age: ");
                patientToUpdate.setAge(sc.nextInt());
                sc.nextLine(); //consume
                System.out.print("Enter your Password: ");
                password = sc.nextLine();
    
                // Validate Date of Birth
                String DOB = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false); // Strict validation
                while (DOB == null) {
                    System.out.print("Enter your Date of Birth (DD/MM/YYYY): ");
                    String inputDOB = sc.nextLine().trim();
                    try {
                        dateFormat.parse(inputDOB);
                        DOB = inputDOB; // Valid date
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use DD/MM/YYYY.\n");
                    }
                }
                patientToUpdate.setDateOB(DOB);
                System.out.print("Enter your Blood Type: ");
                bloodType = sc.nextLine();
                bloodType = bloodType.substring(0, 1).toUpperCase() + bloodType.substring(1);
                patientToUpdate.setBloodType(bloodType);
                System.out.print("Enter your Contact Info @gmail: ");
                patientToUpdate.setEmail(sc.nextLine());
                System.out.println("Patient " + patientToUpdate.getName() + "'s details has been updated.");
                updatePassword(patientToUpdate.getHospitalID(), password);
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
     * @param hospitalID The hospital ID of the patient whose password is to be updated.
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