package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVclear {
    //clear data from CSV but keep the header
    public static void clearFile(String filePath) {
        try {
            //read the header line
            String header;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                header = reader.readLine();
            }

            //overwrite the file, keeping only the header, resulting in empty file
            try (FileWriter writer = new FileWriter(filePath, false)) { //false for overwrite mode
                if (header != null) {
                    writer.write(header + "\n"); //rewrite the header
                }
            }

            //System.out.println("CSV data cleared, header preserved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeRow(){
        
    }
}
