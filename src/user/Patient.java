package user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import menus.PatientMenu;
import appointmentManager.*;

public class Patient extends User implements PatientMenu {
    private String dateOB; // date of birth
    private String bloodType;
    private String contactInfo; // changeable

    // constructors
    public Patient() {
        super();
        this.dateOB = null;
        this.bloodType = null;
        this.contactInfo = null;
    }

    public Patient(String hospitalID, String name, Role role,
            String gender, int age, String password, String dateOB,
            String bloodType, String contactInfo) {
        super(hospitalID, name, role, gender, age, password);
        this.dateOB = dateOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    public Patient(String hospitalID, String name) {
        super(hospitalID, name);
    }

    // get Methods()
    public String getDateOB() {
        return dateOB;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getHospitalID() {
        return super.getHospitalID();
    }

    // set Methods()
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Override SuperClass Methods
    @Override
    public void logout() {
        System.out.println("Patient Logging Out.");
        return;
    }

    @Override
    public String userInfo() {
        return String.format(
                "[PatientID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s, DateOB = %s, BloodType = %s, ContactInfo = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword(), dateOB, bloodType,
                contactInfo);
    }

    // Interface Menus
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nPatient Menu ");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Schedule appointment");
            System.out.println("2. Reschedule appointment");
            System.out.println("3. Cancel appointment");
            System.out.println("4. View confirmed appointments");
            System.out.println("5. View appointment status");
            System.out.println("6. View appointment outcomes");
            System.out.println("7. Logout");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume Line

            switch (choice) {
                case 1: // done
                    scheduleAppointment(sc);
                    break;
                case 2: // done
                    rescheduleAppointment();
                    break;
                case 3: // done
                    cancelAppointment();
                    break;
                case 4:
                    viewScheduledAppointments();
                    break;
                case 5: // done
                    viewAppointmentStatus();
                    break;
                case 6: // done
                    viewAppointmentOutcomes();
                    break;
                case 7:
                    logout();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 7);
    }

    public void scheduleAppointment(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter Patient ID: ");
        String patientID = sc.nextLine();

        System.out.println("Enter Doctor ID: ");
        String doctorID = sc.nextLine();

        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.next());

        System.out.println("Enter Appointment Time in 24-hour clock format (HH:MM): ");
        LocalTime time = LocalTime.parse(sc.next());

        service.scheduleAppointment(patientID, doctorID, date, time);
    }

    public void rescheduleAppointment() {

    }

    public void cancelAppointment() {

    }

    public void viewScheduledAppointments() {

    }

    public void viewAppointmentStatus() {

    }

    public void viewAppointmentOutcomes() {

    }

    @Override
    public void scheduleAppointment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scheduleAppointment'");
    }
}
