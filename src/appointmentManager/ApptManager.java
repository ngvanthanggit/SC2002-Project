package appointmentManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import inventory.InventoryItem;
import io.CSVclear;
import io.CSVread;
import io.CSVwrite;
import user.User;

//holds the List of All Appointments from CSV
public class ApptManager {
    
    //UPDATED CODE
    private static final String originalPath = "Data//Original/Appt_List.csv";
    private static final String updatedPath = "Data//Updated/Appt_List(Updated).csv";
    private static List<Appointment> apptList = new ArrayList<>();

    public static void loadAppointments(boolean isFirstRun){
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }
        apptList.clear();

        Map<String, Integer> apptColumnMapping = new HashMap<>();
        apptColumnMapping.put("AppointmentID", 0);
        apptColumnMapping.put("PatientID", 1);
        apptColumnMapping.put("DoctorID", 2);
        apptColumnMapping.put("Date", 3);
        apptColumnMapping.put("Time", 4);
        apptColumnMapping.put("Status", 5);
        apptColumnMapping.put("ConsultationNotes", 6);
        apptColumnMapping.put("PrescribedMedications", 7);
        apptColumnMapping.put("ServiceType", 8);

        List<Appointment> apptMapList = CSVread.readApptCSV(filePath, apptColumnMapping);

        // add the data from CSV into patientsList
        for (Appointment appt : apptMapList) {
            if (appt instanceof Appointment) {
                apptList.add(appt);
            }
        }
        
