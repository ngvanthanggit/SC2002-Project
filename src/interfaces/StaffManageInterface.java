package interfaces;

import java.util.List;
import java.util.Scanner;

import user.Role;
import user.User;

public interface StaffManageInterface {

    public void viewStaff(Scanner sc, Role role);
    public void filterStaff(Scanner sc, Role role, List<User> userList);
    public void addStaff(Scanner sc, Role role);
    public void updateStaff(Scanner sc, Role role);
    public void removeStaff(Scanner sc, Role role);
}
