package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import user.*;
import inventory.*;
import medicalrecord.*;
import schedule.*;
import appointment.*;
import leave.*;

/**
 * The class provides utility methods for reading various types of CSV files
 * and mapping the data into different object types, such as {@link User}, {@link Patient}, 
 * {@link Doctor}, {@link Pharmacist}, {@link Administrator}, {@link InventoryItem}, {@link ReplenishRequest}, 
 * {@link MedicalRecord}, {@link Schedule}, {@link Appointment} and {@link Leave}.
 * <p>
 * It dynamically maps CSV columns to object fields using a column mapping provided by the caller,
 * making it highly flexible for different CSV structures.
 * </p>
 */
public class CSVread {

    /**
     * Extracts common fields for both {@link User} and {@link Patient}.
     * This method extracts shared properties like hospital ID, name, role, gender, age, and password
     * from the CSV row and creates a new {@link User} object.
     *
     * @param row The row from the CSV file.
     * @param columnMapping A map that associates column names with their index positions in the CSV.
     * @return A new {@link User} object populated with the extracted data.
     */
    private static User extractCommonFields(String[] row, Map<String, Integer> columnMapping) {
        // .trim() used to remove extra spaces after the strings/int
        return new User(
                row[columnMapping.get("hospitalID")].trim(),
                row[columnMapping.get("name")].trim(),
                Role.valueOf(row[columnMapping.get("role")].trim()),
                row[columnMapping.get("gender")].trim(),
                Integer.parseInt(row[columnMapping.get("age")].trim()),
                row[columnMapping.get("password")].trim());
    }

   /**
     * Reads a CSV file and maps each row to a corresponding object based on the provided {@code objectType}.
     * The method supports reading data for different object types, such as {@code User}, {@code Patient},
     * {@code Doctor}, {@code Pharmacist}, and {@code Administrator}.
     *
     * @param fileString The path to the CSV file to be read.
     * @param columnMapping A map that associates CSV column names with their index positions.
     * @param objectType The type of object to create for each row ("User", "Patient", "Doctor", etc.).
     * @return A list of objects created from the CSV data, based on the {@code objectType}.
     */
    public static List<Object> readCSV(String fileString, Map<String, Integer> columnMapping, String objectType) {
        BufferedReader reader = null;
        String line = "";
        List<Object> records = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                // Extract common fields using the helper method
                User baseUser = extractCommonFields(row, columnMapping);

                // Handling User objects, change the row.length for the amount of parameters in
                // your class
                if (objectType.equals("User") && row.length >= 6) {
                    records.add(baseUser); // Add the user
                }
                // Handling Patient objects
                else if (objectType.equals("Patient") && row.length >= 9) {
                    Patient patient = new Patient(
                            baseUser.getHospitalID(),
                            baseUser.getName(),
                            baseUser.getRole(),
                            baseUser.getGender(),
                            baseUser.getAge(),
                            baseUser.getPassword(),
                            row[columnMapping.get("dateOB")].trim(),
                            row[columnMapping.get("bloodType")].trim(),
                            row[columnMapping.get("contactInfo")].trim());
                    records.add(patient); // Add the patient
                }

                // Handle Doctor objects, change the row.length for the amount of parameters in
                // your class
                else if (objectType.equals("Doctor") && row.length >= 6) {
                    Doctor doctor = new Doctor(
                            baseUser.getHospitalID(),
                            baseUser.getName(),
                            baseUser.getRole(),
                            baseUser.getGender(),
                            baseUser.getAge(),
                            baseUser.getPassword());
                    records.add(doctor); // Add the doctor
                }
                // Handle Pharmacist objects, change the row.length for the amount of parameters
                // in your class
                else if (objectType.equals("Pharmacist") && row.length >= 6) {
                    Pharmacist pharmacist = new Pharmacist(
                            baseUser.getHospitalID(),
                            baseUser.getName(),
                            baseUser.getRole(),
                            baseUser.getGender(),
                            baseUser.getAge(),
                            baseUser.getPassword());
                    records.add(pharmacist);
                }
                // Handle Administrator objects
                else if (objectType.equals("Administrator") && row.length >= 6) {
                    Administrator administrator = new Administrator(
                            baseUser.getHospitalID(),
                            baseUser.getName(),
                            baseUser.getRole(),
                            baseUser.getGender(),
                            baseUser.getAge(),
                            baseUser.getPassword());
                    records.add(administrator); // Add the administrator
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return records; // Return the list of records (depends on the class of objects)
    }

    /**
     * Reads an inventory CSV file and maps the rows to a list of {@link InventoryItem} objects.
     * This method expects columns for medicine name, initial stock, and low stock level.
     *
     * @param fileString The path to the CSV file.
     * @param columnMapping A map that associates column names with their index positions in the CSV.
     * @return A list of {@link InventoryItem} objects created from the CSV data.
     */
    public static List<InventoryItem> readItemCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<InventoryItem> inventory = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                InventoryItem item = new InventoryItem(
                        Medicine.valueOf(row[columnMapping.get("Medicine Name")].trim()),
                        Integer.parseInt(row[columnMapping.get("Initial Stock")].trim()),
                        Integer.parseInt(row[columnMapping.get("Low Stock Level Alert")].trim()));

                inventory.add(item); // add item to the list
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inventory; // Return the list of InventoryItem object
    }

