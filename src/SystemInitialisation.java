//this class will be called when our program starts
//main purpose is to handle the imported data


import accounts.PatientsAcc;
import accounts.AdminsAcc;

public class SystemInitialisation {

    private static boolean isFirstRun = true;

    //simiplifies all methods needed when 1st boot up of system under 1 method
    public static void start(){

        //read and store all data from CSV into their respective Lists
        AdminsAcc.loadAdmins(isFirstRun);
        PatientsAcc.loadPatients(isFirstRun);

        //save the original data into a new file to be updated
        AdminsAcc.duplicateAdmin();
        PatientsAcc.duplicatePatient();

        //Displaying the List of Patients and Staffs from CSV
        AdminsAcc.displayAdmins();
        PatientsAcc.displayPatients();

        //set to false after first load
        isFirstRun = false;
    }

}
