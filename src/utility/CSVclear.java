package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for clearing data from CSV files while retaining the header row
 * CSV manipulation
 */
public class CSVclear {

    /**
     * Clears all data from the specified CSV file but preserves the header row
     * <p>
     * The method reads the first line of the CSV file (assumed to be the header) and then overwrites
     * the file with only this header line, effectively removing all other data.
     * 
     * @param filePath the path to the CSV file to be cleared
     * @throws IOException if an I/O error occurs while reading from or writing to the file.
     */
    public static void clearFile(String filePath) {
        try {
            //read the header line
            String header;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                header = reader.readLine();
            }

            //overwrite the file, keeping only the header, resulting in empty file
            //false for overwrite mode
            try (FileWriter writer = new FileWriter(filePath, false)) 
            {
                if (header != null) {
                    writer.write(header + "\n"); //rewrite the header
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
