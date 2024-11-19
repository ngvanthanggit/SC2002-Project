package interfaces;

import java.util.Scanner;

import inventory.Medicine;
import user.Role;

public interface InvenManageInterface {
    public void viewMedicationInven();
    public void viewLowStockInventory();
    public void viewInventory();
    public void chooseMedicine(Scanner sc, Role role);
    public void manageMedicine(Scanner sc, Medicine medicine);
    public void addStock(Scanner sc, Medicine medicine);
    public void deductStock(Scanner sc, Medicine medicine);
    public void updateStock(Scanner sc, Medicine medicine);
    public void updateLevelAlert(Scanner sc, Medicine medicine);
}
