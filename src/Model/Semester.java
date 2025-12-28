package Model;

import java.sql.Date;

public class Semester {

    private int semesterId;
    private int year;        // contoh: "2025"
    private String term;        // GANJIL / GENAP
    private Date startDate;
    private Date endDate;
    private String status;      // PLANNED / ACTIVE / CLOSED

    // constructor kosong (WAJIB untuk UI)
    public Semester() {}

    // constructor lengkap
    public Semester(int semesterId, int year, String term,
                    Date startDate, Date endDate, String status) {
        this.semesterId = semesterId;
        this.year = year;
        this.term = term;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }
}
