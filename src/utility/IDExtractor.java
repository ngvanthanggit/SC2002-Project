package utility;
/**
 * Functional interface for extracting an ID from an object
 * <p>
 * This interface is used in conjunction with the {@link IDGenerator} class to
 * dynamically extract IDs from a list of objects for the purpose of generating
 * unique IDs
 * 
 * @param <T> The type of object from which the ID is to be extracted
 */
public interface IDExtractor<T> {
    /**
     * Extracts the ID from the given object
     * @param obj The object from which the ID is to be extracted
     * @return The ID as a string
     */
    String getID(T obj);
}
