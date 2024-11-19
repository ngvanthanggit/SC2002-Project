package interfaces;

import java.util.Scanner;
import medicalrecord.MedicalRecord;

public interface ApptOutcomeInterface {
    public void viewAllApptOutcomes();
    public MedicalRecord selectMRhelper(Scanner sc);
    public void updateApptMedication(Scanner sc);
}
