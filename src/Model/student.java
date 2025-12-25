package Model;

import java.sql.Date;

public class student {
    private int studentId;
    private int userId;
    private String prodi; //Program Studi
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

    public student(
            int studentId,
            int userId,
            String prodi,
            String nim,
            String fullName,
            Date birthDate,
            Date admissionDate,
            String previousSchool,
            String status,
            String address,
            String gender,
            int entryYear,
            String phone
    ) {
        this.studentId = studentId;
        this.userId = userId;
        this.prodi = prodi;
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
    }

    // ===== Getter & Setter =====
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProgramId() {
        return prodi;
    }
    public void setProgramId(String prodi) {
        this.prodi = prodi;
    }

    public String getNim() {
        return nim;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }
    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    public String getPreviousSchool() {
        return previousSchool;
    }
    public void setPreviousSchool(String previousSchool) {
        this.previousSchool = previousSchool;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getEntryYear() {
        return entryYear;
    }
    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
