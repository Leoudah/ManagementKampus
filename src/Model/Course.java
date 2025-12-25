/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Course {
    private int courseId;
    private int programId;
    private Integer lecturerId;    // boleh null
    private String courseCode;
    private String name;
    private int credits;
    private int semesterSuggestion;

    // Konstruktor lengkap
    public Course(int courseId,
                  int programId,
                  Integer lecturerId,
                  String courseCode,
                  String name,
                  int credits,
                  int semesterSuggestion) {
        this.courseId = courseId;
        this.programId = programId;
        this.lecturerId = lecturerId;
        this.courseCode = courseCode;
        this.name = name;
        this.credits = credits;
        this.semesterSuggestion = semesterSuggestion;
    }

    // Optional: konstruktor tanpa ID (dipakai saat insert baru)
    public Course(int programId,
                  Integer lecturerId,
                  String courseCode,
                  String name,
                  int credits,
                  int semesterSuggestion) {
        this(0, programId, lecturerId, courseCode, name, credits, semesterSuggestion);
    }

    // ===== Getter & Setter =====
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getProgramId() {
        return programId;
    }
    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }
    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSemesterSuggestion() {
        return semesterSuggestion;
    }
    public void setSemesterSuggestion(int semesterSuggestion) {
        this.semesterSuggestion = semesterSuggestion;
    }
}
