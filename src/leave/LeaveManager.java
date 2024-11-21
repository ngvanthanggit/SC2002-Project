package leave;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import utility.CSVread;
import utility.CSVwrite;
import utility.IDGenerator;
import user.Doctor;
import accounts.DoctorsAcc;
import appointment.*;
import main.SystemInitialisation;
import schedule.ScheduleManager;

public class LeaveManager {
    /** A list to store all {@link Leave} objects. */
    private static List<Leave> leaves = new ArrayList<>();

    /** The file path to the original leave CSV file. */
    private static String originalPath;

    /** The file path to the updated leave CSV file. */
    private static String updatedPath;

    /**
     * Updates the file paths for loading and saving leave request data by retrieving them from 
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
        originalPath = SystemInitialisation.getFilePath("LeaveOriginal");
        updatedPath = SystemInitialisation.getFilePath("LeaveUpdated");
    }

    /**
     * Loads leave requests from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
    public static void loadLeaves(boolean isFirstRun) {
        // Load data from the file
        String filePath = isFirstRun ? originalPath : updatedPath;

        // clear the list to avoid having duplicate data
        leaves.clear();

        Map<String, Integer> leaveMapping = new HashMap<>();
        leaveMapping.put("leaveID", 0);
        leaveMapping.put("staffID", 1);
        leaveMapping.put("date", 2);
        leaveMapping.put("status", 3);
        leaveMapping.put("reason", 4);

        List<Leave> leaveMapList = CSVread.readLeaveCSV(filePath, leaveMapping);

        // add the data from CSV into staffsList
        for (Object leave : leaveMapList) {
            if (leave instanceof Leave) {
                leaves.add((Leave) leave);
            }
        }

        if (leaves.isEmpty()) {
            System.out.println("No leave were loaded.");
        } else {
            System.out.println("Leave successfully loaded: " + leaves.size());
        }
    }

    /**
     * Retrieves all leave requests currently in the system.
     * 
     * @return A {@link List} of {@link Leave} objects.
     */
    public static List<Leave> getLeaves(){
        return leaves;
    }

    /**
     * Displays all leave requests in the system.
     * <p>
     * If there are no leave requests, a message is displayed.
     */
    public static void displayLeaves(){
        if (leaves.isEmpty()) {
            System.out.println("There are currently no leaves.");
        } else {
            System.out.println("\nThe Leaves in the CSV file are: ");
                for(Leave leave: leaves){
                    System.out.println(leave.leaveInfo());
                }
        }
    }

    /**
     * Displays all leave requests for a specific doctor.
     * 
     * @param doctor The {@link Doctor} whose leave requests are to be displayed.
     */
    public static void displayDocLeave(Doctor doctor){
        Doctor doctorFound = DoctorsAcc.findDoctorById(doctor.getHospitalID());
        boolean leaveFound = false;
        if(doctorFound!=null){
            //display Leave requests with corresponding doctor IDs
            for(Leave leave : leaves){
                //display all leaves that has the same doctorID
                if(leave.getStaff().getHospitalID().equals(doctorFound.getHospitalID())){
                    System.out.println(leave.leaveInfo());
                    leaveFound = true;
                }
            }
            if(leaveFound == false){
                System.out.println("Doctor " + doctor.getName() + ", you currently have no leave requests");
            }
        } else {
            System.out.println("Doctor not found");
        }
    }

    /**
     * Displays all pending leave requests for a specific doctor.
     * 
     * @param doctor The {@link Doctor} whose pending leave requests are to be displayed.
     */
    public static void displayPendingLeave(Doctor doctor){
        Doctor doctorFound = DoctorsAcc.findDoctorById(doctor.getHospitalID());
        boolean leaveFound = false;

        if(doctorFound!=null){
            for(Leave leave : leaves){
                //display all leaves that has the same doctorID && Status PENDING
                if(leave.getStaff().getHospitalID().equals(doctorFound.getHospitalID())
                    && leave.getLeaveStatus() == LeaveStatus.PENDING){
                    System.out.println(leave.leaveInfo());
                    leaveFound = true;
                }
            }

            if(leaveFound == false){
                System.out.println("Doctor " + doctor.getName() + ", you currently have no pending leave requests");
            }

        } else {
            System.out.println("Doctor not found");
        }
    } 

