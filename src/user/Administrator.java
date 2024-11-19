package user;
import interfaces.AdminApptInterface;
import interfaces.InvenManageInterface;
import interfaces.ReplenishManageInterface;
import interfaces.StaffManageInterface;
import userInterface.AdminApptUI;
import userInterface.AdminUI;
import userInterface.InvenManageUI;
import userInterface.ReplenishManageUI;
import userInterface.StaffManageUI;

/**
 * represents a Administrator in HMS
 * extends {@link User} class
 */
public class Administrator extends User  {
    
    /** default constructor for creating a Administrator with no attributes */
    public Administrator(){
        super(null, null, null, null, 0, null);
    }

    /**
     * constructs a Administrator object
     * @param hospitalID The hospital ID of the administrator
     * @param name The name of the administrator
     * @param role The role of the administrator (must be {@link Role#Administrator}).
     * @param gender The gender of the administrator
     * @param age The age of the administrator
     * @param password The password of the administrator
     */
    public Administrator(String hospitalID, String name, Role role,
                String gender, int age, String password){
        super(hospitalID, name, role, gender, age, password);
    }

    /**
     * Formatted string containing Administrator information
     * Overrides the {@link User#userInfo()} method
     * @return A formatted string with Administrator details
     */
    @Override
    public String userInfo(){
        return String.format("[AdminID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s]",
        getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword());
    }

    /**
     * Displays the user interface for the Administrator
     * Overrides the {@link User#displayUI()} method
     */
    @Override 
    public void displayUI(){
        StaffManageInterface staffManageInterface = new StaffManageUI();
        AdminApptInterface adminApptInterface = new AdminApptUI();
        ReplenishManageInterface replenishManageInterface = new ReplenishManageUI();
        InvenManageInterface invenManageInterface = new InvenManageUI(replenishManageInterface);
        
        AdminUI adminUI = new AdminUI(this, staffManageInterface, adminApptInterface,
                invenManageInterface, replenishManageInterface);
        adminUI.displayMenu();
    }
}