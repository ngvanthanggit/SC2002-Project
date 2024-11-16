package inventory;

import java.time.LocalDate;

import main.HMSApp;

public class ReplenishRequest {
    private String requestID;          // Unique identifier for each request
    private String itemName;           // Medicine name
    private int replenishQuantity;     // Quantity requested
    private String requestedBy;        // ID or name of the pharmacist making the request
    private LocalDate requestDate;     // Date of the request
    private RequestStatus status;      // Status of the request (e.g., PENDING, APPROVED, REJECTED)
    private LocalDate approvalDate;    // Date the request was approved or rejected

    // Constructor for creating a new replenishment request
    public ReplenishRequest(String requestID, String itemName, int replenishQuantity) {
        this.requestID = requestID;
        this.itemName = itemName;
        this.replenishQuantity = replenishQuantity;
        if (HMSApp.getLoggedInUser() != null) {
            this.requestedBy = HMSApp.getLoggedInUser().getName(); // Use the logged-in user's name
        } else {
            throw new IllegalStateException("No user is currently logged in.");
        }
    
        this.requestDate = LocalDate.now();      // Automatically set to the current date
        this.status = RequestStatus.PENDING;     // Default status is PENDING
        this.approvalDate = null;                // Approval date is null until approved
    }

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
    public String getRequestID() {
        return requestID;
    }
    
    public String getItemName() {
        return itemName; 
    }

    public int getReplenishQuantity() {
        return replenishQuantity;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public RequestStatus getRequestStatus() {
        return status;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    // Setters for status and approval date
    public void setRequestStatus(RequestStatus status) {
        this.status = status;
        if (status == RequestStatus.APPROVED || status == RequestStatus.REJECTED) {
            this.approvalDate = LocalDate.now(); // Set approval date only when approved or rejected
        }
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestInfo() {
        return String.format(
            "Inventory[Request ID=%s, Medicine Name=%s, Replenish Quantity=%d, Requested By=%s, Request Date=%s, Status=%s, Approval Date=%s]",
            requestID, itemName, replenishQuantity, requestedBy, 
            requestDate != null ? requestDate.toString() : "N/A", // Convert LocalDate to string
            status != null ? status.toString() : "N/A",           // Convert Enum to string
            approvalDate != null ? approvalDate.toString() : "N/A" // Convert LocalDate to string
        );
    }
    
}