    /**
     * Reads a replenish request CSV file and maps the rows to a list of {@link ReplenishRequest} objects.
     * It parses fields like request ID, medicine name, quantity, requested by, request date, and status.
     *
     * @param fileString The path to the replenish request CSV file.
     * @param columnMapping A map that associates column names with their index positions in the CSV.
     * @return A list of {@link ReplenishRequest} objects created from the CSV data.
     */
    public static List<ReplenishRequest> readReplenishCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<ReplenishRequest> replenishList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Corrected date format

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                // System.out.println("Processing row: " + line);
                // System.out.println("Row length: " + row.length);

                // Check if the row has enough columns for "Medicine"
                if (columnMapping.get("Medicine") >= row.length) {
                    // System.out.println("Skipping malformed row (missing Medicine column): " +
                    // line);
                    continue;
                }

                // Parse fields from the CSV row based on column mapping
                String requestID = row[columnMapping.get("RequestID")].trim();
                String itemName = row[columnMapping.get("Medicine")].trim();
                int replenishQuantity = Integer.parseInt(row[columnMapping.get("Quantity")].trim());
                String requestedBy = row[columnMapping.get("RequestedBy")].trim();
                LocalDate requestDate = LocalDate.parse(row[columnMapping.get("RequestDate")].trim(), formatter);
                RequestStatus status = RequestStatus.valueOf(row[columnMapping.get("Status")].trim());
                // LocalDate approvalDate = (row.length > columnMapping.get("ApprovalDate") &&
                // !row[columnMapping.get("ApprovalDate")].trim().isEmpty())
                // ? LocalDate.parse(row[columnMapping.get("ApprovalDate")].trim(), formatter) :
                // null;

                // Handle ApprovalDate: allow it to remain null if the column is empty
                LocalDate approvalDate = null;
                if (columnMapping.containsKey("ApprovalDate") && columnMapping.get("ApprovalDate") < row.length) {
                    String approvalDateString = row[columnMapping.get("ApprovalDate")].trim();
                    if (!approvalDateString.isEmpty()) {
                        approvalDate = LocalDate.parse(approvalDateString, formatter);
                    }
                }
                ReplenishRequest request = new ReplenishRequest(requestID, itemName, replenishQuantity, requestedBy,
                        requestDate, status, approvalDate);

