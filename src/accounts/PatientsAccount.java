package accounts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.Patient;
import io.CSVutils;

public class PatientsAccount {

    //store the list of patients
    private List<Patient> patients = new ArrayList<>();

    //static method to load patients from CSV file and return as a list
    public static List<Patient> loadPatients(){

        /*
         * might wanna include a if else statement for 1st initialisation 
         * so that we can continously use this statement to load patients
         */
        String fileString = "Data//Patient_List.csv";
        
        Map<String, Integer> patientMapping = new HashMap<>();
        patientMapping.put("hospitalID", 0);
        patientMapping.put("name", 1);
        patientMapping.put("role", 2);
        patientMapping.put("gender", 3);
        patientMapping.put("age", 4);
        patientMapping.put("dateOB", 5);
        patientMapping.put("bloodType", 6);
        patientMapping.put("contactInfo", 7);

        List<Object> patientMapList = CSVutils.readCSVMulti(fileString, patientMapping, "Patient");

        List<Patient> patientsList = new ArrayList<>();
        for (Object user : patientMapList) {
            if (user instanceof Patient) {
                patientsList.add((Patient) user);
            }
        }
        return patientsList;
        // Print all Patient records
        /*for (Object patient : patientsList) {
            if(patient instanceof Patient){
                System.out.println(((Patient) patient).userInfo());
            }
        }*/

        //gettomg specific object
        //System.out.println( ((Patient) patients.get(0)).userInfo() );
    }

    // Method to load patients into the instance variable
    public void loadInstancePatients() {

        //patients now hold the list of patients from the CSV file
        this.patients = loadPatients(); 
        if (patients.isEmpty()) {
            System.out.println("No patients were loaded.");
        } else {
            System.out.println("Patients successfully loaded: " + patients.size());
        }
    }

    public List<Patient> getPatients() {
        return patients;
    }

    /*public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }*/
}
