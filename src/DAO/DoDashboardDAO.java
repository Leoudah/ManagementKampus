package DAO;

import Model.lecturer;
import Database.koneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoDashboardDAO {
    
    
    public lecturer getLecturerById(int lecturerId) {
        lecturer dosen = null;
        String sql = "SELECT l.*, p.name as program_name " +
                     "FROM lecturer l " +
                     "LEFT JOIN program p ON l.program_id = p.program_id " +
                     "WHERE l.lecturer_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    
                    String programName = rs.getString("program_name");
                    if (programName == null) {
                        programName = "Program ID: " + rs.getInt("program_id");
                    }
                    
                    dosen = new lecturer(
                        rs.getInt("lecturer_id"),          // lecturerId
                        rs.getInt("user_id"),              // userId
                        programName,                       // prodi (dari program.name)
                        rs.getInt("nidn"),                 // nidn (int)
                        rs.getString("full_name"),         // fullName
                        rs.getInt("nip"),                  // nip (int)
                        rs.getString("gender"),            // gender
                        rs.getString("position_title"),    // title
                        rs.getString("highest_education"), // edukasiTertinggi
                        rs.getString("education_history"), // edukasiHistory
                        rs.getString("expertise"),         // bidangKeahlian
                        rs.getString("phone"),             // telepon
                        ""                                 // email (tidak ada di DB, kosong)
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getLecturerById: " + e.getMessage());
            // Fallback: coba tanpa join ke program
            return getLecturerByIdWithoutJoin(lecturerId);
        }
        return dosen;
    }
    
   
    private lecturer getLecturerByIdWithoutJoin(int lecturerId) {
        lecturer dosen = null;
        String sql = "SELECT * FROM lecturer WHERE lecturer_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dosen = new lecturer(
                        rs.getInt("lecturer_id"),
                        rs.getInt("user_id"),
                        "Program ID: " + rs.getInt("program_id"), // prodi sebagai program_id
                        rs.getInt("nidn"),
                        rs.getString("full_name"),
                        rs.getInt("nip"),
                        rs.getString("gender"),
                        rs.getString("position_title"),
                        rs.getString("highest_education"),
                        rs.getString("education_history"),
                        rs.getString("expertise"),
                        rs.getString("phone"),
                        ""
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getLecturerByIdWithoutJoin: " + e.getMessage());
            e.printStackTrace();
        }
        return dosen;
    }
    
   
    public List<Object[]> getCoursesByLecturer(int lecturerId) {
        List<Object[]> courses = new ArrayList<>();
        String sql = "SELECT course_code, name, credits, semester_suggestion " +
                     "FROM course WHERE lecturer_id = ? " +
                     "ORDER BY semester_suggestion ASC, course_code ASC";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("course_code") != null ? rs.getString("course_code") : "-",
                        rs.getString("name") != null ? rs.getString("name") : "-",
                        rs.getInt("credits"),
                        rs.getInt("semester_suggestion"),
                        "-" // Kelas (placeholder)
                    };
                    courses.add(row);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getCoursesByLecturer: " + e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }
    
   
    public int getTotalCredits(int lecturerId) {
        int totalCredits = 0;
        String sql = "SELECT SUM(credits) as total FROM course WHERE lecturer_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
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
    
    
    public int getTotalCourses(int lecturerId) {
        int totalCourses = 0;
        String sql = "SELECT COUNT(*) as total FROM course WHERE lecturer_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCourses = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getTotalCourses: " + e.getMessage());
        }
        return totalCourses;
    }
    
  
    public boolean isLecturerExists(int lecturerId) {
        String sql = "SELECT 1 FROM lecturer WHERE lecturer_id = ?";
        
        try (Connection conn = new koneksiDB().connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, lecturerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error isLecturerExists: " + e.getMessage());
            return false;
        }
    }
}