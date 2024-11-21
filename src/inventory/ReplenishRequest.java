package inventory;

import java.time.LocalDate;

import main.MainUI;

/**
 * Represents a replenishment request for inventory items. Each request contains details
 * about the item being requested, the quantity, the requester, the request date, and its status.
 */
public class ReplenishRequest {
    private String requestID;          // Unique identifier for each request
    private String itemName;           // Medicine name
    private int replenishQuantity;     // Quantity requested
    private String requestedBy;        // ID or name of the pharmacist making the request
    private LocalDate requestDate;     // Date of the request
    private RequestStatus status;      // Status of the request (e.g., PENDING, APPROVED, REJECTED)
    private LocalDate approvalDate;    // Date the request was approved or rejected

    /**
     * Constructor to create a new replenishment request.
     * The request date is set to the current date, and the status is set to PENDING.
     * The name of the user who made the request is set from the currently logged-in user.
     * 
     * @param requestID The unique identifier for the request
     * @param itemName The name of the item to replenish
     * @param replenishQuantity The quantity of the item to be replenished
     * @throws IllegalStateException if no user is currently logged in
     */
    public ReplenishRequest(String requestID, String itemName, int replenishQuantity) {
        this.requestID = requestID;
        this.itemName = itemName;
        this.replenishQuantity = replenishQuantity;
        if (MainUI.getLoggedInUser() != null) {
            this.requestedBy = MainUI.getLoggedInUser().getName(); // Use the logged-in user's name
        } else {
            throw new IllegalStateException("No user is currently logged in.");
        }
    
        this.requestDate = LocalDate.now();      // Automatically set to the current date
        this.status = RequestStatus.PENDING;     // Default status is PENDING
        this.approvalDate = null;                // Approval date is null until approved
    }

    /**
     * Constructor for creating a replenishment request with all details provided.
     * This constructor is used when reading data from a persistent source (e.g., CSV).
     * 
     * @param requestID The unique identifier for the request
     * @param itemName The name of the item to replenish
     * @param replenishQuantity The quantity of the item to be replenished
     * @param requestedBy The name of the person who requested the replenishment
     * @param requestDate The date when the request was made
     * @param status The current status of the request
     * @param approvalDate The date when the request was approved or rejected
     */
    public ReplenishRequest(String requestID, String itemName, int replenishQuantity, String requestedBy, LocalDate requestDate, RequestStatus status, LocalDate approvalDate) {
        this.requestID = requestID;
        this.itemName = itemName;
        this.replenishQuantity = replenishQuantity;
        this.requestedBy = requestedBy;
        this.requestDate = requestDate;
        this.status = status;
        this.approvalDate = approvalDate;
    }

    // Getters

    /**
     * Returns the request ID.
     * 
     * @return The unique request ID
     */
    public String getRequestID() {
        return requestID;
    }
    
    /**
     * Returns the name of the item to be replenished.
     * 
     * @return The name of the item
     */
    public String getItemName() {
        return itemName; 
    }

    /**
     * Returns the quantity of the item to be replenished.
     * 
     * @return The replenishment quantity
     */
    public int getReplenishQuantity() {
        return replenishQuantity;
    }

    /**
     * Returns the name of the person who requested the replenishment.
     * 
     * @return The name of the requester
     */
    public String getRequestedBy() {
        return requestedBy;
    }

    /**
     * Returns the date when the replenish request was made.
     * 
     * @return The request date
     */
    public LocalDate getRequestDate() {
        return requestDate;
    }

    /**
     * Returns the current status of the request (e.g., PENDING, APPROVED, REJECTED).
     * 
     * @return The status of the request
     */
    public RequestStatus getRequestStatus() {
        return status;
    }

    /**
     * Returns the date when the request was approved or rejected.
     * 
     * @return The approval date
     */
    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    // Setters 

    /**
     * Sets the status of the replenishment request. If the status is APPROVED or REJECTED,
     * the approval date is automatically set to the current date.
     * 
     * @param status The new status of the request
     */
    public void setRequestStatus(RequestStatus status) {
        this.status = status;
        if (status == RequestStatus.APPROVED || status == RequestStatus.REJECTED) {
            this.approvalDate = LocalDate.now(); // Set approval date only when approved or rejected
        }
    }

    /**
     * Sets the approval date of the request.
     * 
     * @param approvalDate The new approval date
     */
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     * Sets the request date for the replenishment request.
     * 
     * @param requestDate The new request date
     */
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Sets the name of the person who requested the replenishment.
     * 
     * @param requestedBy The name of the requester
     */
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    /**
     * Returns a string representation of the replenish request with all its details.
     * 
     * @return A formatted string containing the request's information
     */
    public String getRequestInfo() {
        return String.format(
            "[RequestID = %s, Medicine Name = %s, Replenish Quantity = %d, Requested By = %s, Request Date = %s, Status = %s, Approval Date = %s]",
            requestID, itemName, replenishQuantity, requestedBy, 
            requestDate != null ? requestDate.toString() : "N/A", // Convert LocalDate to string
            status != null ? status.toString() : "N/A",           // Convert Enum to string
            approvalDate != null ? approvalDate.toString() : "N/A" // Convert LocalDate to string
        );
    }
    
}

