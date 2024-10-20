import java.util.Scanner;

import accounts.PatientsAccount;
import accounts.StaffsAccount;
import user.*;

public class HMSApp {
    public static void main(String[] args){
        
        /*
         * Initialise the system by importing data from CSV files
         * Stores the List of Staffs & Patients
         */
        SystemInitialisation.start();

        //create and retrieve the list instances holding all staff & patients
        StaffsAccount staffs = SystemInitialisation.getStaffsAccount();
        PatientsAccount patients = SystemInitialisation.getPatientsAccount();
        Scanner sc = new Scanner(System.in);
        String hospitalId, gender, role, name;
        int age;

        User newUser = new User("S123", "Male", "Doctor", "John Doe", 35);
        staffs.addStaff(newUser);
        
        //Test whether new user was added
        System.out.println();
        System.out.println("The Staffs in the CSV file are: ");
        for (User staff : staffs.getStaffs()) {
            System.out.println(staff.userInfo());
        }

        /*System.out.println("Adding new staff");
        System.out.print("Enter your Id: "); //will make it automatic 
        hospitalId = sc.nextLine();
        System.out.print("Enter your gender: "); 
        gender = sc.nextLine();
        System.out.print("Enter your role: ");  
        role = sc.nextLine();
        System.out.print("Enter you name: "); 
        name = sc.nextLine();
        System.out.print("Enter your age: ");  
        age = sc.nextInt();

        User newUser = new User(hospitalId, gender, role, name, age);
        staffs.addStaff(newUser);*/
    }
}
