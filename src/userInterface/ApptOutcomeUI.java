package userInterface;

import java.util.Scanner;
import interfaces.ApptOutcomeInterface;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;

public class ApptOutcomeUI implements ApptOutcomeInterface {

    //display all outcomes
    public void viewAllApptOutcomes(){
        MedicalRecordManager.displayMedicalRecords();
    }

    public void updateApptMedication(Scanner sc){
        System.out.println("\nCurrent Pending Prescriptions: ");
        MedicalRecordManager.displayPendingMR();
        MedicalRecord medicalRecord = selectMRhelper(sc);
        if(medicalRecord!=null){
            //update status to dispensed
            MedicalRecordManager.updateMRStatus(medicalRecord);
        }
    }

    //helper function for selecting patient
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
