package userInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import accounts.AdminsAcc;
import accounts.PharmacistsAcc;
import user.User;
import user.Role;

/**
 * This class provides a user interface for managing hospital staff.
 * <p>
 * It includes methods to view, add, update, and remove staff.
 */
public class StaffManageUI {

    /**
     * Displays a list of staff based on their role and provides options for filtering and sorting.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to view (e.g., Doctor, Pharmacist, Administrator).
     */
    public static void viewStaff(Scanner sc, Role role){
        //pass the corresponding type of List
        switch(role){
            case Doctor:
                //Doctor List
                System.out.println("Doctor not Done");
                break;
            case Pharmacist:
                //Pharmacist List
                filterStaff(sc, role, new ArrayList<>(PharmacistsAcc.getPharmacists()));
                break;
            case Administrator:
                //pass Administrator List
                filterStaff(sc, role, new ArrayList<>(AdminsAcc.getAdmins()));
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
    public static void filterStaff(Scanner sc, Role role, List<User> copiedList){
        int filter;
        do {
            System.out.println("\nSelect the way " + role  + " are displayed");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Name");
            System.out.println("2. Role/ID");
            System.out.println("3. Gender");
            System.out.println("4. Age");
            System.out.println("5. Default");
            System.out.println("6. Go Back");
            System.out.print("Choice: ");
            filter = sc.nextInt();
            sc.nextLine();

            //sort using List.sort + comparator class based on custom sorting input
            switch(filter) {
                case 1:
                    copiedList.sort(Comparator.comparing(User::getName)); //sort by Name
                    break;
                case 2:
                    //sort by Role, then by Hospital ID if roles are the same
                    copiedList.sort(Comparator.comparing(User::getRole).thenComparing(User::getHospitalID));
                    break;
                case 3:
                    copiedList.sort(Comparator.comparing(User::getGender)); //sort by Gender
                    break;
                case 4:
                    copiedList.sort(Comparator.comparingInt(User::getAge)); //sort by Age
                    break;
                case 5:
                    //default order, do nothing
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
            //after sorting, display staffs
            System.out.println("Displaying staff list:");
                for (User user : copiedList) {
                    System.out.println(user.userInfo());
                }
        } while(filter!=6);
    }

    /**
     * Adds a new staff member to the appropriate list based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to add.
     */
    public static void addStaff(Scanner sc, Role role){
        //add new Staff to specific List based on role 
        switch(role){
            case Doctor:
                //DoctorsAcc.addDoctor();
                System.out.println("New Doctor Added!");
                break;
            case Pharmacist:
                PharmacistsAcc.addPharmacist();
                break;
            case Administrator:
                AdminsAcc.addAdmin();
                break;
            default:
                //handles other roles ex. Patient
                System.out.println("Wrong Type of Staff.");
        }
    }

    /**
     * Updates an existing staff member's details based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to update.
     */
    public static void updateStaff(Scanner sc, Role role){
        //update staff from specific List based on role
        switch(role){
            case Doctor:
                //DoctorsAcc.updateStaff(sc);
                System.out.println("Doctor Updated!");
                break;
            case Pharmacist:
                PharmacistsAcc.updatePharmacist(sc);
                break;
            case Administrator:
                AdminsAcc.updateAdmin(sc);
                break;
            default:
                //handles other roles ex. Patient
                System.out.println("Wrong Type of Staff.");
        }

    }

    /**
     * Removes an existing staff member from the appropriate list based on their role.
     * 
     * @param sc   A {@link Scanner} object for user input.
     * @param role The {@link Role} of the staff to remove.
     */
    public static void removeStaff(Scanner sc, Role role){
        //remove staff from specific list based on role
        switch(role){
            case Doctor:
                //DoctorsAcc.removeDoctor(sc);
                System.out.println("Doctor Removed!");
                break;
            case Pharmacist:
                PharmacistsAcc.removePharmacist(sc);
                break;
            case Administrator:
                AdminsAcc.removeAdmin(sc); 
                break;
            default:
                //handles other roles ex. Patient
                System.out.println("Wrong Type of Staff.");
        }
    }
}
