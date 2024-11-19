package interfaces;

import java.util.Scanner;
import user.Role;

public interface InvenManageInterface {
    public void viewMedicationInven();
    public void viewLowStockInventory();
    public void viewInventory();
    public void chooseMedicine(Scanner sc, Role role);
}
