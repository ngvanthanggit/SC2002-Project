package userInterface;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import appointment.Appointment;
import appointment.AppointmentManager;
import appointment.ApptStatus;
import interfaces.DocApptInterface;
import interfaces.ScheduleInterface;
import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import medicalrecord.PrescriptionStatus;
import user.Doctor;
import user.User;
import utility.CSVwrite;
import utility.IDGenerator;

public class DocApptUI implements DocApptInterface{

    private final ScheduleInterface scheduleInterface;
    //constructor
    public DocApptUI(ScheduleInterface scheduleInterface){
        this.scheduleInterface = scheduleInterface;
    }

    public void viewAppointments(Doctor doctor) {
        System.out.println("\n|--- Doctor " + doctor.getName() + "s' Appointments ---|");
        System.out.printf("%s\n", "-".repeat(40));
        List<Appointment> appointments = AppointmentManager.getAppointmentsByDoctor(doctor.getHospitalID(),
                ApptStatus.SCHEDULED);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("The Upcoming Appointments are:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getApptInfo());
        }
    }

    public void AppointmentRequestHandler(Scanner sc, Doctor doctor) {
        //System.out.println("Appointment Request Handler");
        System.out.println("List of Appointment Requests:");
        List<Appointment> appointments = AppointmentManager.getAppointmentsByDoctor(doctor.getHospitalID(),
                ApptStatus.PENDING);
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointment requests found.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getApptInfo());
        }

        System.out.println("\nContinue to Accept/Decline Appointment Request?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Choice: ");
        int continueChoice = -1;
        try {
            continueChoice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Invalid input type. Please enter an Integer.");
            sc.nextLine(); // Consume the invalid input to prevent an infinite loop
        }

        if (continueChoice == 2) {
            return;
        }

        Appointment appointment = null;

        //accepting appointment requests
        while (true) {
            System.out.print("\nEnter Appointment ID: ");
            // sc.nextLine();
            // String appointmentID = sc.nextLine();
            String appointmentID = sc.next();
            System.out.println("Appointment ID: " + appointmentID);

            appointment = AppointmentManager.getAppointment(appointmentID);
            if (appointment == null || appointment.getDoctor().getHospitalID() != doctor.getHospitalID() ||
                    appointment.getStatus() != ApptStatus.PENDING) {
                if (appointment == null) {
                    System.out.println("Appointment is null.");
                } else if (appointment.getDoctor().getHospitalID() != doctor.getHospitalID()) {
                    System.out.println("Doctor ID does not match.");
                    System.out.println("Doctor ID: " + appointment.getDoctor().getHospitalID());
                    System.out.println("Your ID: " + doctor.getHospitalID());
                } else if (appointment.getStatus() != ApptStatus.PENDING) {
                    System.out.println("Appointment status is not pending.");
                    System.out.println("Appointment status: " + appointment.getStatus());
                }
                System.out.println("Invalid Appointment ID.");
                System.out.println("\nWould you like to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice: ");
                int choice =-1;
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Invalid input type. Please enter an Integer.");
                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                    continue; // Restart the loop to prompt the user again
                }

                if (choice == 2) {
                    return;
                }
                System.out.println("Again");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("\nWould you like to Accept or Decline the appointment?");
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            System.out.println("3. Exit");
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

            boolean done = true;
            switch (choice) {
                case 1:
                    acceptAppointmentRequest(appointment, doctor);
                    break;
                case 2:
                    declineAppointmentRequest(appointment);
                    break;
                case 3:
                    return;
                default:
                    done = false;
                    break;
            }
            if (done) {
                break;
            }
        }
    }

    public void acceptAppointmentRequest(Appointment appointment, Doctor doctor) {
        System.out.println("Accept Appointment Request");
        // set the appointment status to scheduled
        appointment.acceptAppointment();
        // save to file
        AppointmentManager.duplicateAppointments();
        // set availability for the doctor at this time slot: removing the time slot
        scheduleInterface.removeSchedule(appointment.getDate(), appointment.getTime(), doctor);
    }

    public void declineAppointmentRequest(Appointment appointment) {
        System.out.println("Decline Appointment Request");
        appointment.cancelAppointment();
        // save to file
        AppointmentManager.duplicateAppointments();
    }

    public void recordAppointmentOutcome(Scanner sc, Doctor doctor) {
        System.out.println("\nRecord Appointments Outcome");
        System.out.printf("%s\n", "-".repeat(30));

        // Listing out the scheduled appointments of the Doctor
        viewAppointments(doctor);
        Appointment appointment = null;

        while (true) {
            try {
                System.out.print("\nEnter Appointment ID: ");
                String appointmentID = sc.next();
                appointment = AppointmentManager.getAppointment(appointmentID);
                
                //validate whether an appropiate appointment found
                if (appointment == null || appointment.getDoctor().getHospitalID() != doctor.getHospitalID() ||
                        appointment.getStatus() != ApptStatus.SCHEDULED) {
                    System.out.println("Invalid Appointment ID.");
                    System.out.println("Would you like to try again?");
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
                    
                    if (choice == 2) {
                        return;
                    }
                    System.out.println("Try Again");
                    continue; //continue loop until user chooses 2.no
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter 1 or 2 for your choice.");
                sc.nextLine();
            }
            break; //valid appointment found, exit loop
        }

        //create empty record
        MedicalRecord record = new MedicalRecord();
        //set medical record ID

        //write down appointment outcomes
        System.out.println("Enter Consultation Notes: ");
        sc.nextLine();
        String consultationNotes = sc.nextLine();
        appointment.setConsultationNotes(consultationNotes);

        // Enter and set diagnoses
        System.out.println("Enter new diagnoses (separated by ';'): ");
        String newDiagnose = sc.nextLine();
        //List<String> diagnosesList = Arrays.asList(newDiagnose.split(";")); // Convert to List

        //check for correct medications and format
        System.out.println("Enter prescription in the format (Medication: Quantity) ");
        System.out.print("Enter prescription: ");
        String prescribedMedications = null;
        try {
            prescribedMedications = sc.nextLine().trim();
            appointment.setPrescribedMedications(prescribedMedications);
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong Medication Format");
        }

        //check for correct Service Type
        System.out.println("Enter Service Type: ");
        String serviceType = sc.nextLine();
        appointment.setServiceType(serviceType);

        // Enter and set treatment plans
        System.out.println("Enter new treatment plan (separated by ';'): ");
        String newTreatmentPlan = sc.nextLine();
        //List<String> treatmentPlansList = Arrays.asList(newTreatmentPlan.split(";")); // Convert to List

        //appointment completed
        AppointmentManager.completeAppointment(appointment);
        
        //create new Medical Record to signify Appointment Completed
        String medicalRID = IDGenerator.generateID("MR", MedicalRecordManager.getMedicalRecords(), 
                            MedicalRecord::getMedicalRID, 3);
        record.setMedicalRID(medicalRID);
        record.setDoctorID(appointment.getDoctor().getHospitalID());
        record.setPatientID(appointment.getPatient().getHospitalID());
        record.addDiagnose(newDiagnose);
        record.addPrescription(prescribedMedications);
        record.addTreatmentPlan(newTreatmentPlan);
        record.setStatus(PrescriptionStatus.PENDING);
        MedicalRecordManager.addMedicalRecord(record);
    }
}
