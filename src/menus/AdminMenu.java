package menus;
import java.util.Scanner;

public interface AdminMenu {

    public void displayMenu();

    //staff management
    public void manageHospitalStaff();
    public void viewStaff(Scanner sc);
    public void addStaff(Scanner sc);
    public void updateStaff(Scanner sc);
    public void removeStaff(Scanner sc);

    //inventory management
    public void inventoryManagement();
    public void viewMedicationInven();
    public void manageMedicationInven();
    public void approveReplenishment();

    //appointment managemenet

}

