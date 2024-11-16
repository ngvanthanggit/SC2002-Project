package user;
//parent class of all User classes

import menus.CommonMenus;

public class User implements CommonMenus {
    private String hospitalId;
    private String name;
    private Role role;
    private String gender;
    private int age; // changeable
    private String password; // changeable

    // default creator if needed
    public User() {
        this(null, null, null, null, 0, null);
    }

    public User(String hospitalID, String name, Role role,
            String gender, int age, String password) {
        // implement those found in excel sheet first
        this.hospitalId = hospitalID;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.password = password;
    }

    public User(String hospitalID, String name) {
        this.hospitalId = hospitalID;
        this.name = name;
    }

    // get Methods()
    public String getHospitalID() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    // set Methods()
    public void setName(String name){
        this.name = name;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // other Methods()
    public String userInfo() {
        return String.format("User[StaffID=%s, Name=%s, Role=%s, Gender=%s, Age=%d, Password=%s]",
                hospitalId, name, role, gender, age, password);
    }

    public void displayMenu(){
        System.out.println("User Menu.");
    }

    public void logout(){
        System.out.println("User Logging Out.");
        return;
    }
}
