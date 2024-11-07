package user;

public class Doctor extends User {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    public Doctor(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }
}
