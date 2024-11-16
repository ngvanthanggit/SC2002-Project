//this class will be called when our program starts
//main purpose is to handle the imported data
package main;

import accounts.PatientsAcc;
import accounts.PharmacistsAcc;
import accounts.AdminsAcc;
import accounts.DoctorsAcc;
import inventory.InventoryManager;
import inventory.ReplenishManager;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;

public class SystemInitialisation {
    private static boolean isFirstRun = true;

    // simiplifies all methods needed when 1st boot up of system under 1 method
    public static void start() {

        // read and store all data from CSV into their respective Lists
        AdminsAcc.loadAdmins(isFirstRun);
        PatientsAcc.loadPatients(isFirstRun);
        PharmacistsAcc.loadPharmacists(isFirstRun);
        DoctorsAcc.loadDoctors(isFirstRun);
        InventoryManager.loadInventory(isFirstRun);
        ReplenishManager.loadReplenish(isFirstRun);
        MedicalRecordManager.loadMedicalRecords(isFirstRun);

        // save the original data into a new file to be updated
        AdminsAcc.duplicateAdmin();
        PatientsAcc.duplicatePatient();
        PharmacistsAcc.duplicatePharmacist();
        DoctorsAcc.duplicateDoctor();
        InventoryManager.duplicateInventory();
        ReplenishManager.duplicateReplenish();
        MedicalRecordManager.duplicateMedicalRecord();

        // Displaying the List of Patients and Staffs from CSV
        AdminsAcc.displayAdmins();
        PatientsAcc.displayPatients();
        PharmacistsAcc.displayPharmacists();
        DoctorsAcc.displayDoctors();
        InventoryManager.displayInventory();
        ReplenishManager.displayReplenishList();
        MedicalRecordManager.displayMedicalRecords();

        // set to false after first load
        isFirstRun = false;
    }

}
