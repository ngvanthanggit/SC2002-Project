package interfaces;

import java.util.Scanner;
import user.Patient;

public interface PatientMenu extends CommonMenu{

    public void updatePersonalInfo(Scanner sc, Patient patient);
}
