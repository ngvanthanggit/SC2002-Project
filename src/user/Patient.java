package user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import accounts.DoctorsAcc;

import java.util.ArrayList;
import java.util.List;

import menus.PatientMenu;
//import appointmentManager.*;

import schedule.*;
import medicalrecord.*;
import appointment.*;

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
            System.out.println("1. View Medical Records");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointments Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointments");
            System.out.println("6. Cancel Appointments");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointments Outcomes Reports");
            System.out.println("9. Logout");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume Line

            switch (choice) {
                case 1:
                    viewMedicalRecords();
                    break;
                case 2:
                    updatePersonalInfo();
                    break;
                case 3:
                    viewAvailableApptSlots();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    viewScheduledAppointments();
                    break;
                case 8:
                    viewPastApptOutcomes();
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice > 0 && choice < 10);
    }

    public void viewMedicalRecords() {
        System.out.println("Viewing Medical Records");
        List<MedicalRecord> medicalRecords = MedicalRecordManager.getMedicalRecordsByPatient(getHospitalID());
        if (medicalRecords.isEmpty()) {
            System.out.println("No medical records found.");
        } else {
            System.out.println("Medical Records: ");
            for (MedicalRecord record : medicalRecords) {
                System.out.println(record.getRecordDetailsWithPersonalInfo());
            }
        }
    }

    public void updatePersonalInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Update Personal Information");
        System.out.println("Enter New Contact Information: ");
        String contactInfo = sc.nextLine();
        setContactInfo(contactInfo);
        System.out.println("Contact Information Updated Successfully.");
    }

    public void viewDoctorTimeSlots(String doctorID) {
        List<Schedule> doctorSchedules = ScheduleManager.getScheduleOfDoctor(doctorID);
        if (doctorSchedules.isEmpty()) {
            System.out.println("No available schedules found for doctor.");
        } else {
            System.out.println("Available Appointment Slots: ");
            for (Schedule schedule : doctorSchedules) {
                System.out.println(schedule.getScheduleDetails());
            }
        }
    }

    public boolean selectTimeSlot(String doctorID) {
        Scanner sc = new Scanner(System.in);
        LocalDate date;
        LocalTime time;

        while (true) {
            System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
            date = LocalDate.parse(sc.next());
            System.out.println("Enter Appointment Time (HH:MM): ");
            time = LocalTime.parse(sc.next());

            if (!ScheduleManager.isDoctorAvailable(doctorID, date, time)) {
                System.out.println("Doctor is not available at the specified time.");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = sc.nextInt();
                if (choice != 1) {
                    return false;
                } else {
                    continue;
                }
            }
            break;
        }

        // Schedule an appointment at the specified date and time
        AppointmentManager.requestAppointment(doctorID, getHospitalID(), date, time);
        System.out.println("Appointment Scheduled Successfully.");

        return true;
    }

    public void viewAvailableApptSlots() {
        System.out.println("Viewing Available Appointment Slots");
        System.out.println("Enter Doctor ID: ");
        Scanner sc = new Scanner(System.in);
        String doctorID = sc.nextLine();

        viewDoctorTimeSlots(doctorID);
    }

    public void viewScheduledAppointments() {
        System.out.println("Viewing Scheduled Appointments");
        // get all appointments for the patient
        List<Appointment> patientAppts = AppointmentManager.getAppointmentsByPatient(getHospitalID());
        // filter out the scheduled appointments
        List<Appointment> scheduledAppts = new ArrayList<>();
        for (Appointment appt : patientAppts) {
            if (appt.getStatus() == ApptStatus.SCHEDULED) {
                scheduledAppts.add(appt);
            }
        }
        if (scheduledAppts.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Scheduled Appointments: ");
            for (Appointment appt : scheduledAppts) {
                System.out.println(appt.getApptInfo());
            }
        }
    }

    public void scheduleAppointment() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Doctor ID: ");
        String doctorID = sc.nextLine();

        // display the available time slots for the doctor
        viewDoctorTimeSlots(doctorID);
        // select an available time slot
        selectTimeSlot(doctorID);
    }

    public void rescheduleAppointment() {
        System.out.println("Rescheduling Appointment");

        // display the scheduled appointments
        viewScheduledAppointments();

        // select an appointment to reschedule
        Scanner sc = new Scanner(System.in);
        Appointment appt = null;
        while (true) {
            System.out.println("Enter Appointment ID to Reschedule: ");
            String apptID = sc.nextLine();

            appt = AppointmentManager.getAppointment(apptID);
            if (appt == null || !appt.getPatient().getHospitalID().equals(getHospitalID())) {
                System.out.println("Appointment not found. Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = sc.nextInt();
                if (choice != 1) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }

        // display the available time slots for the doctor
        Doctor doctor = DoctorsAcc.getDoctorByID(appt.getDoctor().getHospitalID());
        viewDoctorTimeSlots(doctor.getHospitalID());

        // select an available time slot. If the time slot is selected, cancel the
        // previous appointment
        if (selectTimeSlot(doctor.getHospitalID())) {
            // cancel the previous appointment
            AppointmentManager.removeAppointment(appt);
            // save to file
            AppointmentManager.duplicateAppointments();
        }
    }

    public void cancelAppointment() {
        System.out.println("Cancelling Appointment");

        // display the scheduled appointments
        viewScheduledAppointments();

        // select an appointment to cancel
        Scanner sc = new Scanner(System.in);
        Appointment appt = null;
        while (true) {
            System.out.println("Enter Appointment ID to Cancel: ");
            String apptID = sc.nextLine();

            appt = AppointmentManager.getAppointment(apptID);
            if (appt == null || !appt.getPatient().getHospitalID().equals(getHospitalID())) {
                System.out.println("Appointment not found. Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice = sc.nextInt();
                if (choice != 1) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }

        // cancel the appointment
        AppointmentManager.removeAppointment(appt);
    }

    public void viewPastApptOutcomes() {
        System.out.println("Viewing Past Appointment Outcomes");
        List<Appointment> patientAppts = AppointmentManager.getAppointmentsByPatient(getHospitalID());

        List<Appointment> pastAppts = new ArrayList<>();
        for (Appointment appt : patientAppts) {
            if (appt.getStatus() == ApptStatus.COMPLETED) {
                pastAppts.add(appt);
            }
        }

        if (pastAppts.isEmpty()) {
            System.out.println("No past appointments found.");
        } else {
            System.out.println("Past Appointments Outcomes: ");
            for (Appointment appt : pastAppts) {
                System.out.println(appt.getApptInfo());
            }
        }
    }
}
