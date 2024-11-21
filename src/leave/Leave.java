package leave;

import java.time.LocalDate;
import user.User;

public class Leave {

    private String leaveID;
    private User staff;
    private LocalDate date;
    private LeaveStatus status;
    private String reason;

    public Leave(){
        this(null, null, null, null, null);
    }

    public Leave(String leaveID, User staff, LocalDate date, LeaveStatus status, String reason){
        this.leaveID = leaveID;
        this.staff = staff;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }

    //getter methods
    public String getLeaveID(){
        return leaveID;
    }

    public User getStaff(){
        return staff;
    }

    public LocalDate getDate(){
        return date;
    }

    public LeaveStatus getLeaveStatus(){
        return status;
    }

    public String getReason(){
        return reason;
    }

    //getter methods()

    public void setLeaveID(String leaveID){
        this.leaveID = leaveID;
    }

    public void setStaff(User staff){
        this.staff = staff;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setLeaveStatus(LeaveStatus status){
        this.status = status;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String leaveInfo(){
        return String.format("[Leave ID = %s, Staff ID = %s, Date = %s, Status = %s, Reason = %s]",
        getLeaveID(), getStaff().getHospitalID(), getDate(), getLeaveStatus(), getReason());
    }

    public String toCSVFormat() {
        return leaveID + " ," + staff.getHospitalID() + " ," + date + " ," + status + " ," + reason;
    }
}
