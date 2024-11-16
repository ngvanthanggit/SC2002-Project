import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


import appointmentManager.*;

//test case for Patient Appointment Class
public class AppointmentApp {

    private ApptScheduler scheduler = new ApptScheduler();
    private ApptManager repository = new ApptManager();
    private ApptService service = new ApptService(scheduler, repository);

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //SystemInitialisation.start();
        AppointmentApp app = new AppointmentApp();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to the Appointment Management System");

        boolean running = true;
        while (running) {
            System.out.println("");
            System.out.println("Select User:");
            System.out.println("1. Patient");
            System.out.println("2. Doctor");
            System.out.println("3. Administrator");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1: 
                    patientMenu();
                    break;
                case 2: 
                    doctorMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    
    public void patientMenu() {
        boolean running = true;
        while (running) {
            System.out.println("");
            System.out.println("Patient Menu ");
            System.out.println("--------------");
            System.out.println("1. Schedule appointment");
            System.out.println("2. Reschedule appointment");
            System.out.println("3. Cancel appointment");
            System.out.println("4. View confirmed appointments");
            System.out.println("5. View appointment status");
            System.out.println("6. View appointment outcomes");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1: // done
                    scheduleAppointment();
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void doctorMenu() {
        boolean running = true;
        while (running) {
            System.out.println("");
            System.out.println("Doctor Menu ");
            System.out.println("-------------");
            System.out.println("1. View personal schedule");
            System.out.println("2. Set availability for appointment");
            System.out.println("3. Accept appointment request");
            System.out.println("4. Decline appointment request");
            System.out.println("5. View upcoming appointments");
            System.out.println("6. Record appointment outcomes");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1: // done
                    viewPersonalSched();
                    break;
                case 2: // idk... HELP
                    setAvailability();
                    break;
                case 3: // done
                    acceptAppointment();
                    break;
                case 4: // done
                    declineAppointment();
                    break;
                case 5: // done
                    viewUpcomingAppointment();
                    break;
                case 6: // done
                    recordAppointmentOutcomes();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("");
            System.out.println("Administrator Menu ");
            System.out.println("--------------------");
            System.out.println("1. View updates of all appointments");
            System.out.println("2. View appointment details (using appointmentID)");
            System.out.println("3. View appointment details (using patientID)");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    viewAppointmentUpdates();
                    break;
                case 2: // done
                    viewAppointmentDetails();
                    break;
                case 3: // done
                    viewPatientAppointmentDetails();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // patient methods
    private void scheduleAppointment() {
        scanner.nextLine();
        System.out.println("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        System.out.println("Enter Doctor ID: ");
        String doctorID = scanner.nextLine();

        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.next());

        System.out.println("Enter Appointment Time in 24-hour clock format (HH:MM): ");
        LocalTime time = LocalTime.parse(scanner.next());

        service.scheduleAppointment(patientID, doctorID, date, time);
    } 

    private void rescheduleAppointment() {
        LocalDate newDate;
        LocalTime newTime;
        System.out.println("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();
        scanner.nextLine(); 
        
        try {
            System.out.println("Enter new date (YYYY-MM-DD): ");
            newDate = LocalDate.parse(scanner.next());
            System.out.println("Enter new time in 24-hour clock format (HH:MM): ");
            newTime = LocalTime.parse(scanner.next());
        } catch (DateTimeException e){
            System.out.println("Invalid date or time format. Please enter the date as YYYY-MM-DD and time as HH:MM");
            return;
        }
        service.rescheduleAppointment(appointmentID, newDate, newTime);
    }

    private void cancelAppointment() {
        System.out.println("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();

        service.cancelAppointment(appointmentID);
    }

    private void viewAppointmentStatus() {
        System.out.println("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();
        Appointment appointment = repository.findAppointmentByID(appointmentID);

        System.out.println("Appointment is " + appointment.getStatus());
    }
  
    private void viewAppointmentOutcomes() {
        scanner.nextLine();
        System.out.println("Would you like to view ALL past appointment outcomes? (Y/N)");
        String c = scanner.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            System.out.println("Enter hospital ID: ");
            String hospitalID = scanner.nextLine();
            System.out.println("");
            service.getAllOutcomeRecords(hospitalID);
        }
        else if (c.equalsIgnoreCase("N")) {
            int appointmentID = -1;
            System.out.println("Enter appointment ID: ");
            appointmentID = scanner.nextInt();
            if (appointmentID == -1) {
                System.out.println("AppointmentID invalid. Please try again.");
                return;
            }
            System.out.println("");
            service.getOutcomeRecord(appointmentID);
        }
        else System.out.println("Invalid choice. Please try again.");
    }

    private void viewScheduledAppointments() {
        System.out.println("Enter patient ID: ");
        scanner.nextLine();
        String patientID = scanner.nextLine();
        System.out.println("");
        service.viewScheduledAppointment(patientID);
    }

    // doctor methods
    private void viewPersonalSched(){
        scanner.nextLine();
        System.out.print("Enter hospital ID: ");
        String doctorID = scanner.nextLine();
        System.out.println("");
        service.viewPersonalSched(doctorID);
    }

    private void setAvailability() {

    }

    private void acceptAppointment(){
        System.out.print("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();

        service.acceptAppointment(appointmentID);
    }

    private void declineAppointment(){
        System.out.print("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();

        service.declineAppointment(appointmentID);
    }

    private void viewUpcomingAppointment(){
        System.out.print("Enter doctor ID: ");
        scanner.nextLine();
        String doctorID = scanner.nextLine();
        System.out.println("");
        service.viewUpcomingAppointment(doctorID);
    }

    private void recordAppointmentOutcomes() {
        System.out.println("Enter appointment ID: ");
        int appointmentID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.println("Has the appointment been completed? (Y/N)");
        String c = scanner.nextLine();
        if (c.equalsIgnoreCase("Y")) {
            service.completedAppointment(appointmentID);
        }
    
        System.out.println("Enter consultation notes: ");
        String consultationNotes = scanner.nextLine();
    
        System.out.println("Enter prescribed medications: ");
        String prescribedMedications = scanner.nextLine();
    
        System.out.println("Enter type of service provided: ");
        String serviceType = scanner.nextLine();
    
        service.recordAppointmentOutcomes(appointmentID, consultationNotes, prescribedMedications, serviceType);
    }
    
    // admin methods
    public void viewAppointmentUpdates(){
        service.viewAppointmentUpdates();
    }

    public void viewAppointmentDetails() {
        System.out.println("Enter appointment ID: ");
            int appointmentID = scanner.nextInt();

            if (appointmentID == -1) {
                System.out.println("AppointmentID invalid. Please try again.");
                return;
            }
            System.out.println("");
            service.getOutcomeRecord(appointmentID);
    }

    public void viewPatientAppointmentDetails(){
        System.out.print("Enter hospital ID: ");
        scanner.nextLine();
        String hospitalID = scanner.nextLine();
        System.out.println("");
        service.getAllOutcomeRecords(hospitalID);
    }
}  