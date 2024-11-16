package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import user.*;
import inventory.*;
import medicalrecord.*;

public class CSVread {

    // Extract common fields for both User and Patient
    private static User extractCommonFields(String[] row, Map<String, Integer> columnMapping) {
        // .trim() used to remove extra spaces after the strings/int
        return new User(
                row[columnMapping.get("hospitalID")].trim(),
                row[columnMapping.get("name")].trim(),
                row[columnMapping.get("role")].trim(),
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

    // General method to read CSV and map columns to fields dynamically for
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
                        row[columnMapping.get("Medicine Name")].trim(),
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

    // General method to read CSV and map columns to fields dynamically for
    // replenishrequest
    public static List<ReplenishRequest> readReplenishCSV(String fileString, Map<String, Integer> columnMapping) {
        BufferedReader reader = null;
        String line = "";
        List<ReplenishRequest> replenishList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try {
            reader = new BufferedReader(new FileReader(fileString));

            // Read the first line to skip the header
            String headerLine = reader.readLine();

            // Continuously read the next line
            while ((line = reader.readLine()) != null) {
                // Array of Strings, split at commas
                String[] row = line.split(",");

                // Parse fields from the CSV row based on column mapping
                String requestID = row[columnMapping.get("RequestID")].trim();
                String itemName = row[columnMapping.get("Medicine")].trim();
                int replenishQuantity = Integer.parseInt(row[columnMapping.get("Quantity")].trim());
                String requestedBy = row[columnMapping.get("RequestedBy")].trim();
                LocalDate requestDate = LocalDate.parse(row[columnMapping.get("RequestDate")].trim(), formatter);
                RequestStatus status = RequestStatus.valueOf(row[columnMapping.get("Status")].trim());
                LocalDate approvalDate = (row.length > columnMapping.get("ApprovalDate")
                        && !row[columnMapping.get("ApprovalDate")].trim().isEmpty())
                                ? LocalDate.parse(row[columnMapping.get("ApprovalDate")].trim(), formatter)
                                : null;

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

}
