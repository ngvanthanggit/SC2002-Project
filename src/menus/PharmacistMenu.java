package menus;

import java.util.*;

public interface PharmacistMenu extends CommonMenu{

    // show all items & low level warning
    public void viewInventory(Scanner sc);
    public void submitReplenish(Scanner sc);
}
