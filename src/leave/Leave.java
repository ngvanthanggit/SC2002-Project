package leave;

import java.time.LocalDate;
import user.User;

/**
 * The {@code Leave} class represents a leave request in the system.
 * <p>
 * It contains details about the leave request, including the leave ID, staff member requesting the leave,
 * the date of the leave, the status of the leave, and the reason for the leave.
 * <p>
 * This class provides methods to access and modify leave details, format leave information for display,
 * and convert leave data to CSV format.
 */

public class Leave {

    private String leaveID;
    private User staff;
    private LocalDate date;
    private LeaveStatus status;
    private String reason;

    /**
     * Default constructor for creating an empty leave request.
     * <p>
     * Initializes all attributes to {@code null}.
     */
    public Leave(){
        this(null, null, null, null, null);
    }

    /**
     * Constructs a leave request with the specified details.
     * 
     * @param leaveID The unique identifier for the leave request.
     * @param staff   The {@link User} who requested the leave.
     * @param date    The {@link LocalDate} of the leave.
     * @param status  The {@link LeaveStatus} indicating the status of the leave.
     * @param reason  The reason provided for the leave.
     */
    public Leave(String leaveID, User staff, LocalDate date, LeaveStatus status, String reason){
        this.leaveID = leaveID;
        this.staff = staff;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }

    //getter methods

    /**
     * Gets the unique identifier for the leave request.
     * 
     * @return The leave ID as a {@code String}.
     */
    public String getLeaveID(){
        return leaveID;
    }

    /**
     * Gets the staff member associated with the leave request.
     * 
     * @return The {@link User} who requested the leave.
     */
    public User getStaff(){
        return staff;
    }

    /**
     * Gets the date of the leave request.
     * 
     * @return The {@link LocalDate} of the leave.
     */
    public LocalDate getDate(){
        return date;
    }

    /**
     * Gets the current status of the leave request.
     * 
     * @return The {@link LeaveStatus} of the leave.
     */
    public LeaveStatus getLeaveStatus(){
        return status;
    }

    /**
     * Gets the reason provided for the leave request.
     * 
     * @return The reason as a {@code String}.
     */

    public String getReason(){
        return reason;
    }

    //setter methods()

    /**
     * Sets the unique identifier for the leave request.
     * 
     * @param leaveID The new leave ID.
     */
    public void setLeaveID(String leaveID){
        this.leaveID = leaveID;
    }

    /**
     * Sets the staff member associated with the leave request.
     * 
     * @param staff The {@link User} who requested the leave.
     */
    public void setStaff(User staff){
        this.staff = staff;
    }

    /**
     * Sets the date of the leave request.
     * 
     * @param date The new {@link LocalDate} for the leave.
     */
    public void setDate(LocalDate date){
        this.date = date;
    }

    /**
     * Sets the status of the leave request.
     * 
     * @param status The new {@link LeaveStatus} for the leave.
     */
    public void setLeaveStatus(LeaveStatus status){
        this.status = status;
    }

    /**
     * Sets the reason provided for the leave request.
     * 
     * @param reason The new reason as a {@code String}.
     */
    public void setReason(String reason){
        this.reason = reason;
    }

    /**
     * Returns a formatted string containing information about the leave request.
     * 
     * @return A {@code String} with details of the leave request.
     */
    public String leaveInfo(){
        return String.format("[Leave ID = %s, Staff ID = %s, Date = %s, Status = %s, Reason = %s]",
        getLeaveID(), getStaff().getHospitalID(), getDate(), getLeaveStatus(), getReason());
    }

    /**
     * Converts the leave request into a CSV-formatted string.
     * 
     * @return A {@code String} representing the leave request in CSV format.
     */
    public String toCSVFormat() {
        return leaveID + " ," + staff.getHospitalID() + " ," + date + " ," + status + " ," + reason;
    }
}
