package user;

public class Patient extends User{
    private String dateOB; //date of birth
    private String bloodType;
    private String contactInfo; //changeable

    //constructors
    public Patient(){
        super();
        this.dateOB = null;
        this.bloodType = null;
        this.contactInfo = null;
    }
    
    public Patient(String hospitalID, String name, String role,
                    String gender, int age, String password, String dateOB, 
                    String bloodType, String contactInfo){
        super(hospitalID, name, role, gender, age, password);
        this.dateOB = dateOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    //get Methods()
    public String getDateOB() {
        return dateOB;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    //set Methods()
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    //other Methods()
    public String userInfo() {
        return String.format("Patient[PatientID=%s, Name=%s, Role=%s, Gender=%s, Age=%d, Password=%s, DateOB=%s, BloodType=%s, ContactInfo=%s]",
        getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword(), dateOB, bloodType, contactInfo);
    }
}
