/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 62895
 */
public class lecture {
    private int lecturerId;
    private int userId;
    private String prodi; //Program Studi
    private int nidn;
    private String fullName;
    private int nip;
    private String gender;
    private String title;
    private String edukasiTertinggi;
    private String edukasiHistory;
    private String bidangKeahlian;
    private String telepon;
    private String email;
    
    public lecture(            
            int lecturerId,
            int userId,
            String prodi,
            int nidn,
            String fullName,
            int nip,
            String gender,
            String title,
            String edukasiTertinggi,
            String edukasiHistory,
            String bidangKeahlian,
            String telepon,
            String email) {
        this.lecturerId = lecturerId;
        this.userId = userId;
        this.prodi = prodi;
        this.nidn = nidn;
        this.fullName = fullName;
        this.nip = nip;
        this.gender = gender;
        this.title = title;
        this.edukasiTertinggi = edukasiTertinggi;
        this.edukasiHistory = edukasiHistory;
        this.bidangKeahlian = bidangKeahlian;
        this.telepon = telepon;
        this.email = email;
   }
    
    // ===== Getter & Setter =====
    public int getLecturerId() {
        return lecturerId;
   }
    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
   }
    public int getUserId() {
        return userId;
   }
    public void setUserId(int userId) {
        this.userId = userId;
   }
    public String getProdi() {
        return prodi;
   }
    public void setProdi(String prodi) {
        this.prodi = prodi;
   }
    public int getNidn() {
        return nidn;
   }
    public void setNidn(int nidn) {
        this.nidn = nidn;
   }
    public String getFullName() {
        return fullName;
   }
    public void setFullName(String fullName) {
        this.fullName = fullName;
   }
    public int getNip() {
        return nip;
   }
    public void setNip(int nip) {
        this.nip = nip;
   }
    public String getGender() {
        return gender;
   }
    public void setGender(String gender) {
        this.gender = gender;
   }
    public String getTitle() {
        return title;
   }
    public void setTitle(String title) {
        this.title = title;
   }
    public String getEdukasiTertinggi() {
        return edukasiTertinggi;
   }
    public void setEdukasiTertinggi(String edukasiTertinggi) {
        this.edukasiTertinggi = edukasiTertinggi;
   }
    public String getEdukasiHistory() {
        return edukasiHistory;
   }
    public void setEdukasiHistory(String edukasiHistory) {
        this.edukasiHistory = edukasiHistory;
   }
    public String getBidangKeahlian() {
        return bidangKeahlian;
   }
    public void setBidangKeahlian(String bidangKeahlian) {
        this.bidangKeahlian = bidangKeahlian;
   }
    public String getTelepon() {
        return telepon;
   }
    public void setTelepon(String telepon) {
        this.telepon = telepon;
   }
    public String getEmail() {
        return email;
   }
    public void setEmail(String email) {
        this.email = email;
   }
}
