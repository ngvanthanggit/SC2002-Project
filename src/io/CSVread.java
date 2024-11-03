package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import user.*;

public class CSVread {

    //Extract common fields for both User and Patient
    private static User extractCommonFields(String[] row, Map<String, Integer> columnMapping) {
        //.trim() used to remove extra spaces after the strings/int
        return new User(
            row[columnMapping.get("hospitalID")].trim(),
            row[columnMapping.get("name")].trim(),
            row[columnMapping.get("role")].trim(),
            row[columnMapping.get("gender")].trim(),
            Integer.parseInt(row[columnMapping.get("age")].trim()),
            row[columnMapping.get("password")].trim()
        );
    }

    //General method to read CSV and map columns to fields dynamically
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

                // Handling User objects, change the row.length for the amount of parameters in your class
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

                // Handle Doctor objects, change the row.length for the amount of parameters in your class
                else if (objectType.equals("Doctor") && row.length >= 9){

                }
                // Handle Pharmacist objects, change the row.length for the amount of parameters in your class
                else if (objectType.equals("Pharmacist") && row.length >= 9){

                }
                // Handle Administrator objects
                else if (objectType.equals("Administrator") && row.length >= 6){
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
}
