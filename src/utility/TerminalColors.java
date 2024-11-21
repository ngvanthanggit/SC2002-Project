package utility;

public class TerminalColors {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[93m";   // For Administrator
    public static final String GREEN = "\u001B[32m"; // For Pharmacist
    public static final String BLUE = "\u001B[34m";  // For Doctor
    public static final String CYAN = "\u001B[36m";  // For Patient

    // Method to get color by role
    public static String getColorByRole(String role) {
        switch (role) {
            case "Administrator":
                return YELLOW;
            case "Pharmacist":
                return GREEN;
            case "Doctor":
                return BLUE;
            case "Patient":
                return CYAN;
            default:
                return RESET;
        }
    }
}

