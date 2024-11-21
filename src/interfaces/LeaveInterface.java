package interfaces;

import java.util.Scanner;

import user.Doctor;

public interface LeaveInterface {

    public void applyLeave(Scanner sc, Doctor doctor);
    public void displayAllLeaveRequests();
    public void displayPendingLeave(Scanner sc, Doctor doctor);
    public void displayLeaveOutcome(Scanner sc, Doctor doctor);
    public void manageLeaveRequests(Scanner sc);
    public void updateLeave(Scanner sc, Doctor doctor);
}
