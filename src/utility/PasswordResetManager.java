package utility;

import user.*;
import accounts.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages password reset requests and operations.
 * <p>
 * This class provides functionality to add, display, approve, and process password reset requests.
 * It also handles resetting user passwords to a default value based on their role.
 */
public class PasswordResetManager {
    
    /** A list to store all {@link PasswordRequest} objects */
    private static List<PasswordResetRequest> requests = new ArrayList<>();
    
    /** The default password for every account */
    private static final String DEFAULT_PASSWORD = "password1234";

    /**
     * Adds a password reset request to the system.
     * <p>
     * Validates the user ID and name before adding the request.
     * 
     * @param request The {@link PasswordResetRequest} to be added.
     * @return {@code true} if the request was successfully added; {@code false} otherwise.
     */
    public static boolean addRequest(PasswordResetRequest request) {
        String userId = request.getUserId();
        String userName = request.getUserName();

        // Validate the user ID and name
        if (!validateUserIdAndName(userId, userName)) {
            System.out.println("Invalid User ID or Name. No password reset request added.");
            return false; // Indicate failure;
        }

        requests.add(request);
        System.out.println("Password reset request added: " + request);
        return true; // Indicate success
    }

    /**
     * Displays all pending password reset requests.
     * <p>
     * If no requests are pending, a message is displayed.
     */
    public static void displayRequests() {
        if (requests.isEmpty()) {
            System.out.println("No pending password reset requests.");
            return;
        }
        System.out.println("\nPending Password Reset Requests:");
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + ". " + requests.get(i));
        }
    }

    /**
     * Approves a password reset request and removes it from the list.
     * 
     * @param index The index of the request to approve (0-based).
     * @return The approved {@link PasswordResetRequest}, or {@code null} if the index is invalid.
     */
    public static PasswordResetRequest approveRequest(int index) {
        if (index < 0 || index >= requests.size()) {
            System.out.println("Invalid request index.");
            return null;
        }
        return requests.remove(index);
    }

    /**
     * Resets the password of a user to the default value.
     * <p>
     * The user type is determined based on their hospital ID prefix, and the password
     * is updated in the corresponding account database.
     * 
     * @param hospitalID The hospital ID of the user whose password is to be reset.
     */
    public static void resetPasswordToDefault(String hospitalID) {
        String prefix = extractPrefix(hospitalID);

        if (prefix == null) {
            System.out.println("Invalid hospital ID format.");
            return;
        }

        switch (prefix) {
            case "P1": // Patient
                PatientsAcc.updatePassword(hospitalID, DEFAULT_PASSWORD);
                break;
            case "D": // Doctor
                DoctorsAcc.updatePassword(hospitalID, DEFAULT_PASSWORD);
                break;
            case "A": // Administrator
                AdminsAcc.updatePassword(hospitalID, DEFAULT_PASSWORD);
                break;
            case "P": // Pharmacist
                PharmacistsAcc.updatePassword(hospitalID, DEFAULT_PASSWORD);
                break;
            default:
                System.out.println("Unknown user type for hospital ID: " + hospitalID);
        }
        System.out.println("Password reset to default ('" + DEFAULT_PASSWORD + "') for User: " + hospitalID);
    }

    /**
     * Extracts the prefix from a hospital ID to determine the user type.
     * 
     * @param hospitalID The hospital ID to extract the prefix from.
     * @return The prefix as a {@code String}, or {@code null} if the format is invalid.
     */
    private static String extractPrefix(String hospitalID) {
        // Extract the prefix based on your naming convention
        if (hospitalID == null || hospitalID.isEmpty()) {
            return null;
        }
        if (hospitalID.startsWith("P1")) { // Patient IDs
            return "P1";
        } else if (hospitalID.startsWith("D")) { // Doctor IDs
            return "D";
        } else if (hospitalID.startsWith("A")) { // Administrator IDs
            return "A";
        } else if (hospitalID.startsWith("P")) { // Pharmacist IDs
            return "P";
        }
        return null;
    }

    /**
     * Validates the user ID and name against the corresponding user database.
     * <p>
     * Checks if the user exists and if the provided name matches the name in the database.
     * 
     * @param userId   The user ID to validate.
     * @param userName The name of the user to validate.
     * @return {@code true} if the user ID and name are valid; {@code false} otherwise.
     */
    private static boolean validateUserIdAndName(String userId, String userName) {
        String prefix = extractPrefix(userId);

        if (prefix == null) {
            System.out.println("Invalid User ID format.");
            return false;
        }

        switch (prefix) {
            case "P1": // Patient
                Patient patient = PatientsAcc.findPatientById(userId);
                if (patient == null || !patient.getName().equalsIgnoreCase(userName)) {
                    return false;
                }
                break;
            case "D": // Doctor
                Doctor doctor = DoctorsAcc.findDoctorById(userId);
                if (doctor == null || !doctor.getName().equalsIgnoreCase(userName)) {
                    return false;
                }
                break;
            case "A": // Administrator
                Administrator admin = AdminsAcc.findAdminById(userId);
                if (admin == null || !admin.getName().equalsIgnoreCase(userName)) {
                    return false;
                }
                break;
            case "P": // Pharmacist
                Pharmacist pharmacist = PharmacistsAcc.findPharmById(userId);
                if (pharmacist == null || !pharmacist.getName().equalsIgnoreCase(userName)) {
                    return false;
                }
                break;
            default:
                System.out.println("Unknown user type for User ID: " + userId);
                return false;
        }

        return true;
    }
}
