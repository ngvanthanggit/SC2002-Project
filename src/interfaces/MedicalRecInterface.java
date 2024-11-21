package interfaces;

import java.util.Scanner;
import java.util.List;

import medicalrecord.MedicalRecord;
import user.Doctor;
import user.Patient;

/**
 * This interface defines methods related to managing medical records for
 * patients.
 * It includes functionality for viewing patient medical records, selecting a
 * patient for review,
 * and updating medical records.
 * <p>
 * The methods in this interface are intended to be implemented by classes that
 * provide
 * functionality for handling patient medical records, including viewing,
 * selecting, and updating records.
 * </p>
 */
public interface MedicalRecInterface {

    /**
     * Displays the medical records of a specific patient.
     * 
     * @param patient The {@link Patient} whose medical records are to be viewed.
     */
    public void viewPatientMedicalRecords(Patient patient);

    /**
     * Allows the doctor to choose a patient for reviewing their medical records.
     * 
     * @param sc     A {@link Scanner} object for reading user input.
     * @param doctor The {@link Doctor} who is selecting the patient.
     * @return A list of {@link MedicalRecord} objects for the selected patient.
     */
    public List<MedicalRecord> choosePatient(Scanner sc, Doctor doctor);

    /**
     * Updates the medical records of a specific patient.
     * 
     * @param sc      A {@link Scanner} object for reading user input.
     * @param records A list of {@link MedicalRecord} objects to be updated.
     */
    public void updatePatientRecords(Scanner sc, List<MedicalRecord> records);
}
