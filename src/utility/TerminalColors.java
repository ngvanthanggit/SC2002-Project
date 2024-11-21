package utility;

/**
 * The {@code TerminalColors} class provides ANSI color codes for different user roles.
 * It includes predefined color constants and a method to retrieve a color based on the user's role.
 * The color codes are intended for use in terminal output to highlight specific roles.
 */
public class TerminalColors {
    // ANSI color codes

    /** ANSI reset color code to reset the terminal text color. */
    public static final String RESET = "\u001B[0m";

    /** ANSI color code for yellow, typically used for Administrator role. */
    public static final String YELLOW = "\u001B[93m";   // For Administrator

    /** ANSI color code for green, typically used for Pharmacist role. */
    public static final String GREEN = "\u001B[32m"; // For Pharmacist

    /** ANSI color code for blue, typically used for Doctor role. */
    public static final String BLUE = "\u001B[34m";  // For Doctor

    /** ANSI color code for cyan, typically used for Patient role. */
    public static final String CYAN = "\u001B[36m";  // For Patient

    /**
     * Retrieves the ANSI color code based on the specified user role.
     *
     * @param role the user role, which can be "Administrator", "Pharmacist", "Doctor", or "Patient".
     * @return the corresponding ANSI color code for the specified role.
     *         If the role is unrecognized, the default reset color code is returned.
     */
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

