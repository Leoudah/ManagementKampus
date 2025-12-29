package DAO;

import Database.koneksiDB;
import Model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    private koneksiDB db = new koneksiDB();

    /* =========================
       CREATE ENROLLMENT
    ========================= */
public boolean enroll(int studentId, int courseId, int semesterId) {
    // Ambil attempt terakhir
    int lastAttempt = getLastAttempt(studentId, courseId);
    int nextAttempt = lastAttempt + 1;

    // Cek apakah mahasiswa pernah lulus
    String sqlCheck = """
        SELECT grade, status 
        FROM enrollment 
        WHERE student_id=? AND course_id=? 
        ORDER BY attempt DESC 
        LIMIT 1
    """;

    try (Connection con = db.connect();
         PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {

        psCheck.setInt(1, studentId);
        psCheck.setInt(2, courseId);

        try (ResultSet rs = psCheck.executeQuery()) {
            if (rs.next()) {
                String grade = rs.getString("grade");
                String status = rs.getString("status");

                // Mahasiswa sudah lulus (grade != E) → tidak boleh enroll
                if (!"E".equalsIgnoreCase(grade) && "COMPLETED".equalsIgnoreCase(status)) {
                    throw new RuntimeException("Mahasiswa sudah lulus course ini, tidak bisa enroll ulang");
                }
            }
        }

        // INSERT enrollment baru
        String sqlInsert = """
            INSERT INTO enrollment (student_id, course_id, semester_id, status, attempt)
            VALUES (?, ?, ?, 'ENROLLED', ?)
        """;

        try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
            psInsert.setInt(1, studentId);
            psInsert.setInt(2, courseId);
            psInsert.setInt(3, semesterId);
            psInsert.setInt(4, nextAttempt);

            return psInsert.executeUpdate() > 0;
        }

    } catch (SQLException e) {
        throw new RuntimeException("Gagal enroll: " + e.getMessage(), e);
    }
}


    /* =========================
       CHECK ALREADY ENROLLED
    ========================= */
    public boolean isAlreadyEnrolled(int studentId, int courseId, int semesterId) {

        String sql = """
            SELECT 1
            FROM enrollment
            WHERE student_id = ?
              AND course_id = ?
              AND semester_id = ?
              AND status = 'ENROLLED'
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setInt(3, semesterId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Gagal cek enrollment", e);
        }
    }

    /* =========================
       FIND ENROLLED COURSES BY STUDENT
    ========================= */
    public List<Enrollment> findByStudentAndSemester(int studentId, int semesterId) {

        List<Enrollment> list = new ArrayList<>();

        String sql = """
            SELECT
                e.enrollment_id,
                e.student_id,
                e.course_id,
                e.semester_id,
                e.grade,
                e.status,
                e.attempt,
                c.course_code,
                c.name AS course_name,
                c.credits
            FROM enrollment e
            JOIN course c ON e.course_id = c.course_id
            WHERE e.student_id = ?
              AND e.semester_id = ?
            ORDER BY c.name
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, semesterId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Enrollment e = new Enrollment();
                    e.setEnrollmentId(rs.getInt("enrollment_id"));
                    e.setStudentId(rs.getInt("student_id"));
                    e.setCourseId(rs.getInt("course_id"));
                    e.setSemesterId(rs.getInt("semester_id"));
                    e.setGrade(rs.getString("grade"));
                    e.setStatus(rs.getString("status"));
                    e.setAttempt(rs.getInt("attempt"));

                    // field tambahan untuk UI
                    e.setCourseCode(rs.getString("course_code"));
                    e.setCourseName(rs.getString("course_name"));
                    e.setCredits(rs.getInt("credits"));

                    list.add(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil enrollment mahasiswa", e);
        }

        return list;
    }

    /* =========================
       WITHDRAW COURSE (OPTIONAL)
    ========================= */
    public boolean withdraw(int enrollmentId) {

        String sql = """
            UPDATE enrollment
            SET status = 'WITHDRAWN'
            WHERE enrollment_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, enrollmentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Gagal withdraw mata kuliah", e);
        }
    }

    public boolean canEnrollAgain(int studentId, int courseId) {
        String sql = """
            SELECT grade, status
            FROM enrollment
            WHERE student_id = ? AND course_id = ?
            ORDER BY attempt DESC
            LIMIT 1
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String grade = rs.getString("grade");
                    String status = rs.getString("status");

                    // Jika sebelumnya gagal (grade E) dan sudah completed → boleh enroll ulang
                    return "E".equalsIgnoreCase(grade) && "COMPLETED".equalsIgnoreCase(status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // jika belum pernah mengambil course, boleh enroll
        return true;
    }

    public int getLastAttempt(int studentId, int courseId) {
        String sql = """
            SELECT attempt
            FROM enrollment
            WHERE student_id=? AND course_id=?
            ORDER BY attempt DESC
            LIMIT 1
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("attempt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
