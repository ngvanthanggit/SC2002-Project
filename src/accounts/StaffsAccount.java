package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.CSVutils;
import user.User;

public class StaffsAccount {
    //store all user objects
    private List<User> staffs = new ArrayList<>();
    
    //read in csv file of staffs
    public static List<User> loadStaffs(){
        String fileString = "Data//Staff_List.csv";

        Map<String, Integer> userMapping = new HashMap<>();
        userMapping.put("hospitalID", 0);
        userMapping.put("name", 1);
        userMapping.put("role", 2);
        userMapping.put("gender", 3);
        userMapping.put("age", 4);

        List<Object> usersMappList = CSVutils.readCSVMulti(fileString, userMapping, "User");

        List<User> staffList = new ArrayList<>();
        
        for(Object staff: usersMappList){
            if(staff instanceof User){
                staffList.add((User)staff);
            }
        }

        return staffList;
    }

    // Method to load patients into the instance variable
    public void loadInstanceStaffs() {

        //patients now hold the list of patients from the CSV file
        this.staffs = loadStaffs(); 
        if (staffs.isEmpty()) {
            System.out.println("No staffs were loaded.");
        } else {
            System.out.println("Staffs successfully loaded: " + staffs.size());
        }
    }

    public List<User> getStaffs() {
        return staffs;
    }

    public void addStaff(User user){
        //add new users to the staff List
        staffs.add(user);
    }
}
