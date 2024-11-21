package utility;

import java.util.List;

/**
 * Functional interface for generating an ID for an object
 * <p>
 * This interface is used in conjunction with the {@link IDExtractor} class.
 */
public class IDGenerator {
    /**
     * Generates a new unique ID based on the prefix and the list of existing objects.
     * 
     * @param prefix     The prefix for the ID (e.g., "P", "D", "A").
     * @param objects    The list of objects to check for existing IDs.
     * @param idGetter   A functional interface to extract IDs as strings from objects.
     * @param numDigits  The number of digits for the numeric part of the ID.
     * @return           A new unique ID in the format prefix + padded number.
     */

    public static <T> String generateID(String prefix, List<T> objects, IDExtractor<T> idGetter, int numDigits) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }

        int maxID = findMaxID(prefix, objects, idGetter);
        return prefix + String.format("%0" + numDigits + "d", maxID + 1);
    }

    /**
     * Finds the maximum numeric ID with the given prefix in a list of objects.
     * 
     * @param prefix    The prefix for filtering IDs.
     * @param objects   The list of objects to check for IDs.
     * @param idGetter  A functional interface to extract IDs as strings from objects.
     * @return          The maximum numeric value of IDs with the given prefix.
     */
    private static <T> int findMaxID(String prefix, List<T> objects, IDExtractor<T> idGetter) {
        int maxID = 0;

        for (T obj : objects) {
            String id = idGetter.getID(obj);
            if (id != null && id.startsWith(prefix)) {
                try {
                    int numericPart = Integer.parseInt(id.substring(prefix.length()));
                    maxID = Math.max(maxID, numericPart);
                } catch (NumberFormatException e) {
                    // Skip invalid IDs that don't match the numeric pattern.
                }
            }
        }

        return maxID;
    }
}
