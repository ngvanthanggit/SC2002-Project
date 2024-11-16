package inventory;
import java.util.*;

import io.*;

public class ReplenishManager {
    private static List<ReplenishRequest> replenishList = new ArrayList<>();
    private InventoryManager inventoryManager;

    private InventoryItem item;
    private static int idCounter = 0;
    private static String originalPath = "Data//Original/Replenish_List.csv";
    private static String updatedPath = "Data//Updated/Replenish_List(Updated).csv";

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
            System.out.println("Replenish List successfully loaded: " + replenishList.size());

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

    public static void displayReplenishList() {
        if(replenishList.isEmpty()) {
            System.out.println("There are no replenish request at the moment.");
        }
        else {
            System.out.println("\nThe request in the CSV file are: ");
            for(ReplenishRequest request: replenishList){
                System.out.println(request.getRequestInfo());
            }
        }
    }

    public static void duplicateReplenish(){
        CSVwrite.writeCSVList(updatedPath, replenishList);
    }

    public static void generateReplenish(String itemName, int replenishQuantity) {
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
        CSVwrite.writeCSV(updatedPath, request); // Update CSV to reflect approval
        item = inventoryManager.getItem(request.getItemName());
        inventoryManager.updateItem(request.getItemName(), item.getQuantity() + request.getReplenishQuantity());
        System.out.println("Replenish request for " + request.getItemName() + " approved.");
    }

    public void rejectReplenish(ReplenishRequest request) {
        request.setRequestStatus(RequestStatus.REJECTED);
        CSVwrite.writeCSV(updatedPath, request); // Update CSV to reflect rejection
        System.out.println("Replenish request for " + request.getItemName() + " rejected.");
    }
}