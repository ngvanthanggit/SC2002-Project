package inventory;

import java.time.LocalDate;
import java.util.*;

import utility.*;

/**
 * Manages replenish requests for inventory items, including loading requests, displaying
 * the replenish list, and processing new, approved, or rejected requests.
 */
public class ReplenishManager {
    private static List<ReplenishRequest> replenishList = new ArrayList<>();

    // private InventoryItem item;
    // private static int idCounter = 0;
    private static String originalPath = "Data//Original/Replenish_List.csv";
    private static String updatedPath = "Data//Updated/Replenish_List(Updated).csv";

    /**
     * Loads the replenish requests from a CSV file.
     * <p>
     * If it is the first run, it loads from the original path and clears the updated file.
     * If not, it loads from the updated path.
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
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
        }
    }

    /*
     * private static int extractIdNumber(String requestId) {
     * String numericPart = requestId.replaceAll("[^\\d]", ""); // Removes non-digit
     * characters
     * return Integer.parseInt(numericPart); // Convert to integer
     * }
     */

     /**
     * Returns the list of all replenish requests.
     * 
     * @return A list of replenish requests
     */
    public static List<ReplenishRequest> getReplenishList() {
        return replenishList;
    }

    /**
     * Displays all replenish requests currently loaded in the system.
     * If the list is empty, it notifies the user.
     */
    public static void displayReplenishList() {
        if (replenishList.isEmpty()) {
            System.out.println("There are no replenish request at the moment.");
        } else {
            System.out.println("\nThe Requests in the CSV file are: ");
            for (ReplenishRequest request : replenishList) {
                System.out.println(request.getRequestInfo());
            }
        }
    }

    /** Duplicates the replenish list by writing it to the updated CSV file. */
    public static void duplicateReplenish() {
        CSVwrite.writeCSVList(updatedPath, replenishList);
    }

    /**
     * Generates and adds a new replenish request for an item.
     * The request is then written to the CSV file.
     * 
     * @param itemName The name of the item to replenish
     * @param replenishQuantity The quantity of the item to replenish
     */
    public static void generateReplenish(String itemName, int replenishQuantity) {
        // Generate a unique ID for the request
        // String requestID = "REQ" + String.format("%03d", ++idCounter);
        String requestID = IDGenerator.generateID(
                "REQ",
                replenishList,
                ReplenishRequest::getRequestID, // Use method reference for ID extraction
                3);

        // Create a new replenish request
        ReplenishRequest request = new ReplenishRequest(requestID, itemName, replenishQuantity);

        // Add to the list and write to CSV
        replenishList.add(request);
        CSVwrite.writeCSV(updatedPath, request);

        System.out.println("Replenish request submitted for " + itemName + " (" + replenishQuantity + " units).");
    }

    /**
     * Finds and returns a replenish request by its request ID.
     * 
     * @param requestID The ID of the request to find
     * @return The {@link ReplenishRequest} object, or {@code null} if not found
     */
    public static ReplenishRequest findReplenishRequest(String requestID){
        for (ReplenishRequest request : replenishList){
            if (request.getRequestID().equals(requestID)){
                return request;
            }
        }
        return null;
    }

    /**
     * Manages a replenish request by allowing an administrator to approve or reject it.
     * 
     * @param requestID The ID of the request to manage
     * @param sc A {@link Scanner} object for user input.
     */
    public static void manageReplenish(String requestID, Scanner sc){
        ReplenishRequest request = findReplenishRequest(requestID); //returns request object

        if(request!=null){
            System.out.println("Do you want to approve or reject request " + requestID + "?");
            System.out.print("Choice Y/N: ");
            char choice = Character.toUpperCase(sc.next().charAt(0));
            switch (choice){
                case 'Y':
                    approveReplenish(request);
                    break;
                case 'N':
                    rejectReplenish(request);
                    break;
                default: System.out.println("Invalid choice!");
            }
        } else {
            System.out.println("Replenish Request " + requestID + " not found.");
        }
    }

    /**
     * Approves a replenish request and updates the inventory stock.
     * The request's status is set to APPROVED and the approval date is set to the current date.
     * 
     * @param request The {@link ReplenishRequest} to approve
     */
    public static void approveReplenish(ReplenishRequest request) {
        if(request.getRequestStatus() == RequestStatus.PENDING){
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setApprovalDate(LocalDate.now()); //set approvalDate to now
            
            //update inventory stock level
            InventoryItem item = InventoryManager.findItemByName(request.getItemName());
            int updateQuantity = item.getQuantity() + request.getReplenishQuantity();
            
            //change this to use add/update method inventormyManager later
            InventoryManager.updateItemStockHelper(request.getItemName(), updateQuantity);
            duplicateReplenish(); 
            System.out.println("Replenish request for " + request.getItemName() + " approved.");
        } else {
            System.out.println("You can't approve " + request.getRequestID() + ". It has already been " + request.getRequestStatus());
        }
        
    }

    /**
     * Rejects a replenish request.
     * The request's status is set to REJECTED and the list is updated in the CSV file.
     * @param request The {@link ReplenishRequest} to reject
     */
    public static void rejectReplenish(ReplenishRequest request) {
        if(request.getRequestStatus() == RequestStatus.PENDING){
            request.setRequestStatus(RequestStatus.REJECTED);
            duplicateReplenish(); //update CSV for rejection
            System.out.println("Replenish request for " + request.getItemName() + " rejected.");
        } else {
            System.out.println("You can't reject " + request.getRequestID() + ". It has already been " + request.getRequestStatus());
        }
    }
}