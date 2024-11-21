package utility;

import user.*;
import accounts.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordResetManager {
    private static List<PasswordResetRequest> requests = new ArrayList<>();
    private static final String DEFAULT_PASSWORD = "password1234";

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

    public static PasswordResetRequest approveRequest(int index) {
        if (index < 0 || index >= requests.size()) {
            System.out.println("Invalid request index.");
            return null;
        }
        return requests.remove(index);
    }

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
