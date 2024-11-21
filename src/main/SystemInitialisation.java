//this class will be called when our program starts
//main purpose is to handle the imported data
package main;

import accounts.PatientsAcc;
import accounts.PharmacistsAcc;
//import appointmentManager.ApptManager;
import inventory.InventoryManager;
import inventory.ReplenishManager;
import accounts.AdminsAcc;
import accounts.DoctorsAcc;
import medicalrecord.MedicalRecordManager;
import schedule.ScheduleManager;
import appointment.AppointmentManager;

/**
 * This class is responsible for initializing the system during the first run of the program.
 * It loads necessary data from CSV files, saves the data into new files for updating, and prepares all necessary
 * components for the application to function.
 */
public class SystemInitialisation {

    /**
     * A flag indicating whether the system is running for the first time.
     * It is used to control whether to load original data or updated data.
     */
    private static boolean isFirstRun = true;

    /**
     * Starts the system initialization process. It loads the necessary data from CSV files, stores it in
     * their respective lists, and prepares the system for use. Additionally, it ensures that the original data
     * is saved into a new file for future updates. It simplifies the methods needed during the first boot up of the system into one method.
     */
    public static void start() {

        // read and store all data from CSV into their respective Lists
        AdminsAcc.loadAdmins(isFirstRun);
        PatientsAcc.loadPatients(isFirstRun);
        PharmacistsAcc.loadPharmacists(isFirstRun);
        DoctorsAcc.loadDoctors(isFirstRun);
        InventoryManager.loadInventory(isFirstRun);
        ReplenishManager.loadReplenish(isFirstRun);
        MedicalRecordManager.loadMedicalRecords(isFirstRun);
        ScheduleManager.loadSchedules(isFirstRun);
        AppointmentManager.loadAppointments(isFirstRun);
        // ApptManager.loadAppointments(isFirstRun);

        // save the original data into a new file to be updated
        AdminsAcc.duplicateAdmin();
        PatientsAcc.duplicatePatient();
        PharmacistsAcc.duplicatePharmacist();
        DoctorsAcc.duplicateDoctor();
        InventoryManager.duplicateInventory();
        ReplenishManager.duplicateReplenish();
        MedicalRecordManager.duplicateMedicalRecord();
        ScheduleManager.duplicateSchedule();
        AppointmentManager.duplicateAppointments();
        // ApptManager.duplicateAppointments();

        // Displaying the List of Patients and Staffs from CSV
        /*
         * AdminsAcc.displayAdmins();
         * PatientsAcc.displayPatients();
         * PharmacistsAcc.displayPharmacists();
         * DoctorsAcc.displayDoctors();
         * InventoryManager.displayInventory();
         * ReplenishManager.displayReplenishList();
         * MedicalRecordManager.displayMedicalRecords();
         * ScheduleManager.displaySchedules();
         * AppointmentManager.displayAppointments();
         */

        // ApptManager.displayAppointments();

        // set to false after first load
        isFirstRun = false;
    }

}
