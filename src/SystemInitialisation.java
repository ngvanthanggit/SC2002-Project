//this class will be called when our program starts
//main purpose is to handle the imported data


import accounts.PatientsAccount;
import accounts.StaffsAccount;
import user.*;

public class SystemInitialisation {

    private static PatientsAccount patientsAccount;
    private static StaffsAccount staffsAccount;

    //simiplifies all methods needed when 1st boot up of system under 1 method
    public static void start(){

        //create the list instances holding all staff & patients
        staffsAccount = new StaffsAccount();
        patientsAccount = new PatientsAccount();

        //Load patients & staff into the instance variable using the static method
        staffsAccount.loadInstanceStaffs();
        patientsAccount.loadInstancePatients();

        //Displaying the List of Patients and Staffs from CSV
        displayStaffsAccount();
        displayPatientsAccount();
    }

    public static StaffsAccount getStaffsAccount(){
        return staffsAccount;
    }

    public static PatientsAccount getPatientsAccount(){
        return patientsAccount;
    }

    // Printing the List of Patients and Staffs
    public static void displayStaffsAccount(){
        System.out.println("The Staffs in the CSV file are: ");
        for (User staff : staffsAccount.getStaffs()) {
            System.out.println(staff.userInfo());
        }
    }

    public static void displayPatientsAccount(){
        System.out.println("The Patients in the CSV file are: ");
        for (Patient patient : patientsAccount.getPatients()) {
            System.out.println(patient.userInfo());
        }
    }
}
