package leave;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import utility.CSVclear;
import utility.CSVread;
import utility.CSVwrite;
import utility.IDGenerator;
import user.Doctor;
import accounts.DoctorsAcc;
import appointment.*;
import schedule.ScheduleManager;

public class LeaveManager {
    /** A list to store all {@link Leave} objects. */
    private static List<Leave> leaves = new ArrayList<>();

    /** The file path to the original leave CSV file. */
    private static String originalPath = "Data//Original/Leave_List.csv";

    /** The file path to the updated leave CSV file. */
    private static String updatedPath = "Data//Updated/Leave_List(Updated).csv";

    public static void loadLeaves(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

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

    public static List<Leave> getLeaves(){
        return leaves;
    }

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
            if(leaveFound = false){
                System.out.println("Doctor " + doctor.getName() + ", you currently have no leave requests");
            }
        } else {
            System.out.println("Doctor not found");
        }
    }

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
            if(leaveFound = false){
                System.out.println("Doctor " + doctor.getName() + ", you currently have no Approved/Rejected leave requests");
            }

        } else {
            System.out.println("Doctor not found");
        }
    }

    public static void duplicateLeave(){
        CSVwrite.writeCSVList(updatedPath, leaves);
    }

    public static Leave findLeaveByID(String leaveID){
        for(Leave leave : leaves){
            if(leave.getLeaveID().equals(leaveID)){
                return leave;
            }
        }
        return null;
    }

    public static void addLeave(Leave leave){
        //generate newID for leave
        String leaveID = IDGenerator.generateID("LR", LeaveManager.getLeaves(), Leave::getLeaveID, 3); 

        leave.setLeaveID(leaveID);
        leaves.add(leave); //add new entry to list
        CSVwrite.writeCSV(updatedPath, leave); //add new entry to database
    }

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

    public static void removeLeave(){
        
    }

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
