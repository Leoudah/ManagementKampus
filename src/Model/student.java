package Model;

import java.util.Date;

public class student {
    private int studentId;
    private int userId;
    private int programId;
    private String programName; // hasil JOIN (opsional)
    private String nim;
    private String fullName;
    private Date birthDate;
    private Date admissionDate;
    private String previousSchool;
    private String status;
    private String address;
    private String gender;
    private int entryYear;
    private String phone;
    private String email;
    private String statusAccount;
    
    public student(){
    }
    
    public student(int studentId, int userId, String nim, String fullName, 
                   String status, int entryYear, String programName, String statusAccount){
        this.studentId = studentId;
        this.userId = userId;
        this.nim = nim;
        this.fullName = fullName;
        this.status = status;
        this.entryYear = entryYear;
        this.programName = programName;
        this.statusAccount = statusAccount;
    }
    
    public student(int studentId, int userId, int programId, String programName, String nim, 
                   String fullName, Date birthDate, Date admissionDate, String previousSchool, 
                   String status, String address, String gender, int entryYear, 
                   String phone, String email, String statusAccount) {
        this.studentId = studentId;
        this.userId = userId;
        this.programId = programId;
        this.programName = programName;
        this.nim = nim;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.previousSchool = previousSchool;
        this.status = status;
        this.address = address;
        this.gender = gender;
        this.entryYear = entryYear;
        this.phone = phone;
        this.email = email;
        this.statusAccount = statusAccount;
    }

    // ===== Getter & Setter =====
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public Date getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(Date admissionDate) { this.admissionDate = admissionDate; }
    
    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getEntryYear() { return entryYear; }
    public void setEntryYear(int entryYear) { this.entryYear = entryYear; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getStatusAccount() { return statusAccount; }
    public void setStatusAccount(String statusAccount) { this.statusAccount = statusAccount; }
}
