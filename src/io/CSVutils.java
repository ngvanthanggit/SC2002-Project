package io;
import java.io.*;
import java.util.*;
import user.*;

public class CSVutils {

    // Extract common fields for both User and Patient
    private static User extractCommonFields(String[] row, Map<String, Integer> columnMapping) {
        //.trim() used to remove extra spaces after the strings/int
        return new User(
            row[columnMapping.get("hospitalID")].trim(),
            row[columnMapping.get("gender")].trim(),
            row[columnMapping.get("role")].trim(),
            row[columnMapping.get("name")].trim(),
            Integer.parseInt(row[columnMapping.get("age")].trim())
        );
    }

    // General method to read CSV and map columns to fields dynamically
    public static List<Object> readCSVMulti(String fileString, Map<String, Integer> columnMapping, String objectType) {
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

                // Handling User objects
                if (objectType.equals("User") && row.length >= 5) {
                    records.add(baseUser); // Directly add the user
                }
                // Handling Patient objects
                else if (objectType.equals("Patient") && row.length >= 8) {
                    Patient patient = new Patient(
                        baseUser.getHospitalId(),
                        baseUser.getGender(),
                        baseUser.getRole(),
                        baseUser.getName(),
                        baseUser.getAge(),
                        row[columnMapping.get("dateOB")].trim(),
                        row[columnMapping.get("bloodType")].trim(),
                        row[columnMapping.get("contactInfo")].trim()
                    );
                    records.add(patient); // Add the patient
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

        return records; // Return the list of records (either User or Patient objects)
    }
}
