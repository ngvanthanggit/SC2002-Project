package inventory;

/**
 * Represents the status of a replenishment request.
 */
public enum RequestStatus {

    /** The request is waiting for approval or rejection. */
    PENDING,

    /** The request has been approved. */
    APPROVED,

    /** The request has been rejected */
    REJECTED
}
