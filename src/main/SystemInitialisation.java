//this class will be called when our program starts
//main purpose is to handle the imported data
package main;

import accounts.PatientsAcc;
import accounts.PharmacistsAcc;
import inventory.InventoryManager;
import inventory.ReplenishManager;
import leave.LeaveManager;

import java.util.HashMap;
import java.util.Map;

import accounts.AdminsAcc;
import accounts.DoctorsAcc;
import medicalrecord.MedicalRecordManager;
import schedule.ScheduleManager;
import appointment.AppointmentManager;

/**
 * This class is responsible for initializing the system during the first run of
 * the program.
 * It loads necessary data from CSV files, saves the data into new files for
 * updating, and prepares all necessary
 * components for the application to function.
 */
public class SystemInitialisation {
    /**
     * A flag indicating whether the system is running for the first time.
     * It is used to control whether to load original data or updated data.
     */
    private static boolean isFirstRun = true;

    /**
     * A centralized map for storing file paths associated with various components
     * in the system.
     * <p>
     * The {@code filePaths} map provides a single source of truth for file path
     * management,
     * reducing hardcoding and enhancing maintainability. Each key-value pair in the
     * map
     * associates a unique identifier (e.g., "AdminsOriginal") with its
     * corresponding file path.
     */
    private static final Map<String, String> filePaths = new HashMap<>();

    /**
     * Static initializer block for populating the {@code filePaths} map with file
     * paths.
     * <p>
     * This block initializes the map with predefined keys and their respective file
     * paths.
     * The keys represent different entities and operations in the system (e.g.,
     * "AdminsOriginal", "DoctorsUpdated").
     * <p>
     * Example:
     * <ul>
     * <li>{@code "AdminsOriginal"} maps to the original administrator list file
     * path.</li>
     * <li>{@code "DoctorsUpdated"} maps to the updated doctor list file path.</li>
     * </ul>
     * 
     * The use of this map ensures centralized and consistent management of file
     * paths across the application.
     */
    static {
        // Administrator Files
        filePaths.put("AdminsOriginal", "../Data//Original/Admin_List.csv");
        filePaths.put("AdminsUpdated", "../Data//Updated/Admin_List(Updated).csv");
        // Appointment Files
        filePaths.put("AppointmentOriginal", "../Data//Original/Appt_List.csv");
        filePaths.put("AppointmentUpdated", "../Data//Updated/Appt_List(Updated).csv");
        // Doctor Files
        filePaths.put("DoctorsOriginal", "../Data//Original/Doctor_List.csv");
        filePaths.put("DoctorsUpdated", "../Data//Updated/Doctor_List(Updated).csv");
        // Doctor Schedule Files
        filePaths.put("ScheduleOriginal", "../Data//Original/Schedule_List.csv");
        filePaths.put("ScheduleUpdated", "../Data//Updated/Schedule_List(Updated).csv");
        // Inventory Files
        filePaths.put("InventoryOriginal", "../Data//Original/Medicine_List.csv");
        filePaths.put("InventoryUpdated", "../Data//Updated/Medicine_List(Updated).csv");
        // Leave Requests Files
        filePaths.put("LeaveOriginal", "../Data//Original/Leave_List.csv");
        filePaths.put("LeaveUpdated", "../Data//Updated/Leave_List(Updated).csv");
        // Medical Record Files
        filePaths.put("MedicalRecordOriginal", "../Data//Original/MedicalRecord_List.csv");
        filePaths.put("MedicalRecordUpdated", "../Data//Updated/MedicalRecord_List(Updated).csv");
        // Pateient Files
        filePaths.put("PatientsOriginal", "../Data//Original/Patient_List.csv");
        filePaths.put("PatientsUpdated", "../Data//Updated/Patient_List(Updated).csv");
        // Pharmacist Files
        filePaths.put("PharmacistOriginal", "../Data//Original/Pharm_List.csv");
        filePaths.put("PharmacistUpdated", "../Data//Updated/Pharm_List(Updated).csv");
        // Replenish Requests Files
        filePaths.put("ReplenishOriginal", "../Data//Original/Replenish_List.csv");
        filePaths.put("ReplenishUpdated", "../Data//Updated/Replenish_List(Updated).csv");
    }

    /**
     * Retrieves the file path associated with the specified key.
     * <p>
     * This method allows for dynamic retrieval of file paths stored in the
     * {@code filePaths} map. It helps centralize file path management by
     * using keys to access specific file paths, reducing hardcoding and
     * improving maintainability.
     * 
     * @param key The key representing the desired file path (e.g.,
     *            "AdminsOriginal", "AdminsUpdated").
     * @return The file path associated with the provided key, or {@code null} if
     *         the key does not exist.
     */
    public static String getFilePath(String key) {
        return filePaths.get(key);
    }

    /**
     * Starts the system initialization process. It loads the necessary data from
     * CSV files, stores it in
     * their respective lists, and prepares the system for use. Additionally, it
     * ensures that the original data
     * is saved into a new file for future updates. It simplifies the methods needed
     * during the first boot up of the system into one method.
     */
    public static void start() {

        // Set file paths for all classes
        AdminsAcc.setFilePaths();
        DoctorsAcc.setFilePaths();
        PatientsAcc.setFilePaths();
        PharmacistsAcc.setFilePaths();
        AppointmentManager.setFilePaths();
        InventoryManager.setFilePaths();
        LeaveManager.setFilePaths();
        MedicalRecordManager.setFilePaths();
        ReplenishManager.setFilePaths();
        ScheduleManager.setFilePaths();

        // read and store all data from CSV into their respective Lists
        AdminsAcc.loadAdmins(isFirstRun);
        DoctorsAcc.loadDoctors(isFirstRun);
        PatientsAcc.loadPatients(isFirstRun);
        PharmacistsAcc.loadPharmacists(isFirstRun);
        AppointmentManager.loadAppointments(isFirstRun);
        InventoryManager.loadInventory(isFirstRun);
        LeaveManager.loadLeaves(isFirstRun);
        MedicalRecordManager.loadMedicalRecords(isFirstRun);
        ReplenishManager.loadReplenish(isFirstRun);
        ScheduleManager.loadSchedules(isFirstRun);

        // save the original data into a new file to be updated
        AdminsAcc.duplicateAdmin();
        DoctorsAcc.duplicateDoctor();
        PatientsAcc.duplicatePatient();
        PharmacistsAcc.duplicatePharmacist();
        AppointmentManager.duplicateAppointments();
        InventoryManager.duplicateInventory();
        LeaveManager.duplicateLeave();
        MedicalRecordManager.duplicateMedicalRecord();
        ReplenishManager.duplicateReplenish();
        ScheduleManager.duplicateSchedule();

        // set to false after first load
        isFirstRun = false;
    }

}
