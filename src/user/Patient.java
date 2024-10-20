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
    
    public Patient(String hospitalID, String gender, String role,
                    String name, int age, String dateOB, 
                    String bloodType, String contactInfo){
        super(hospitalID, gender, role, name, age);
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
        return String.format("Patient[PatientID=%s, Name=%s, Role=%s, Gender=%s, Age=%d, DateOB=%s, BloodType=%s, ContactInfo=%s]",
        getHospitalId(), getName(), getRole(), getGender(), getAge(), dateOB, bloodType, contactInfo);
    }
}
