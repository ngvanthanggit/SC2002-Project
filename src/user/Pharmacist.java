package user;
import interfaces.ApptOutcomeInterface;
import interfaces.InvenManageInterface;
import interfaces.ReplenishManageInterface;
import userInterface.ApptOutcomeUI;
import userInterface.InvenManageUI;
import userInterface.PharmacistUI;
import userInterface.ReplenishManageUI;

/**
 * represents a Pharmacist in HMS
 * extends {@link User} class
 */
public class Pharmacist extends User{

    /** default constructor for creating a Pharmacist with no attributes */
    public Pharmacist(){
        super(null, null, null, null, 0, null);
    }

    /**
     * constructs a Pharmacist object
     * @param hospitalID The hospital ID of the pharmacist
     * @param name The name of the pharmacist
     * @param role The role of the pharmacist (must be {@link Role#Pharmacist}).
     * @param gender The gender of the pharmacist
     * @param age The age of the pharmacist
     * @param password The password of the pharmacist
     */
    public Pharmacist(String hospitalID, String name, Role role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    /**
     * Formatted string containing Pharmacist information
     * Overrides the {@link User#userInfo()} method
     * @return A formatted string with Pharmacist details
     */
    @Override
    public String userInfo() {
        return String.format("[PharmacistID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword());
    }

    /**
     * Displays the user interface for the Pharmacist
     * Overrides the {@link User#displayUI()} method
     */
    @Override
    public void displayUI(){
        ApptOutcomeInterface apptOutcomeInterface = new ApptOutcomeUI();
        ReplenishManageInterface replenishManageInterface = new ReplenishManageUI();
        InvenManageInterface invenManageInterface = new InvenManageUI(replenishManageInterface);

        PharmacistUI pharmUI = new PharmacistUI(this, invenManageInterface, replenishManageInterface, apptOutcomeInterface);
        pharmUI.displayMenu();
    }
}
