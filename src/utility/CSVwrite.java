package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import appointmentManager.Appointment;
import inventory.InventoryItem;
import leave.Leave;
import medicalrecord.MedicalRecord;
import schedule.Schedule;
import appointment.Appointment;

/**
 * A utility class that provides methods for writing objects and lists of objects to a CSV file. 
 * <p>
 * The class supports writing both single objects and lists of objects. For each object, 
 * it checks the type and writes the appropriate CSV format. If the file does not exist or 
 * the header is missing, it generates a default header based on the object type.
 */
public class CSVwrite {

    /**
     * Writes a single object to a CSV file. The method checks the type of the object
     * and writes its data into the CSV file in the appropriate format.
     * 
     * @param <T> The type of object being written to the CSV file.
     * @param filePath The path to the CSV file.
     * @param object The object whose data will be written to the CSV file.
     */
    public static <T> void writeCSV(String filePath, T object) {

        if (object == null) {
            System.out.println("No data to write to CSV file.");
            return;
        }

        FileWriter output = null;
        try {
            // set to true to append not replace exisiting data, set to false to write over
            // all data
            output = new FileWriter(filePath, true);

            // handles InventoryItem
            if (object instanceof InventoryItem) {
                output.write(((InventoryItem) object).toCSVRow() + '\n');
            }
            // handles all User based classes
            else {
                List<String> data = new ArrayList<>();

                // get all class&inherited fields
                for (Field field : getAllFields(object.getClass())) {
                    field.setAccessible(true); // Allows access to private fields
                    Object value = field.get(object);

                    // Convert data to readable format and check for null values
                    if (value != null && value.getClass().isArray()) {
                        data.add(Arrays.toString((Object[]) value)); // Convert array to string
                    } else if (value != null) {
                        data.add(value.toString()); // Convert other fields to string
                    } else {
                        data.add(""); // Empty string if null
                    }
                }

                // write only the data values (not field names) as a CSV row
                output.write(String.join(",", data) + "\n");
                // output.write("\n");
                // System.out.println("Data written successfully!");
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /*
     * when calling this method we will pass the List<Object> and maybe filePath
     * use <T> to define that the method will accept any generic types of objects in
     * the List
     * makes the method reusable for different List of Objects
     */
    public static <T> void writeCSVList(String filePath, List<T> objects) {

        if (objects == null || objects.isEmpty()) {
            System.out.println("No data to write to CSV file.");
            return;
        }

        String header;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            header = reader.readLine(); // Read and retain the header line
        } catch (IOException e) {
            System.out.println(filePath);
            System.out.println("Failed to read header.");
            return;
        }

        FileWriter output = null;
        try {
            output = new FileWriter(filePath, false); // Overwrite mode

            // Write the header first
            if (header != null) {
                output.write(header + "\n");
            } else {
                if (objects.get(0) instanceof MedicalRecord) {
                    header = "Doctor ID,Patient ID,Diagnoses,Prescriptions,Treatment Plan";
                    output.write(header + "\n");
                }
            }

            // Write each object data as a new row
            for (T object : objects) {
                // handles InventoryItem
                if (object instanceof InventoryItem) {
                    output.write(((InventoryItem) object).toCSVRow() + "\n");
                }
                // handles MedicalRecord
                else if (object instanceof MedicalRecord) {
                    MedicalRecord record = (MedicalRecord) object;
                    output.write(record.toCSVRow() + "\n"); // Use toCSVRow from MedicalRecord
                }
                // handles Schedule
                else if (object instanceof Schedule) {
                    output.write(((Schedule) object).toCSVRow() + "\n");
                }
                // handles Appointments
                else if (object instanceof Appointment) {
                    output.write(((Appointment) object).toCSVFormat() + "\n");
                } 
                // handles Leave
                else if (object instanceof Leave){
                    output.write(((Leave) object).toCSVFormat() + "\n");
                }
                // handles user based classes
                else {
                    List<String> data = new ArrayList<>();

                    // Get all class & inherited fields
                    for (Field field : getAllFields(object.getClass())) {
                        field.setAccessible(true);
                        Object value = field.get(object);

                        if (value != null && value.getClass().isArray()) {
                            data.add(Arrays.toString((Object[]) value));
                        } else if (value != null) {
                            data.add(value.toString());
                        } else {
                            data.add("");
                        }
                    }
                    output.write(String.join(",", data) + "\n");
                }
            }

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves all fields from the class hierarchy of the given class, including fields from 
     * its superclasses. This method uses reflection to get both declared fields of the class 
     * and its superclasses.
     * 
     * @param clazz The class whose fields are to be retrieved.
     * @return A list of {@link Field} objects representing all fields of the class and its 
     *         superclasses.
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classHierarchy = new ArrayList<>(); // List of Classes

        // building the class hierarchy list from super -> child
        while (clazz != null) {
            classHierarchy.add(0, clazz); // sorting
            clazz = clazz.getSuperclass();
        }

        for (Class<?> currentClass : classHierarchy) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
        }
        return fields;
    }
}
