package user;

import java.util.*;
import java.time.*;

import medicalrecord.MedicalRecord;
import medicalrecord.MedicalRecordManager;
import schedule.Schedule;
import schedule.ScheduleManager;

import menus.DoctorMenu;

public class Doctor extends User implements DoctorMenu {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    @Override
    public void logout() {
        System.out.println("Doctor Logging Out.");
        return;
    }

    public Doctor(String hospitalID, String name, String role,
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
                    AppointmentsHandler();
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

            for (String timeSlotStr : newTimeSlotsStr) {
                LocalTime timeSlot = LocalTime.parse(timeSlotStr);
                if ((date.isEqual(LocalDate.now()) && timeSlot.isAfter(LocalTime.now())) ||
                        date.isAfter(LocalDate.now())) {
                    newTimeSlots.add(LocalTime.parse(timeSlotStr));
                    continue;
                }
            }

            if (newTimeSlots.isEmpty()) {
                System.out.println("All the entered time slots are invalid. Please enter a future time.");
                continue;
            }

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
                    for (LocalTime timeSlot : newTimeSlots) {
                        boolean isTimeSlotValid = true;

                        List<LocalTime> existingTimeSlots = schedule.getTimeSlots();
                        for (LocalTime existingTimeSlot : existingTimeSlots) {
                            if (timeSlot.equals(existingTimeSlot)) {
                                isTimeSlotValid = false; // existed
                                continue;
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
                Schedule newSchedule = new Schedule(this.getHospitalID(), date, newTimeSlots);
                ScheduleManager.addSchedule(newSchedule);
                validTimeSlots.addAll(newTimeSlots);
            }

            System.out.println("Time slots added successfully (excluding invalid time slots) are:");
            System.out.println(validTimeSlots);
            break;
        }
        // save to file
        ScheduleManager.duplicateSchedule();
    }

    public void viewAppointments() {
        System.out.println("View Appointments");
    }

    public void AppointmentsHandler() {
        System.out.println("Appointment Request");
    }

    public void recordAppointmentOutcome() {
        System.out.println("Record Appointments Outcome");
    }

    public void acceptAppointmentRequest() {
        System.out.println("Accept Appointment Request");
    }

    public void declineAppointmentRequest() {
        System.out.println("Decline Appointment Request");
    }
}
