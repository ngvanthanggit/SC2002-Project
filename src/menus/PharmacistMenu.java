package menus;
import java.util.*;

public interface PharmacistMenu {

    public void handleSelection(int option, Scanner scanner);
    //show all items & low level warning
    public void viewInventory();
}