                replenishList.add(request); // Add item to the list
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return replenishList; // Return the list of ReplenishRequest objects
    }

    /**
 * Reads a CSV file containing medical record information and parses it into a list of {@link MedicalRecord} objects.
 * The method uses a column mapping to identify which columns to read for each field.
 * 
 * @param fileString The path to the CSV file to read.
 * @param columnMapping A map that associates column names to their respective indices in the CSV file.
 * @return A list of {@link MedicalRecord} objects containing the parsed data.
 */
    public static List<MedicalRecord> readMedicalRecordCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<MedicalRecord> medicalRecords = new ArrayList<>();
    
        try {
            reader = new BufferedReader(new FileReader(fileString));
    
            // Read the first line to skip the header
            reader.readLine();
    
            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
    
                // Array of Strings, split at commas (prescriptions field may use colons internally)
                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    
                // Parse fields from the CSV row based on column mapping
                String medicalRID = row[columnMapping.get("MedicalR ID")].trim();
                String doctorID = row[columnMapping.get("Doctor ID")].trim();
                String patientID = row[columnMapping.get("Patient ID")].trim();
                List<String> diagnoses = Arrays.asList(row[columnMapping.get("Diagnoses")].split(";"));
                String prescriptionsString = row[columnMapping.get("Prescriptions")].replace("\"", "");
                List<String> treatmentPlans = Arrays.asList(row[columnMapping.get("Treatment Plan")].split(";"));
                PrescriptionStatus status = null;
                try {
                    status = PrescriptionStatus.valueOf(row[columnMapping.get("Prescription Status")].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid Prescription Status: " + row[columnMapping.get("Prescription Status")]);
                }

                // Parse prescriptions into a Map<String, Integer>
                Map<String, Integer> prescriptions = new HashMap<>();
                if (!prescriptionsString.isEmpty()) {
                    String[] prescriptionItems = prescriptionsString.split(";");
                    for (String item : prescriptionItems) {
                        String[] parts = item.trim().split(": ");

                        if (parts.length != 2) {
                            System.err.println("Invalid prescription format: " + item);
                            continue; // Skip invalid entries
                        }
                        
                        if (parts.length == 2) {
                            String medication = parts[0];
                            try {
                                int quantity = Integer.parseInt(parts[1]);
                                prescriptions.put(medication, quantity);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid prescription quantity: " + item);
                            }
                        }
                    }
                }
    
                // Create a new MedicalRecord object
                MedicalRecord record = new MedicalRecord(medicalRID, doctorID, patientID, diagnoses, prescriptions, treatmentPlans, status);
                medicalRecords.add(record);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return medicalRecords; // Return the list of MedicalRecord objects
    }
    


    /**
     * Reads a CSV file containing schedule information and parses it into a list of {@link Schedule} objects.
     * The method uses a column mapping to identify which columns to read for each field.
     * 
     * @param fileString The path to the CSV file to read.
     * @param columnMapping A map that associates column names to their respective indices in the CSV file.
     * @return A list of {@link Schedule} objects containing the parsed data.
     */
    public static List<Schedule> readScheduleCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<Schedule> schedules = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (row.length < columnMapping.size()) {
                    System.err.println("Skipping malformed row: " + line);
                    continue;
                }

                // Parse fields from the CSV row based on column mapping
                String doctorID = row[columnMapping.get("Doctor ID")].trim();
                LocalDate date = LocalDate.parse(row[columnMapping.get("Date")].trim());
                String timeSlotsString = row[columnMapping.get("Time Slots")].replace("\"", "").trim();

                List<LocalTime> timeSlots = new ArrayList<>();
                for (String timeSlot : timeSlotsString.split(";")) {
                    LocalTime time = LocalTime.parse(timeSlot);
                    timeSlots.add(time);
                }

                // Create a new Schedule object
                Schedule schedule = new Schedule(doctorID, date, timeSlots);

                // Add the schedule to the list
                schedules.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return schedules;
    }

    /**
     * Reads a CSV file containing appointment information and parses it into a list
     * of {@link Appointment} objects.
     * The method uses a column mapping to identify which columns to read for each
     * field.
     * It then reconstructs the {@link Doctor} and {@link Patient} objects using
     * their respective IDs.
     * 
     * @param fileString    The path to the CSV file to read.
     * @param columnMapping A map that associates column names to their respective
     *                      indices in the CSV file.
     * @return A list of {@link Appointment} objects containing the parsed data.
     */
    public static List<Appointment> readApptCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<Appointment> appointments = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                // Extract appointment fields based on column mapping
                String appointmentID = row[columnMapping.get("AppointmentID")].trim();
                String patientID = row[columnMapping.get("PatientID")].trim();
                String doctorID = row[columnMapping.get("DoctorID")].trim();
                LocalDate date = LocalDate.parse(row[columnMapping.get("Date")].trim());
                LocalTime time = LocalTime.parse(row[columnMapping.get("Time")].trim());
                ApptStatus status = ApptStatus.valueOf(row[columnMapping.get("Status")].trim().toUpperCase());
                String consultationNotes = row.length > columnMapping.get("ConsultationNotes")
                        ? row[columnMapping.get("ConsultationNotes")].trim()
                        : "";
                String prescribedMedications = row.length > columnMapping.get("PrescribedMedications")
                        ? row[columnMapping.get("PrescribedMedications")].trim()
                        : "";
                String serviceType = row.length > columnMapping.get("ServiceType")
                        ? row[columnMapping.get("ServiceType")].trim()
                        : "";

                // Recreate Doctor and Patient objects using their IDs
                Doctor doctor = (Doctor) DoctorsAcc.findDoctorById(doctorID);
                Patient patient = (Patient) PatientsAcc.findPatientById(patientID);

                // Create a new Appointment object
                Appointment appointment = new Appointment(doctor, patient, date, time, appointmentID);
                appointment.setStatus(status);
                appointment.setConsultationNotes(consultationNotes);
                appointment.setPrescribedMedications(prescribedMedications);
                appointment.setServiceType(serviceType);

                appointments.add(appointment); // Add the appointment to the list
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appointments; // Return the list of Appointment objects
    }

    /**
     * Reads leave data from a CSV file and converts it into a list of {@link Leave} objects.
     * <p>
     * Parses each row of the CSV based on the provided column mapping, recreating {@link Leave} 
     * objects with associated staff details.
     *
     * @param fileString    The path to the CSV file to read.
     * @param columnMapping A map specifying the column names and their corresponding indices.
     * @return A {@link List} of {@link Leave} objects parsed from the CSV file.
     */
    public static List<Leave> readLeaveCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<Leave> leaves = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                // Parse fields from the CSV row based on column mapping
                String leaveID = row[columnMapping.get("leaveID")].trim();
                String staffID = row[columnMapping.get("staffID")].trim();
                LocalDate date = LocalDate.parse(row[columnMapping.get("date")].trim(), formatter);
                LeaveStatus status = LeaveStatus.valueOf(row[columnMapping.get("status")].trim());
                String reason = row[columnMapping.get("reason")].trim();

                // Recreate Staff objects using their IDs, only handle doctorIDs for now but expandable
                Doctor doctor = null;
                if(staffID.charAt(0) == 'D') {
                    doctor = (Doctor) DoctorsAcc.findDoctorById(staffID);
                }

                Leave leave = new Leave(leaveID, doctor, date, status, reason);
                leaves.add(leave); // add item to the list
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return leaves; // Return the list of Leave object
    }
}
