package user;

public class Pharmacist extends User {

    public Pharmacist(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    public String getName() {
        return super.getName();
    }
}
