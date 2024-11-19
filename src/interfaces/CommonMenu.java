package interfaces;

/**
 * The interface defines common functionalities for menus used in the HMS
 * This includes displaying menus and handling logout functionality
 */
public interface CommonMenu {
    /** Logs out user and terminates session */
    public void logout(); 
    /** Displays main menu options of the logged-in user*/
    public void displayMenu();
}
