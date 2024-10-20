package user;
//importing from package name.Class
import menus.AdminMenu;
import menus.CommonMenus;

public class Administrator extends User {

    //constructors
    public Administrator(){
        super(null, null, null, null, 0);
    }

    public Administrator(String hospitalID, String gender, String role,
                String name, int age){
        super(hospitalID, gender, role, name, age);
    }

    //methods
    public void ViewHospitalStaff(){

    }
    public void ManageHospitalStaff(){

    }
    public void ViewMedicationInven(){

    }
    public void ManageMedicationInven(){

    }
    public void ApproveReplenishment(){

    }
    public void Menu(){
        
    }
    public void Logout(){
        
    }
}
