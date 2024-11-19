package interfaces;

import java.util.Scanner;

import medicalrecord.MedicalRecord;
import user.Doctor;
import user.Patient;

public interface MedicalRecInterface {

    public void viewPatientMedicalRecords(Patient patient);
    public MedicalRecord choosePatient(Scanner sc, Doctor doctor);
    public void updatePatientRecords(Scanner sc, MedicalRecord record);
}
