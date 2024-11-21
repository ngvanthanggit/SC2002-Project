package userInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import accounts.AdminsAcc;
import accounts.DoctorsAcc;
import accounts.PatientsAcc;
import accounts.PharmacistsAcc;
import interfaces.StaffManageInterface;
import user.User;
import user.Role;

/**
 * This class provides a user interface for managing hospital staff.
 * <p>
 * It includes methods to view, add, update, and remove staff.
 */
public class StaffManageUI implements StaffManageInterface{

    //constructor
    public StaffManageUI(){}

    /**
     * Displays a list of staff based on their role and provides options for filtering and sorting.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to view (e.g., Doctor, Pharmacist, Administrator).
     */
    public void viewStaff(Scanner sc, Role role){
        //pass the corresponding type of List
        switch(role){
            case Doctor:
                //Doctor List
                filterStaff(sc, role, new ArrayList<>(DoctorsAcc.getDoctors()));
                break;
            case Pharmacist:
                //Pharmacist List
                filterStaff(sc, role, new ArrayList<>(PharmacistsAcc.getPharmacists()));
                break;
            case Administrator:
                //pass Administrator List
                filterStaff(sc, role, new ArrayList<>(AdminsAcc.getAdmins()));
                break;
            case Patient:
                //pass Patient List
                filterStaff(sc, role, new ArrayList<>(PatientsAcc.getPatients()));
                break;
            default:
                //handles other roles ex. Patient
                System.out.println("Wrong Type of Staff.");
                
        }
    }

    /**
     * Provides filtering and sorting options for a list of staff members.
     * 
     * @param sc         A {@link Scanner} object for user input.
     * @param role       The {@link Role} of the staff being filtered.
     * @param copiedList A list of {@link User} objects to filter and sort.
     */
    public void filterStaff(Scanner sc, Role role, List<User> copiedList){
        int choice = -1;
        do {
            System.out.println("\nSelect the way " + role  + " are displayed");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Name");
            System.out.println("2. Gender");
            System.out.println("3. Age");
            System.out.println("4. Default");
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            //sort using List.sort + comparator class based on custom sorting input
            switch(choice) {
                case 1:
                    copiedList.sort(Comparator.comparing(User::getName)); //sort by Name
                    break;
                case 2:
                    copiedList.sort(Comparator.comparing(User::getGender)); //sort by Gender
                    break;
                case 3:
                    copiedList.sort(Comparator.comparingInt(User::getAge)); //sort by Age
                    break;
                case 4:
                    //default order, do nothing
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
            //after sorting, display staffs
            System.out.println("\nDisplaying " + role + " list:");
                for (User user : copiedList) {
                    System.out.println(user.userInfo());
                }
        } while(choice!=5);
    }

    /**
     * Adds a new staff member to the appropriate list based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to add.
     */
    public void addStaff(Scanner sc, Role role){
        //add new Staff to specific List based on role 
        switch(role){
            case Doctor:
                //DoctorsAcc.addDoctor();
                DoctorsAcc.addDoctor(sc);
                break;
            case Pharmacist:
                PharmacistsAcc.addPharmacist(sc);
                break;
            case Administrator:
                AdminsAcc.addAdmin(sc);
                break;
            case Patient:
                PatientsAcc.addPatient(sc);
                break;
            default:
                System.out.println("Wrong Type of User.");
        }
    }

    /**
     * Updates an existing staff member's details based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to update.
     */
    public void updateStaff(Scanner sc, Role role){
        //update staff from specific List based on role
        switch(role){
            case Doctor:
                DoctorsAcc.updateDoctor(sc);
                break;
            case Pharmacist:
                PharmacistsAcc.updatePharmacist(sc);
                break;
            case Administrator:
                AdminsAcc.updateAdmin(sc);
                break;
            case Patient:
                PatientsAcc.updatePatient(sc, null);
                break;
            default:
                System.out.println("Wrong Type of User.");
        }

    }

    /**
     * Removes an existing staff member from the appropriate list based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to remove.
     */
    public void removeStaff(Scanner sc, Role role){
        //remove staff from specific list based on role
        switch(role){
            case Doctor:
                //DoctorsAcc.removeDoctor(sc);
                DoctorsAcc.removeDoctor(sc);
                break;
            case Pharmacist:
                PharmacistsAcc.removePharmacist(sc);
                break;
            case Administrator:
                AdminsAcc.removeAdmin(sc); 
                break;
            case Patient:
                PatientsAcc.removePatient(sc);
                break;
            default:
                System.out.println("Wrong Type of User.");
        }
    }
}
