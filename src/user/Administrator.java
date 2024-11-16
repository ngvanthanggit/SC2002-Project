package user;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


import accounts.AdminsAcc;
import accounts.DoctorsAcc;
import accounts.PharmacistsAcc;

public class Administrator extends User  {
    
    //constructors
    public Administrator(){
        super(null, null, null, null, 0, null);
    }

    public Administrator(String hospitalID, String name, Role role,
                String gender, int age, String password){
        super(hospitalID, name, role, gender, age, password);
    }

    @Override
    public void logout(){
        System.out.println("Administrator Logging Out.");
        return;
    }

    @Override
    public String userInfo() {
        return String.format("[AdminID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s]",
        getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword());
    }

    //View all Admin Methods, implemented from User Class Interface
    public void displayMenu(){
        int choice;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\nAdmin Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Choice: ");   
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    manageHospitalStaff(sc);
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    logout(); 
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while(choice!=5);
    }

    /*
     * Staff Management
     */
    public void manageHospitalStaff(Scanner sc){
        int choice;
        do {
            System.out.println("\nView & Manage Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Hospital Staff");
            System.out.println("2. Add New Hospital Staff");
            System.out.println("3. Update Hospital Staff");
            System.out.println("4. Remove Hospital Staff Member");
            System.out.println("5. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    viewStaff(sc);
                    break;

                case 2:
                    addStaff(sc);
                    break;

                case 3:
                    updateStaff(sc);
                    break;

                case 4:
                    removeStaff(sc);
                    break;

                case 5:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }
        while(choice!=5);
    }

    public void viewStaff(Scanner sc){
        int choice;
        do{
            System.out.println("\nSelect the staffs you want to display");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            //pass the List they want to filter
            switch(choice){
                case 1:
                    //Doctor List
                    break;
                case 2:
                    //Pharmacist List
                    filterStaff(sc, new ArrayList<>(PharmacistsAcc.getPharmacists()));
                    break;
                case 3:
                    //pass Administrator List
                    filterStaff(sc, new ArrayList<>(AdminsAcc.getAdmins()));
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

        } while(choice!=4);
    }

    public void filterStaff(Scanner sc, List<User> copiedList){
        int filter;
        do {
            System.out.println("\nSelect the way staffs are displayed");
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

    public void addStaff(Scanner sc){
        int choice;
        do{
            System.out.println("\nChoose the staff to add");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            //pass the List they want to filter
            switch(choice){
                case 1:
                    //DoctorsAcc.addDoctor();
                    System.out.println("New Doctor Added!");
                    break;
                case 2:
                    PharmacistsAcc.addPharmacist();
                    break;
                case 3:
                    AdminsAcc.addAdmin();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

        } while(choice!=4);
    }

    //update staff info findbyID
    public void updateStaff(Scanner sc){
        int choice;
        String staffID;
        do{
            System.out.println("\nChoose the staff to update");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            //pass the List they want to filter
            switch(choice){
                case 1:
                    //DoctorsAcc.addDoctor();
                    System.out.println("Doctor Updated!");
                    break;
                case 2:
                    System.out.print("Enter the Pharmacist ID to update: ");
                    staffID = sc.nextLine();
                    PharmacistsAcc.updateStaff(staffID);
                    break;
                case 3:
                    System.out.print("Enter the Admin ID to update: ");
                    staffID = sc.nextLine();
                    AdminsAcc.updateAdmin(staffID, sc);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

        } while(choice!=4);
    }

    public void removeStaff(Scanner sc){
        int choice;
        String staffID;
        do{
            System.out.println("\nChoose the staff to remove");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.println("3. Administrator");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            //pass the List they want to remove
            switch(choice){
                case 1:
                    //DoctorsAcc.removeDoctor(staffID);
                    System.out.print("Enter the Doctor ID to remove: ");
                    staffID = sc.nextLine();
                    System.out.println("Doctor Removed!");
                    break;
                case 2:
                    System.out.print("Enter the Pharmacist ID to remove: ");
                    staffID = sc.nextLine();
                    PharmacistsAcc.removePharmacist(staffID);
                    break;
                case 3:
                    System.out.print("Enter the Admin ID to remove: ");
                    staffID = sc.nextLine();
                    AdminsAcc.removeAdmin(staffID); 
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }

        } while(choice!=4);
    }

    /*
     * Inventory Management
     */
    public void ViewMedicationInven(){

    }
    public void ManageMedicationInven(){

    }
    public void ApproveReplenishment(){

    }

    /*
     * Appointment Management
     */
}
