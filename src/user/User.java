package user;

/**
 * Represents a user in the HMS
 * Parent Class for all users in our system
 */
public class User {
    private String hospitalId;
    private String name;
    private Role role;
    private String gender;
    private int age; 
    private String password; 

    /**
     * Default constructor for creating a user with no attributes
     * Initializes all fields to default values
     */
    public User() {
        this(null, null, null, null, 0, null);
    }

    /**
     * Constructs a user with the specified attributes
     *
     * @param hospitalID The hospital ID of the user
     * @param name The name of the user
     * @param role The role of the user located in {@link Role}
     * @param gender The gender of the user
     * @param age The age of the user
     * @param password The password of the user
     */
    public User(String hospitalID, String name, Role role,
            String gender, int age, String password) {
        this.hospitalId = hospitalID;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.password = password;
    }

    /*public User(String hospitalID, String name) {
        this.hospitalId = hospitalID;
        this.name = name;
    }*/

    // Getter Methods()

    /**
     * Returns the hospital ID of the user
     * @return the hospital ID
     */
    public String getHospitalID() {
        return hospitalId;
    }

    /**
     * Returns the name of the user
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the role of the user
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the gender of the user
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the age of the user
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the password of the user
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    //Setter Methods()

    /**
     * Updates name of the user
     * @param name
     */
    public void setName(String name){
        name.trim(); //ensure consistency
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.name = name;
    }

    /**
     * Updates gender of the user
     * @param gender
     */
    public void setGender(String gender){
        gender.trim(); //ensure consistency
        gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
        this.gender = gender;
    }

    /**
     * Updates age of the user
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Updates password of the user
     * @param password
     */
    public void setPassword(String password) {
        password.trim(); //ensure consistency
        this.password = password;
    }

    //Other Methods()

    /**
     * Formatted string containing user information
     * @return A formatted string with user details
     */
    public String userInfo() {
        return String.format("User[StaffID=%s, Name=%s, Role=%s, Gender=%s, Age=%d, Password=%s]",
                hospitalId, name, role, gender, age, password);
    }

    /**
     * displays user interface, will be overriden by subclasses
     */
    public void displayUI(){
        System.out.println("User UI Unavailable.");
    }

    /**
     * logs out user
     */
    public void logout(){
        System.out.println("User Logging Out.");
        return;
    }
}
