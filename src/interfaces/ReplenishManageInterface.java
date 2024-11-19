package interfaces;

import java.util.Scanner;

import inventory.Medicine;
import user.Pharmacist;

public interface ReplenishManageInterface {
    public void viewReplenishRequest();
    public void addReplenish(Scanner sc, Medicine medicineName);
    public void manageReplenish(Scanner sc);
    public void submitReplenish(Scanner sc, Pharmacist pharmacist, 
    ReplenishManageInterface replenishManageInterface, InvenManageInterface invenManageInterface);
}
