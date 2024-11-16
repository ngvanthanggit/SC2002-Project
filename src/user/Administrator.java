package user;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


import accounts.AdminsAcc;

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
            System.out.println("\nAdmin Menus are listed Below");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View appointment details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Choice: ");   
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    viewManageMenu(sc);
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

    //1st subMenu + methods
    public void viewManageMenu(Scanner sc){
        int choice;
        do {
            System.out.println("\nView & Manage Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Hospital Staff");
            System.out.println("2. Update Hospital Staff");
            System.out.println("3. Remove Hospital Staff Member");
            System.out.println("4. Go Back");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch(choice) {
                case 1:
                    displayStaffs(sc);
                    break;

                case 2:
                    break;

                case 3:
                    removeStaff(sc);
                    break;

                case 4:
                    return; //go back to main menu
                default: 
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        }
        while(choice!=4);
    }
    public void displayStaffs(Scanner sc){
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

            //pass the List they want to filter
            switch(choice){
                case 1:
                    //Doctor List
                    break;
                case 2:
                    //Pharmacist List
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

            //sort using List.sort + comparator class based on custom sorting input
            switch(filter) {
                case 1:
                    //sort by Name
                    copiedList.sort(Comparator.comparing(User::getName));
                    break;
                case 2:
                    //sort by Role, then by Hospital ID if roles are the same
                    copiedList.sort(Comparator.comparing(User::getRole).thenComparing(User::getHospitalID));
                    break;
                case 3:
                    //sort by Gender
                    copiedList.sort(Comparator.comparing(User::getGender));
                    break;
                case 4:
                    //sort by Age
                    copiedList.sort(Comparator.comparingInt(User::getAge));
                    break;
                case 5:
                    // Display in the default order
                    copiedList = AdminsAcc.getAdmins(); // Reset to original order
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

    //update staff info findbyID
    public void updateStaff(Scanner sc){

    }
    public void removeStaff(Scanner sc){
        //need to use a check such that i cant remove myself
        sc.nextLine(); //consume
        System.out.print("Enter the Hospital ID of the staff to remove: ");
        String staffID = sc.nextLine();

        //use StaffsAccount method to remove
        AdminsAcc.removeAdmin(staffID); 
    }

    //2nd subMenu + methods
    public void ViewMedicationInven(){

    }
    public void ManageMedicationInven(){

    }
    public void ApproveReplenishment(){

    }
}