    /**
     * Displays the outcome (approved or rejected) of leave requests for a specific doctor.
     * 
     * @param doctor The {@link Doctor} whose leave request outcomes are to be displayed.
     */
    public static void displayLeaveOutcome(Doctor doctor){
        Doctor doctorFound = DoctorsAcc.findDoctorById(doctor.getHospitalID());
        boolean leaveFound = false;

        if(doctorFound!=null){
            for(Leave leave : leaves){
                //display all leaves that has the same doctorID && Status APPROVED||REJECTED
                if(leave.getStaff().getHospitalID().equals(doctorFound.getHospitalID())
                    && leave.getLeaveStatus() != LeaveStatus.PENDING){
                    System.out.println(leave.leaveInfo());
                    leaveFound = true;
                }
            }
            if(leaveFound == false){
                System.out.println("Doctor " + doctor.getName() + ", you currently have no Approved/Rejected leave requests");
            }

        } else {
            System.out.println("Doctor not found");
        }
    }

    /** Saves the current list of leave requests to the updated CSV file. */
    public static void duplicateLeave(){
        CSVwrite.writeCSVList(updatedPath, leaves);
    }

    /**
     * Finds a leave request by its ID.
     * 
     * @param leaveID The ID of the leave request to find.
     * @return The {@link Leave} object if found, otherwise {@code null}.
     */
    public static Leave findLeaveByID(String leaveID){
        for(Leave leave : leaves){
            if(leave.getLeaveID().equals(leaveID)){
                return leave;
            }
        }
        return null;
    }

    /**
     * Adds a new leave request to the system.
     * <p>
     * A new leave ID is generated, and the leave is saved to both the list and the updated file.
     * 
     * @param leave The {@link Leave} object to add.
     */
    public static void addLeave(Leave leave){
        //generate newID for leave
        String leaveID = IDGenerator.generateID("LR", LeaveManager.getLeaves(), Leave::getLeaveID, 3); 

        leave.setLeaveID(leaveID);
        leaves.add(leave); //add new entry to list
        CSVwrite.writeCSV(updatedPath, leave); //add new entry to database
    }

    /**
     * Updates a leave request for a specific doctor.
     * <p>
     * The leave request must be pending and must belong to the doctor making the update.
     * 
     * @param sc      A {@link Scanner} object for user input.
     * @param leaveID The ID of the leave request to update.
     * @param doctor  The {@link Doctor} making the update.
     */
    public static void updateLeave(Scanner sc, String leaveID, Doctor doctor){
        Leave leave = findLeaveByID(leaveID);

        if(leave==null){
            System.out.println("The leave request with ID " + leaveID + " does not exist.");
            return;
        }

        //request found but does not belong to logged in doctor
        if(!leave.getStaff().getHospitalID().equals(doctor.getHospitalID())){
            System.out.println("The leave request with ID " + leaveID + " was not requested by you. You can't modify it.");
            return;
        }

        if(leave.getLeaveStatus()!= LeaveStatus.PENDING){
            System.out.println("The leave request with ID " + leaveID + " has been " + leave.getLeaveStatus() + ". You can't modify it.");
            return;
        }

        String dateStr;
        LocalDate date;
        //enter Date
        while(true){
            try {
                System.out.print("\nUpdate the date of leave (yyyy-mm-dd): ");
                dateStr = sc.nextLine().trim();
                date = LocalDate.parse(dateStr); // Try parsing the date
                    
                // Check if the date is in the future
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Invalid date. Please enter a future date.");
                    continue; // Prompt user to enter the date again
                }
                break; // Exit loop if date is valid
            } catch (Exception e) {
                //catches incorrect date format
                System.out.println("Invalid date format. Please enter the date in the format yyyy-mm-dd.");
            }
        }
        System.out.print("Update your reason for applying leave: ");
        String reason = sc.nextLine();

