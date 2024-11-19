package interfaces;

import java.util.Scanner;
import user.Role;

public interface StaffManageInterface {

    public void viewStaff(Scanner sc, Role role);
    public void addStaff(Scanner sc, Role role);
    public void updateStaff(Scanner sc, Role role);
    public void removeStaff(Scanner sc, Role role);
}
