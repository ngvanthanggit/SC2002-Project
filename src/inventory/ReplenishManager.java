package inventory;
import java.util.*;
//import java.time.LocalDate;


import io.*;

public class ReplenishManager {
    private static List<ReplenishRequest> replenishList = new ArrayList<>();
    private static int idCounter = 0;
    private static String originalPath = "../Data//Original/Replenish_List.csv";
    private static String updatedPath = "../Data//Updated/Replenish_List(Updated).csv";

    public static void loadReplenish(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }
        replenishList.clear();

        // Define column mapping for CSV reading
        Map<String, Integer> replenishColumnMapping = new HashMap<>();
        replenishColumnMapping.put("RequestID", 0);
        replenishColumnMapping.put("Medicine", 1);
        replenishColumnMapping.put("Quantity", 2);
        replenishColumnMapping.put("RequestedBy", 3);
        replenishColumnMapping.put("RequestDate", 4);
        replenishColumnMapping.put("Status", 5);
        replenishColumnMapping.put("ApprovalDate", 6);

        // Load requests from CSV file
        replenishList = CSVread.readReplenishCSV(filePath, replenishColumnMapping);

        if (replenishList.isEmpty()) {
            System.out.println("No items were loaded.");
        } else {
            System.out.println("Inventory successfully loaded: " + replenishList.size());

            String lastRequestID = replenishList.get(replenishList.size() - 1).getRequestID();
            idCounter = extractIdNumber(lastRequestID);
            
        }
    }

    private static int extractIdNumber(String requestId) {
        String numericPart = requestId.replaceAll("[^\\d]", ""); // Removes non-digit characters
        return Integer.parseInt(numericPart); // Convert to integer
    }

    public static List<ReplenishRequest> getReplenishList() {
        return replenishList;
    }

    public void submitReplenish(String itemName, int replenishQuantity) {
        // Generate a unique ID for the request
        String requestID = "REQ" + String.format("%04d", idCounter++);
        
        // Create a new replenish request
        ReplenishRequest request = new ReplenishRequest(requestID, itemName, replenishQuantity);
        
        // Add to the list and write to CSV
        replenishList.add(request);
        CSVwrite.writeCSV(updatedPath, request);
        
        System.out.println("Replenish request submitted for " + itemName + " (" + replenishQuantity + " units).");
    }
    
    public void approveReplenish(ReplenishRequest request) {
        request.setRequestStatus(RequestStatus.APPROVED);
        request.setApprovalDate(LocalDate.now());
        CSVwrite.writeCSV(updatedPath, request); // Update CSV to reflect approval
        System.out.println("Replenish request for " + request.getItemName() + " approved.");
    }

    public void rejectReplenish(ReplenishRequest request) {
        request.setRequestStatus(RequestStatus.REJECTED);
        CSVwrite.writeCSV(updatedPath, request); // Update CSV to reflect rejection
        System.out.println("Replenish request for " + request.getItemName() + " rejected.");
    }
}
