package leave;

/**
 * The {@code LeaveStatus} enum represents the possible statuses of a leave request.
 * <p>
 * It is used to track the state of a leave request throughout its lifecycle.
 */
public enum LeaveStatus {
    /**
     * Indicates that the leave request has been approved.
     */
    APPROVED,

    /**
     * Indicates that the leave request is pending and awaiting approval or rejection.
     */
    PENDING,

    /**
     * Indicates that the leave request has been rejected.
     */
    REJECTED;
}
