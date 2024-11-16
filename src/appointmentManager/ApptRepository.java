package appointmentManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import io.CSVclear;
import user.User;


public class ApptRepository {
    private static final String CSV_FILE = "appointments.csv";
    public List<Appointment> appointments;
    
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
   
    public Appointment findAppointmentByID(int appointmentID) {
        appointments = loadAllAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;  // Return null if not found
    }

    // get past appointments for a specific patient
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

    public List<Appointment> getAllAppointments(String hospitalID) {
        List<Appointment> AllAppointments = new ArrayList<>();
        appointments = loadAllAppointments();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getHospitalID().equals(hospitalID) || appointment.getPatient().getHospitalID().equals(hospitalID)) {
                    AllAppointments.add(appointment);
            }
        }
        return AllAppointments;
    }
}
