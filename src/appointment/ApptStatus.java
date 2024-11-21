package appointment;

/**
 * This enum represents the different possible states of an appointment. 
 * It is used to track the current status of the appointment, whether 
 * the appointment is scheduled, confirmed, completed, or cancelled.
 */
public enum ApptStatus {

    /** The appointment has been scheduled. */
    SCHEDULED,

    /** The appointment has been cancelled. */
    CANCELLED,

    /** The appointment has been completed. */
    COMPLETED,

    /** The appointment has not been scheduled yet. */
    NOT_SCHEDULED,

    /** The appointment is pending acceptance. */
    PENDING,

    /** The appointment has been confirmed and is scheduled. */
    CONFIRMED
}