        //update all leave fields
        leave.setDate(date);
        leave.setLeaveStatus(LeaveStatus.PENDING);
        leave.setReason(reason);
        duplicateLeave();//update database
        System.out.print("Leave Request ID " + leave.getLeaveID() + " has been updated.");
    }

    /**
     * Removes a leave request from the system for a specific doctor.
     * <p>
     * This method verifies that the leave request exists, belongs to the given doctor, 
     * and then removes it from the system. If the leave request is successfully removed, 
     * the leave data is updated in the storage.
     * 
     * @param leaveID The ID of the leave request to remove.
     * @param doctor  The {@link Doctor} attempting to remove the leave request.
     * 
     * <p>
     * Conditions:
     * <ul>
     *   <li>If the leave request does not exist, an error message is displayed.</li>
     *   <li>If the leave request was not submitted by the provided doctor, an error message is displayed.</li>
     *   <li>If the leave request is successfully removed, the change is saved to the updated file.</li>
     * </ul>
     */
    public static void removeLeave(String leaveID, Doctor doctor){
        //find leave request with leaveID
        Leave leaveRemove = findLeaveByID(leaveID);

        if(leaveRemove == null){
            System.out.println("The leave request with ID " + leaveID + " does not exist.");
            return;
        }

        if(!leaveRemove.getStaff().getHospitalID().equals(doctor.getHospitalID())){
            System.out.println("The leave request with ID " + leaveID + " was not requested by you. You can't remove it.");
            return;
        }
        
        if(leaveRemove.getLeaveStatus()!=LeaveStatus.PENDING){
            System.out.println("You can't remove a request has been " + leaveRemove.getLeaveStatus() + ". Please get help from an Administrator.");
            return;
        }

        if(leaveRemove!=null){
            leaves.remove(leaveRemove);
            System.out.println("The leave request of ID: " + leaveRemove.getLeaveID() + " has been removed.");
            duplicateLeave();
        } 
    }

    /**
     * Approves a leave request.
     * <p>
     * The status of the leave request is set to "APPROVED". Schedules for the day of the leave
     * are cleared, and any affected appointments are canceled.
     * 
     * @param leaveID The ID of the leave request to approve.
     */
    public static void approveLeave(String leaveID){
        Leave leaveReject = findLeaveByID(leaveID);

        if(leaveReject == null){
            System.out.println("This leave request was not found.");
            return;
        }

        //status must be pending
        if(leaveReject.getLeaveStatus() != LeaveStatus.PENDING){
            System.out.println("This leave request has already been " + leaveReject.getLeaveStatus());
            return;
        }

        //remove schedule from doctor if any, succesful
        ScheduleManager.removeDaySchedule(leaveReject.getDate(), (Doctor) leaveReject.getStaff());

        //update patient appointments to cancelled for, patient appt doesnt get cancelled
        List<Appointment> apptList = AppointmentManager.getAllAppointments();
        for(Appointment appt : apptList){
            if(appt.getDate().equals(leaveReject.getDate())){
                appt.cancelAppointment();
            }
        }
        //save to appointment database
        AppointmentManager.duplicateAppointments();
        
        //leave approved
        leaveReject.setLeaveStatus(LeaveStatus.APPROVED); //set status approved
        System.out.println("Leave Request ID " + leaveID + " has been succesfully approved.");
        duplicateLeave(); //update database
    }

    /**
     * Rejects a leave request.
     * <p>
     * The status of the leave request is set to "REJECTED".
     * 
     * @param leaveID The ID of the leave request to reject.
     */
    public static void rejectLeave(String leaveID){
        //set leave status to rejected
        Leave leaveReject = findLeaveByID(leaveID);

        if(leaveReject == null){
            System.out.println("This leave request was not found.");
            return;
        }

        //status must be pending
        if(leaveReject.getLeaveStatus() != LeaveStatus.PENDING){
            System.out.println("This leave request has already been " + leaveReject.getLeaveStatus());
            return;
        }

        leaveReject.setLeaveStatus(LeaveStatus.REJECTED); //set status rejected
        System.out.println("Leave Request ID " + leaveID + " has been succesfully rejected.");
        duplicateLeave(); //update database
    }
}
