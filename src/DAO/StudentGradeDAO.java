package DAO;

import Database.koneksiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeDAO {
    private koneksiDB db = new koneksiDB();

    // Ambil semua semester yang pernah diambil oleh mahasiswa
    public List<Integer> getSemestersByStudent(int studentId) {
        List<Integer> semesters = new ArrayList<>();
        String sql = """
            SELECT DISTINCT e.semester_id
            FROM enrollment e
            WHERE e.student_id = ?
            ORDER BY e.semester_id
            """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    semesters.add(rs.getInt("semester_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return semesters;
    }

    // Ambil grade per semester
    public List<Object[]> getGradesByStudentAndSemester(int studentId, int semesterId) {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT 
                c.name AS course_name,
                e.semester_id,
                COALESCE(e.grade, 'Belum Dinilai') AS grade
            FROM enrollment e
            JOIN course c ON e.course_id = c.course_id
            WHERE e.student_id = ? AND e.semester_id = ?
            ORDER BY c.name
            """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, semesterId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("course_name"),
                        rs.getInt("semester_id"),
                        rs.getString("grade")
                    };
                    list.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}