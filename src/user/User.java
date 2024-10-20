package user;
//parent clase of all User classes

public class User {
    private String hospitalId;
    private String gender;
    private String role;
    private String name;
    private int age; //changeable
    private String password; //changeable
    private String username; //changeable

    //default creator if needed
    public User(){
        this(null, null, null, null, 0);
    }

    public User(String hospitalID, String gender, String role,
                String name, int age){
        //implement those found in excel sheet first
        this.hospitalId = hospitalID;
        this.gender = gender;
        this.role = role;
        this.name = name;
        this.age = age;
    }

    //get Methods()
    public String getHospitalId() {
        return hospitalId;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    //set Methods()
    public void setAge(int age) {
        this.age = age;
    }

    //other Methods()
    public String userInfo(){
        return String.format("User[StaffID=%s, Name=%s, Role=%s, Gender=%s, Age=%d]", 
                            hospitalId, name, role, gender, age);
    }
    
}