        if (apptList.isEmpty()) {
            System.out.println("No items were loaded.");
        } else {
            System.out.println("Inventory successfully loaded: " + apptList.size());
        }
    }

    //return all appointments in the list
    public static List<Appointment> getAppointments(){
        return apptList;
    }

    //rewrite all data in the CSV file
    public static void duplicateAppointments(){
        CSVwrite.writeCSVList(updatedPath, apptList);
    }

    //find appointment by the entered ID
    public static Appointment findAppointmentByID(int appointmentID) {
        for (Appointment appointment : apptList) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;  // Return null if not found
    }

    //only used by the Administrator
    public static void displayAppointments(){
        if(apptList.isEmpty()) {
            System.out.println("The Appointments is currently empty.");
        }
        else {
            System.out.println("\nThe Appointments in the CSV file are: ");
            for(Appointment appointment: apptList){
                System.out.println(appointment.getApptInfo());
            }
        }
    }

    //used by Doctor OR Patient to Display ALL appointments
    public static List<Appointment> displayAppointments(User user){
        //create a new list to store appts with the same Doctor/Patient
        List<Appointment> allAppts = new ArrayList<>();
        //find matching Patient/Doctor in all Appointments
        for (Appointment appointment: apptList){
            //if found either Patient/Doctor return all appts
            if(appointment.getDoctor().equals(user) || appointment.getPatient().equals(user)){
                allAppts.add(appointment);
            }
        }
        return allAppts;
    }

    //used by Doctor or Patient to Display Appts STATUS_CONFIRMED
    public static List<Appointment> displayConfirmedAppts(User user){
        List<Appointment> confirmedAppt = new ArrayList<>();
        for(Appointment appointment: apptList){
            //add all confirmed appointmnets
            if((appointment.getDoctor().equals(user) || appointment.getPatient().equals(user))
            && appointment.getStatus() == ApptStatus.CONFIRMED){
                confirmedAppt.add(appointment);
            }
        }
        return confirmedAppt;
    }

    public static List<Appointment> displayPastAppts(User user){
        List<Appointment> pastAppt = new ArrayList<>();
        for(Appointment appointment: apptList){
            //add all past appointmnets => COMPLETED
            if((appointment.getDoctor().equals(user) || appointment.getPatient().equals(user))
            && appointment.getStatus() == ApptStatus.COMPLETED){
                pastAppt.add(appointment);
            }
        }
        return pastAppt;
    }

    //used by patient & admin
    public Appointment getOutcomeRecord(int appointmentID) {
        try {
            Appointment appointment = findAppointmentByID(appointmentID);
            if (appointment == null) {
                throw new Exception("AppointmentID is null.");
            }
            return appointment;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
    get all Patient Appointments
    - get confirmed only
    - get unconfirmed
    */

    /*
    get all Doctor Appointments
    - get confirmed only
    - get unconfirmed
    */

    //get appointment outcome



    //ORIGINAL CODE
    /*private static final String CSV_FILE = "appointments.csv"; //remove later
    public List<Appointment> appointments;

    public List<Appointment> loadAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            // br.readLine(); // read and discard header line
            while ((line = br.readLine()) != null) {
                Appointment appt = Appointment.fromCSV(line);  // Assuming you have this method
                appointments.add(appt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    private void writeHeaderToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, false))) {  // 'false' to overwrite
            bw.write("AppointmentID,PatientID,DoctorID,Date,Time,Status,ConsultationNotes,PrescribedMedications,ServiceType");  // Header
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while writing header to CSV.");
        }
    }

    private void clearCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, false))) {  // 'false' ensures overwriting
            writeHeaderToCSV();
            bw.newLine();  // Ensure header is written first
            System.out.println("CSV file cleared and header written.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while clearing the CSV file.");
        }
    }

    public boolean removeFromCSV(Appointment appointment) {
        appointments = loadAllAppointments();
        // remove later
        System.out.println("Loaded appointments: " + appointments.size());

        int appointmentID = appointment.getAppointmentID();
        // remove later
        System.out.println("Attempting to remove appointment with ID: " + appointmentID);

        // Filter out the appointment to remove
        List<Appointment> updatedAppointments = appointments.stream()
        .filter(a -> a.getAppointmentID() != appointmentID) // remove the matching appt using appointmentID
        .collect(Collectors.toList());

        // remove later
        System.out.println("Appointments after removal: " + updatedAppointments.size());

        // Rewrite the CSV file without the removed appointment
        return rewriteCSV(updatedAppointments);
    }
    
    private boolean rewriteCSV(List<Appointment> appointments) {
        // appointments = loadAllAppointments();
        // remove later
        System.out.println("Rewriting CSV with " + appointments.size() + " appointments.");
        //clearCSV();
        CSVclear.clearFile(CSV_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Appointment appt : appointments) {
                bw.write(appt.toCSVFormat());
                bw.newLine();
            }
            // remove later
            System.out.println("CSV updated successfully.");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // remove later
            System.out.println("Error while writing to CSV.");

            return false;
        }
    }

    public void saveToCSV(Appointment appointment) {
        // remove later
        System.out.println("Saving appointment with ID: " + appointment.getAppointmentID() + " to CSV.");
        
        // Ensure header is present before saving appointments
        if (new File(CSV_FILE).length() == 0) {
            CSVclear.clearFile(CSV_FILE);
            //clearCSV(); // Write header if file is empty
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            bw.write(appointment.toCSVFormat());
            bw.newLine();
            // remove later
            System.out.println("Appointment saved to CSV.");

        } catch (IOException e) {
            e.printStackTrace();
            // remove later
            System.out.println("Error while saving appointment to CSV.");
        }
    }

    public void updateCSV(Appointment updatedAppointment){
        List<Appointment> appointments = loadAllAppointments(); // Load all appointments from CSV

        // Find the appointment to update and replace it with the updated version
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID() == updatedAppointment.getAppointmentID()) {
                appointments.set(i, updatedAppointment); // Replace with updated appointment
                break;
            }
        }
        // Rewrite the CSV with the updated appointments list
        rewriteCSV(appointments);
    }
    
    public Appointment findAppointmentByID(int appointmentID) {
        appointments = loadAllAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;  // Return null if not found
    }
    //remove till here

    //used by patient & doctor
    public List<Appointment> loadConfirmedAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            // br.readLine(); // read and discard header line
            while ((line = br.readLine()) != null) {
                Appointment appt = Appointment.fromCSV(line); 
                if (appt.getStatus() == Appointment.Status.CONFIRMED) { 
                    appointments.add(appt); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // get past appointments for a specific patient & admin
    public List<Appointment> getPastAppointments(User patient) {
        List<Appointment> pastAppointments = new ArrayList<>();
        appointments = loadAllAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient)) {
                if (appointment.getStatus().equals(Appointment.Status.COMPLETED)|| 
                    appointment.getStatus().equals(Appointment.Status.CANCELLED) || appointment.getStatus().equals(Appointment.Status.CONFIRMED)) {
                    pastAppointments.add(appointment);
                }
            }
        }
        return pastAppointments;
    }

    //used by patient & admin
    public Appointment getOutcomeRecord(int appointmentID) {
        try {
            Appointment appointment = findAppointmentByID(appointmentID);
            if (appointment == null) {
                throw new Exception("AppointmentID is null.");
            }
            return appointment;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //both patient admin usage
    public List<Appointment> getAllAppointments(String hospitalID) {
        List<Appointment> AllAppointments = new ArrayList<>();
        appointments = loadAllAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getHospitalID().equals(hospitalID) || appointment.getPatient().getHospitalID().equals(hospitalID)) {
                    AllAppointments.add(appointment);
            }
        }
        return AllAppointments;
    }*/
}
