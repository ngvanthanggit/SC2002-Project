package user;

import java.util.*;
import java.time.*;

import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import schedule.Schedule;
import schedule.ScheduleManager;
import appointment.Appointment;
import appointment.AppointmentManager;
import appointment.ApptStatus;

import menus.DoctorMenu;

public class Doctor extends User implements DoctorMenu {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    @Override
    public String userInfo() {
        return String.format("[DoctorID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword());
    }

    @Override
    public void logout() {
        System.out.println("Doctor Logging Out.");
        return;
    }

    public Doctor(String hospitalID, String name, Role role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    public void medicalRecordMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMedical Record Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Patient Records");
            System.out.println("2. Update Patient Records");
            System.out.println("3. Exit\n");

            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewPatientRecords();
                    break;
                case 2:
                    updatePatientRecords();
                    break;
                default:
                    break;
            }
        } while (choice > 0 && choice < 3);
    }

    public void scheduleMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSchedule Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Availability Schedule");
            System.out.println("2. Set Schedule");
            System.out.println("3. Exit\n");

            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewSchedule();
                    break;
                case 2:
                    setSchedule();
                    break;
                default:
                    break;
            }
        } while (choice > 0 && choice < 3);
    }

    public void appointmentMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAppointment Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. View Appointments");
            System.out.println("2. Appointment Requests");
            System.out.println("3. Record Appointments Outcome");
            System.out.println("4. Exit\n");

            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewAppointments();
                    break;
                case 2:
                    AppointmentRequestHandler();
                    break;
                case 3:
                    recordAppointmentOutcome();
                    break;
                default:
                    break;
            }
        } while (choice > 0 && choice < 4);
    }

    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nDoctor Menu");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.println("1. Medical Record Menu");
            System.out.println("2. Schedule Menu");
            System.out.println("3. Appointment Menu");
            System.out.println("4. Logout");

            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    medicalRecordMenu();
                    break;
                case 2:
                    scheduleMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    break;
            }
        } while (choice > 0 && choice < 4);
    }

    public void viewPatientRecords() {
        System.out.println("View Patient Records");
        System.out.println("Enter Patient ID: ");
        Scanner sc = new Scanner(System.in);
        String patientID = sc.nextLine();
        MedicalRecord record = MedicalRecordManager.getMedicalRecord(this.getHospitalID(), patientID);
        if (record == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }
        System.out.println(record.getRecordDetails());
    }

    public void updatePatientRecords() {
        System.out.println("Update Patient Records");
        System.out.println("Enter Patient ID: ");
        Scanner sc = new Scanner(System.in);
        String patientID = sc.nextLine();
        MedicalRecord record = MedicalRecordManager.getMedicalRecord(this.getHospitalID(), patientID);
        if (record == null) {
            System.out.println("No medical record found for this patient.");
            return;
        }

        boolean updated = false;
        while (!updated) {
            System.out.println("What would you like to update?");
            System.out.println("1. Diagnoses");
            System.out.println("2. Prescriptions");
            System.out.println("3. Treatment Plan");
            System.out.print("Choice: ");
            int choice = sc.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.println("Do you want to add or clear it?");
            System.out.println("1. Add");
            System.out.println("2. Clear");
            System.out.print("Choice: ");
            int choice2 = sc.nextInt();

            if (choice2 < 1 || choice2 > 2) {
                System.out.println("Invalid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (choice2 == 1) {
                        System.out.println("Enter new diagnoses: ");
                        sc.nextLine();
                        String newDiagnose = sc.nextLine();
                        record.addDiagnose(newDiagnose);
                    } else if (choice2 == 2) {
                        record.clearDiagnoses();
                    }
                    break;
                case 2:
                    if (choice2 == 1) {
                        System.out.println("Enter new prescription: ");
                        sc.nextLine();
                        String newPrescription = sc.nextLine();
                        record.addPrescription(newPrescription);
                    } else if (choice2 == 2) {
                        record.clearPrescriptions();
                    }
                    break;
                case 3:
                    if (choice2 == 1) {
                        System.out.println("Enter new treatment plan: ");
                        sc.nextLine();
                        String newTreatmentPlan = sc.nextLine();
                        record.addTreatmentPlan(newTreatmentPlan);
                    } else if (choice2 == 2) {
                        record.clearTreatmentPlans();
                    }
                    break;
                default:
                    break;
            }
            updated = true;
        }
        MedicalRecordManager.duplicateMedicalRecord();
        System.out.println("Record updated successfully.");
    }

    public void addSchedule(LocalDate date, LocalTime time) {
        if (ScheduleManager.checkValidTime(date, time)) {
            List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(this.getHospitalID());
            boolean isDateInSchedule = false;
            for (Schedule schedule : schedules) {
                if (schedule.getDate().isEqual(date)) {
                    isDateInSchedule = true;
                    schedule.addTimeSlot(time);
                    break;
                }
            }
            if (!isDateInSchedule) {
                Schedule newSchedule = new Schedule(this.getHospitalID(), date, List.of(time));
                ScheduleManager.addSchedule(newSchedule);
            }
            System.out.println("Time slot added successfully.");

            // save to file
            ScheduleManager.duplicateSchedule();
        }
    }

    public void addSchedule(LocalDate date, List<LocalTime> timeSlots) {
        // storing the valid time slots
        List<LocalTime> validTimeSlots = new ArrayList<>();

        // checking if the date is already in the schedule
        boolean isDateInSchedule = false;
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(this.getHospitalID());
        for (Schedule schedule : schedules) {
            if (schedule.getDate().isEqual(date)) {
                isDateInSchedule = true;
                // if the date is already in the schedule, add the new time slots to the
                // existing time slots
                for (LocalTime timeSlot : timeSlots) {
                    boolean isTimeSlotValid = true;

                    List<LocalTime> existingTimeSlots = schedule.getTimeSlots();
                    for (LocalTime existingTimeSlot : existingTimeSlots) {
                        if (timeSlot.equals(existingTimeSlot)) {
                            isTimeSlotValid = false; // existed
                            break;
                        }
                    }
                    if (isTimeSlotValid) {
                        schedule.addTimeSlot(timeSlot); // add the new time slot
                        validTimeSlots.add(timeSlot);
                    }
                }
            }
        }
        // if the date is not in the schedule, create a new schedule
        if (!isDateInSchedule) {
            Schedule newSchedule = new Schedule(this.getHospitalID(), date, timeSlots);
            ScheduleManager.addSchedule(newSchedule);
            validTimeSlots.addAll(timeSlots);
        }

        System.out.println("Time slots added successfully (excluding invalid time slots) are:");
        System.out.println(validTimeSlots);

        // save to file
        ScheduleManager.duplicateSchedule();
    }

    public void removeSchedule(LocalDate date, LocalTime time) {
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(this.getHospitalID());
        for (Schedule schedule : schedules) {
            if (schedule.getDate().isEqual(date)) {
                schedule.removeTimeSlot(time);
                System.out.println("Time slot removed successfully.");
                // save to file
                ScheduleManager.duplicateSchedule();
                return;
            }
        }
        System.out.println("No time slot found for this date.");
    }

    public void viewSchedule() {
        System.out.println("View Schedule");
        List<Schedule> schedules = ScheduleManager.getScheduleOfDoctor(this.getHospitalID());
        if (schedules.isEmpty()) {
            System.out.println("No schedules found.");
            return;
        }
        System.out.println("The Schedules are:");
        for (Schedule schedule : schedules) {
            System.out.println(schedule.getScheduleDetails());
        }
        System.out.println(
                "*The time slots are in 1h intervals. E.g. \"09:00\" means the doctor is available from 9:00 to 10:00 etc.");
    }

    public void setSchedule() {
        System.out.println("Set Schedule");
        Scanner sc = new Scanner(System.in);

        LocalDate date;
        while (true) {
            System.out.println("\nEnter the date (yyyy-mm-dd): ");
            String dateStr = sc.nextLine();
            date = LocalDate.parse(dateStr);

            // checking if the date is in the future
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Invalid date. Please enter a future date.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("\nEnter the time slots (e.g. 09:00, 10:00, 11:00): ");
            System.out.println(
                    "*The time slots are in 1h intervals. E.g. \"09:00\" means the doctor is available from 9:00 to 10:00 etc.");
            String timeSlots = sc.nextLine();
            List<String> newTimeSlotsStr = Arrays.asList(timeSlots.split(", "));
            List<LocalTime> newTimeSlots = new ArrayList<>();

            // convert and filter out the invalid time slots
            for (String timeSlotStr : newTimeSlotsStr) {
                LocalTime timeSlot = LocalTime.parse(timeSlotStr);
                if (ScheduleManager.checkValidTime(date, timeSlot)) {
                    newTimeSlots.add(LocalTime.parse(timeSlotStr));
                    continue;
                }
            }

            // need to have at least one valid time slot. Ask the user to enter again
            if (newTimeSlots.isEmpty()) {
                System.out.println("All the entered time slots are invalid. Please enter a future time.");
                continue;
            }

            // all all the new time slots to the schedule
            addSchedule(date, newTimeSlots);
            break;
        }
    }

    public void viewAppointments() {
        System.out.println("View Appointments");
        List<Appointment> appointments = AppointmentManager.getAppointmentsByDoctor(this.getHospitalID(),
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

    public void AppointmentRequestHandler() {
        System.out.println("Appointment Request Handler");
        System.out.println("List of Appointment Requests:");
        List<Appointment> appointments = AppointmentManager.getAppointmentsByDoctor(this.getHospitalID(),
                ApptStatus.PENDING);
        if (appointments.isEmpty()) {
            System.out.println("No appointment requests found.");
            return;
        }
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getApptInfo());
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Continue to Accept/Decline Appointment Request?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Choice: ");
        int continueChoice = sc.nextInt();
        if (continueChoice == 2) {
            return;
        }

        Appointment appointment = null;

        while (true) {
            System.out.print("Enter Appointment ID: ");
            // sc.nextLine();
            // String appointmentID = sc.nextLine();
            String appointmentID = sc.next();
            System.out.println("Appointment ID: " + appointmentID);

            appointment = AppointmentManager.getAppointment(appointmentID);
            if (appointment == null || appointment.getDoctor().getHospitalID() != this.getHospitalID() ||
                    appointment.getStatus() != ApptStatus.PENDING) {
                if (appointment == null) {
                    System.out.println("Appointment is null.");
                } else if (appointment.getDoctor().getHospitalID() != this.getHospitalID()) {
                    System.out.println("Doctor ID does not match.");
                    System.out.println("Doctor ID: " + appointment.getDoctor().getHospitalID());
                    System.out.println("Your ID: " + this.getHospitalID());
                } else if (appointment.getStatus() != ApptStatus.PENDING) {
                    System.out.println("Appointment status is not pending.");
                    System.out.println("Appointment status: " + appointment.getStatus());
                }
                System.out.println("Invalid Appointment ID.");
                System.out.println("Would you like to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                if (choice == 2) {
                    return;
                }
                System.out.println("Again");
                continue;
            }
            break;
        }

        while (true) {
            System.out.println("Would you like to Accept or Decline the appointment?");
            System.out.println("1. Accept");
            System.out.println("2. Decline");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            boolean done = true;
            switch (choice) {
                case 1:
                    acceptAppointmentRequest(appointment);
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

    public void acceptAppointmentRequest(Appointment appointment) {
        System.out.println("Accept Appointment Request");
        // set the appointment status to scheduled
        appointment.acceptAppointment();
        // save to file
        AppointmentManager.duplicateAppointments();
        // set availability for the doctor at this time slot: removing the time slot
        removeSchedule(appointment.getDate(), appointment.getTime());
    }

    public static void declineAppointmentRequest(Appointment appointment) {
        System.out.println("Decline Appointment Request");
        appointment.cancelAppointment();
        // save to file
        AppointmentManager.duplicateAppointments();
    }

    public void recordAppointmentOutcome() {
        System.out.println("Record Appointments Outcome");

        Scanner sc = new Scanner(System.in);
        // Listing out the scheduled appointments
        viewAppointments();
        Appointment appointment = null;

        while (true) {
            System.out.print("Enter Appointment ID: ");
            String appointmentID = sc.next();
            appointment = AppointmentManager.getAppointment(appointmentID);
            if (appointment == null || appointment.getDoctor().getHospitalID() != this.getHospitalID() ||
                    appointment.getStatus() != ApptStatus.SCHEDULED) {
                System.out.println("Invalid Appointment ID.");
                System.out.println("Would you like to try again?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                if (choice == 2) {
                    return;
                }
                System.out.println("Again");
                continue;
            }
            break;
        }

        System.out.println("Enter Consultation Notes: ");
        sc.nextLine();
        String consultationNotes = sc.nextLine();
        appointment.setConsultationNotes(consultationNotes);

        System.out.println("Enter Prescribed Medications: ");
        String prescribedMedications = sc.nextLine();
        appointment.setPrescribedMedications(prescribedMedications);

        System.out.println("Enter Service Type: ");
        String serviceType = sc.nextLine();
        appointment.setServiceType(serviceType);

        appointment.completeAppointment();
        // save to file
        AppointmentManager.duplicateAppointments();

        System.out.println("Appointment Outcome Recorded Successfully.");
    }

    @Override
    public void updateAppointmentRequest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAppointmentRequest'");
    }
}
