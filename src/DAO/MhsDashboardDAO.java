package DAO;

import Model.student;
import Database.koneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MhsDashboardDAO {
    
    public student getStudentById(int studentId) {
        student mhs = null;
        String sql = "SELECT s.*, sp.name as program_name " +
                     "FROM student s " +
                     "LEFT JOIN study_program sp ON s.program_id = sp.program_id " +
                     "WHERE s.student_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    String programName = rs.getString("program_name");
                    if (programName == null) {
                        programName = "Program ID: " + rs.getInt("program_id");
                    }
                    
                    mhs = new student(
                        rs.getInt("student_id"),           // studentId (1)
                        rs.getInt("user_id"),              // userId (2)
                        rs.getInt("program_id"),           // programId (3) ← DITAMBAHKAN
                        programName,                       // programName (4)
                        rs.getString("nim"),               // nim (5)
                        rs.getString("full_name"),         // fullName (6)
                        rs.getDate("birth_date"),          // birthDate (7)
                        rs.getDate("admission_date"),      // admissionDate (8)
                        rs.getString("previous_school"),   // previousSchool (9)
                        rs.getString("status"),            // status (10)
                        rs.getString("address"),           // address (11)
                        rs.getString("gender"),            // gender (12)
                        rs.getInt("entry_year"),           // entryYear (13)
                        rs.getString("phone"),             // phone (14)
                        "",                                // email (15)
                        ""                                 // statusAccount (16)
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getStudentById: " + e.getMessage());
            return getStudentByIdWithoutJoin(studentId);
        }
        return mhs;
    }
    
    private student getStudentByIdWithoutJoin(int studentId) {
        student mhs = null;
        String sql = "SELECT * FROM student WHERE student_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mhs = new student(
                        rs.getInt("student_id"),
                        rs.getInt("user_id"),
                        rs.getInt("program_id"),           // ← DITAMBAHKAN
                        "Program ID: " + rs.getInt("program_id"),
                        rs.getString("nim"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),
                        rs.getDate("admission_date"),
                        rs.getString("previous_school"),
                        rs.getString("status"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getInt("entry_year"),
                        rs.getString("phone"),
                        "",  // email
                        ""   // statusAccount
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getStudentByIdWithoutJoin: " + e.getMessage());
            e.printStackTrace();
        }
        return mhs;
    }
    
    public List<Object[]> getCoursesByStudent(int studentId) {
        List<Object[]> courses = new ArrayList<>();
        String sql = "SELECT c.name as course_name, " +
                     "       c.course_code, " +
                     "       e.grade, " +
                     "       c.semester_suggestion " +
                     "FROM enrollment e " +
                     "LEFT JOIN course c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ? " +
                     "ORDER BY c.name ASC";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        formatCourseDisplay(
                            rs.getString("course_code"), 
                            rs.getString("course_name")
                        ),
                        rs.getInt("semester_suggestion"),
                        rs.getString("grade") != null ? rs.getString("grade") : ""
                    };
                    courses.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getCoursesByStudent: " + e.getMessage());
        }
        return courses;
    }
    
    private String formatCourseDisplay(String courseCode, String courseName) {
        if (courseCode != null && !courseCode.isEmpty()) {
            return courseCode + " - " + courseName;
        }
        return courseName;
    }
    
    public double calculateGPA(int studentId) {
        double gpa = 0.0;
        String sql = "SELECT " +
                     "  SUM(CASE grade " +
                     "    WHEN 'A' THEN 4 * c.credits " +
                     "    WHEN 'B' THEN 3 * c.credits " +
                     "    WHEN 'C' THEN 2 * c.credits " +
                     "    WHEN 'D' THEN 1 * c.credits " +
                     "    WHEN 'E' THEN 0 * c.credits " +
                     "    ELSE 0 " +
                     "  END) as total_nilai, " +
                     "  SUM(c.credits) as total_sks " +
                     "FROM enrollment e " +
                     "LEFT JOIN course c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ? AND e.grade IS NOT NULL AND e.grade IN ('A','B','C','D','E')";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double totalNilai = rs.getDouble("total_nilai");
                    double totalSKS = rs.getDouble("total_sks");
                    
                    if (totalSKS > 0) {
                        gpa = totalNilai / totalSKS;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculateGPA: " + e.getMessage());
        }
        return gpa;
    }
    
    public boolean isStudentExists(int studentId) {
        String sql = "SELECT 1 FROM student WHERE student_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error isStudentExists: " + e.getMessage());
            return false;
        }
    }
    
    public int getTotalCredits(int studentId) {
        int totalCredits = 0;
        String sql = "SELECT SUM(c.credits) as total " +
                     "FROM enrollment e " +
                     "LEFT JOIN course c ON e.course_id = c.course_id " +
                     "WHERE e.student_id = ? AND e.status = 'COMPLETED'";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCredits = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getTotalCredits: " + e.getMessage());
        }
        return totalCredits;
    }
}