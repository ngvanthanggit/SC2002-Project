package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.print.Doc;

import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import user.*;
import inventory.*;
import medicalrecord.*;
import schedule.*;
import appointment.*;

public class CSVread {

    // Extract common fields for both User and Patient
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

    // General method to read CSV and map columns to fields dynamically
    public static List<Object> readCSV(String fileString, Map<String, Integer> columnMapping, String objectType) {
        BufferedReader reader = null;
        String line = "";
        List<Object> records = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

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

    // general method to read CSV and map columns to fields dynamically for
    // inventory
    public static List<InventoryItem> readItemCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<InventoryItem> inventory = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

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

    // general method to read CSV and map columns to fields dynamically for
    // replenishrequest
    public static List<ReplenishRequest> readReplenishCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<ReplenishRequest> replenishList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Corrected date format

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

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

                // Create a new ReplenishRequest with parsed data
                // ReplenishRequest request = new ReplenishRequest(requestID, itemName,
                // replenishQuantity);
                // request.setRequestedBy(requestedBy);
                // request.setRequestDate(requestDate);
                // request.setRequestStatus(status);
                // request.setApprovalDate(approvalDate); // Manually set approval date if it
                // exists

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

    // General method to read CSV and map columns to fields dynamically for
    // MedicalRecord
    public static List<MedicalRecord> readMedicalRecordCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Array of Strings, split at commas
                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                // Check if row has sufficient columns
                if (row.length < columnMapping.size()) {
                    System.err.println("Skipping malformed row: " + line);
                    continue;
                }

                // Parse fields from the CSV row based on column mapping
                String doctorID = row[columnMapping.get("Doctor ID")].trim();
                String patientID = row[columnMapping.get("Patient ID")].trim();
                List<String> diagnoses = List.of(row[columnMapping.get("Diagnoses")].replace("\"", "").split(";"));
                List<String> prescriptions = List
                        .of(row[columnMapping.get("Prescriptions")].replace("\"", "").split(";"));
                List<String> treatmentPlans = List
                        .of(row[columnMapping.get("Treatment Plan")].replace("\"", "").split(";"));

                // Create a new MedicalRecord object
                MedicalRecord record = new MedicalRecord(doctorID, patientID, diagnoses, prescriptions, treatmentPlans);

                medicalRecords.add(record); // Add the record to the list
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

    public static List<Schedule> readScheduleCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<Schedule> schedules = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line
            String headerLine = reader.readLine();

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

    public static List<Appointment> readApptCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<Appointment> appointments = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

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
}
