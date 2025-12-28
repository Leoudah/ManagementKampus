/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.koneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AManageGraduationDAO {

    private koneksiDB db = new koneksiDB();

    // 🔍 READ - View graduation data
    public List<Object[]> getGraduationData() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                s.student_id,
                s.full_name AS student_name,
                p.name,
                s.gpa
            FROM student s
            JOIN study_program p ON s.program_id = p.program_id
            WHERE s.status = 'Lulus' OR s.gpa >= 3.0
            ORDER BY s.gpa DESC, s.full_name
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("student_id"),
                    rs.getString("student_name"),
                    rs.getString("program_name"),
                    String.format("%.2f", rs.getDouble("gpa"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ➕ ADD
    public boolean addStudent(int studentId, String name, int programId, double gpa, String status) {
        String sql = """
            INSERT INTO student (student_id, full_name, program_id, gpa, status) 
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, name);
            ps.setInt(3, programId);
            ps.setDouble(4, gpa);
            ps.setString(5, status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✏️ UPDATE
    public boolean updateStudent(int studentId, String name, int programId, double gpa, String status) {
        String sql = """
            UPDATE student 
            SET full_name = ?, program_id = ?, gpa = ?, status = ? 
            WHERE student_id = ?
        """;
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, programId);
            ps.setDouble(3, gpa);
            ps.setString(4, status);
            ps.setInt(5, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🗑️ DELETE
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🎓 GENERATE
    public int generateGraduates() {
        String sql = """
            UPDATE student 
            SET status = 'Lulus' 
            WHERE gpa >= 3.0 AND status != 'Lulus'
        """;
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 🔎 GET BY ID
    public Object[] getStudentById(int studentId) {
        String sql = """
            SELECT s.student_id, s.full_name, s.program_id, s.gpa, s.status
            FROM student s
            WHERE s.student_id = ?
        """;
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("program_id"),
                        rs.getDouble("gpa"),
                        rs.getString("status")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
