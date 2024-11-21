package userInterface;

import java.util.Scanner;

import interfaces.ApptOutcomeInterface;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;

/**
 * The class implements {@link ApptOutcomeInterface} to provide a UI for managing and updating appointment outcomes.
 * This class allows the user to view all appointment outcomes, 
 * and update the medication status of a medical record.
 */
public class ApptOutcomeUI implements ApptOutcomeInterface {

    /**
     * Displays all appointment outcomes (medical records).
     * Calls the MedicalRecordManager to display all the medical records.
     */
    public void viewAllApptOutcomes(){
        MedicalRecordManager.displayMedicalRecords();
    }

    /**
     * Allows the user to update the medication status of a medical record.
     * Displays the current pending prescriptions and prompts the user to select a medical record 
     * to update its status to "dispensed".
     * 
     * @param sc A {@link Scanner} object to read user input.
     */
    public void updateApptMedication(Scanner sc){
        System.out.println("\nCurrent Pending Prescriptions: ");
        MedicalRecordManager.displayPendingMR();
        MedicalRecord medicalRecord = selectMRhelper(sc);
        if(medicalRecord!=null){
            //update status to dispensed
            MedicalRecordManager.updateMRStatus(medicalRecord);
        }
    }

    /**
     * Helper function to select a medical record by its ID.
     * 
     * @param sc A {@link Scanner} object to read user input.
     * @return The selected {@link MedicalRecord} object if found, otherwise {@code null}.
     */
    public MedicalRecord selectMRhelper(Scanner sc){
        System.out.print("Enter MedicalR ID to update Prescription Status: ");
        MedicalRecord medicalRecord = MedicalRecordManager.findMedicalRecordbyID(sc.nextLine().trim());
    
        if(medicalRecord!=null){
            return medicalRecord;
        } else {
            System.out.println("Medical Record not found.");
            return null;
        }
    }
}
