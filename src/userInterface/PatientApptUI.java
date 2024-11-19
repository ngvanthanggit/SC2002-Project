package userInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import accounts.DoctorsAcc;
import appointment.Appointment;
import appointment.AppointmentManager;
import appointment.ApptStatus;
import interfaces.PatientApptInterface;
import schedule.Schedule;
import schedule.ScheduleManager;
import user.User;
import user.Doctor;
import user.Patient;

public class PatientApptUI implements PatientApptInterface{

    //constructor
    public PatientApptUI(){}

    public void viewAvailableApptSlots(Scanner sc) {
        viewAvailableDoctors(sc);
        System.out.print("\nEnter Doctor ID: ");
        String doctorID = sc.nextLine();
        viewDoctorTimeSlots(doctorID);
    }

    public void viewAvailableDoctors(Scanner sc){
        List<User> doctors = DoctorsAcc.getDoctors();
        System.out.println("\n|-- Viewing Available Doctors --|");
        System.out.println("The Available Doctor IDs are: ");
        for (User doctor : doctors) {
            System.out.println(doctor.getHospitalID());
        }
    }

    public void viewDoctorTimeSlots(String doctorID) {
        List<Schedule> doctorSchedules = ScheduleManager.getScheduleOfDoctor(doctorID);
        if (doctorSchedules.isEmpty()) {
            System.out.println("No available schedules found for doctor.");
        } else {
            System.out.println("\nAvailable Appointment Slots: ");
            for (Schedule schedule : doctorSchedules) {
                System.out.println(schedule.getScheduleDetails());
            }
        }
    }

    public void scheduleAppointment(Scanner sc, Patient patient) {
        viewAvailableDoctors(sc);
        System.out.println("\nEnter the Doctor ID you would like to view: ");
        System.out.print("Enter Doctor ID: ");
        String doctorID = sc.nextLine();

        Doctor doctor = DoctorsAcc.findDoctorById(doctorID);
        //view when a doctor is found
        if(doctor!=null){
            // display the available time slots for the doctor
            viewDoctorTimeSlots(doctorID);
            // select an available time slot
            selectTimeSlot(sc, doctorID, patient);
        } else {
            System.out.println("The Doctor with this ID does not exist.");
            return;
        }
    }

    //breaks if wrong format is enterd for time and date
    public boolean selectTimeSlot(Scanner sc, String doctorID, Patient patient) {
        LocalDate date = null;
        LocalTime time = null;

        while (true) {
            try {
                // Prompt for and parse the appointment date
                System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                String dateInput = sc.next();
                date = LocalDate.parse(dateInput);

                // Prompt for and parse the appointment time
                System.out.print("Enter Appointment Time (HH:MM): ");
                String timeInput = sc.next();
                time = LocalTime.parse(timeInput);
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date or time format. Please use the correct format.");
                sc.nextLine(); // Clear the invalid input
                continue; // Restart the loop to prompt the user again
            }

            //check if the doctor is available at entered date and time
            if (!ScheduleManager.isDoctorAvailable(doctorID, date, time)) {
                System.out.println("\nDoctor is not available at the specified time.");
                System.out.println("Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice: ");
                int choice = -1;
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Invalid input type. Please enter an Integer.");
                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                    continue; // Restart the loop to prompt the user again
                }

                if (choice != 1) {
                    return false;
                } else {
                    continue;
                }
            }
            break;
        }

        // Schedule an appointment at the specified date and time
        AppointmentManager.requestAppointment(doctorID, patient.getHospitalID(), date, time);
        System.out.println("Appointment Scheduled Successfully.");

        return true;
    }

    public void rescheduleAppointment(Scanner sc, Patient patient) {
        System.out.println("Rescheduling Appointment");

        // display the scheduled appointments
        viewScheduledAppointments(patient);

        // select an appointment to reschedule
        Appointment appt = null;
        while (true) {
            System.out.println("Enter Appointment ID to Reschedule: ");
            String apptID = sc.nextLine();

            appt = AppointmentManager.getAppointment(apptID);
            if (appt == null || !appt.getPatient().getHospitalID().equals(patient.getHospitalID())) {
                System.out.println("Appointment not found. Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice =-1;
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Invalid input type. Please enter an Integer.");
                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                    continue; // Restart the loop to prompt the user again
                }
                
                if (choice != 1) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }

        // display the available time slots for the doctor
        Doctor doctor = DoctorsAcc.findDoctorById(appt.getDoctor().getHospitalID());
        viewDoctorTimeSlots(doctor.getHospitalID());

        // select an available time slot. If the time slot is selected, cancel the
        // previous appointment
        if (selectTimeSlot(sc, doctor.getHospitalID(), patient)) {
            // cancel the previous appointment
            AppointmentManager.removeAppointment(appt);
            // save to file
            AppointmentManager.duplicateAppointments();
        }
    }

    public void cancelAppointment(Scanner sc, Patient patient) {

        // display the scheduled appointments
        viewScheduledAppointments(patient);

        // select an appointment to cancel
        Appointment appt = null;
        while (true) {
            System.out.println("\nEnter Appointment ID to Cancel: ");
            String apptID = sc.nextLine();

            appt = AppointmentManager.getAppointment(apptID);
            if (appt == null || !appt.getPatient().getHospitalID().equals(patient.getHospitalID())) {
                System.out.println("Appointment not found. Do you want to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                int choice=-1;
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Invalid input type. Please enter an Integer.");
                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                    continue; // Restart the loop to prompt the user again
                }
                
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

    public void viewScheduledAppointments(Patient patient) {
        System.out.println("\nViewing Scheduled Appointments");
        // get all appointments for the patient
        List<Appointment> patientAppts = AppointmentManager.getAppointmentsByPatient(patient.getHospitalID());
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

    public void viewPastApptOutcomes(Patient patient) {
        List<Appointment> patientAppts = AppointmentManager.getAppointmentsByPatient(patient.getHospitalID());

        List<Appointment> pastAppts = new ArrayList<>();
        for (Appointment appt : patientAppts) {
            if (appt.getStatus() == ApptStatus.COMPLETED) {
                pastAppts.add(appt);
            }
        }

        if (pastAppts.isEmpty()) {
            System.out.println("No past appointments found for " + patient.getName());
        } else {
            System.out.println(patient.getName() + " Past Appointments Outcomes");
            for (Appointment appt : pastAppts) {
                System.out.println(appt.getApptInfo());
            }
        }
    }
}
