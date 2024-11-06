package user;
//parent class of all User classes

public class User {
    private String hospitalId;
    private String name;
    private String role;
    private String gender;
    private int age; //changeable
    private String password; //changeable

    //default creator if needed
    public User(){
        this(null, null, null, null, 0, null);
    }

    public User(String hospitalID, String name, String role,
                String gender, int age, String password){
        //implement those found in excel sheet first
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

    //get Methods()
    public String getHospitalID() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPassword(){
        return password;
    }

    //set Methods()
    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password){
        this.password = password;
        System.out.println("Password Changed.");
    }

    //other Methods()
    public String userInfo(){
        return String.format("User[StaffID=%s, Name=%s, Role=%s, Gender=%s, Age=%d, Password=%s]", 
                            hospitalId, name, role, gender, age, password);
    }
    
}
