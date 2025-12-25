/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 62895
 */

public class prodi {
    private int programId;
    private int facultyId;
    private String name;
    private String code;
    private String degreeLevel;

    public prodi(int programId, int facultyId, String name, String code, String degreeLevel) {
        this.programId = programId;
        this.facultyId = facultyId;
        this.name = name;
        this.code = code;
        this.degreeLevel = degreeLevel;
    }

    // constructor untuk INSERT
    public prodi(int facultyId, String name, String code, String degreeLevel) {
        this(0, facultyId, name, code, degreeLevel);
    }

    public int getProgramId() { return programId; }
    public int getFacultyId() { return facultyId; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getDegreeLevel() { return degreeLevel; }

    public void setProgramId(int programId) { this.programId = programId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }
    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
    public void setDegreeLevel(String degreeLevel) { this.degreeLevel = degreeLevel; }
}

