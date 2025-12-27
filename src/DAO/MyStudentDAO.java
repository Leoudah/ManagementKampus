package DAO;

import Database.koneksiDB;
import java.sql.*;
import java.util.*;

public class MyStudentDAO {

    private koneksiDB db = new koneksiDB();

    public List<Object[]> getMyStudents(int lecturerId) {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT
                s.full_name   AS student_name,
                s.nim         AS nim,
                c.credits     AS sks,
                e.semester_id AS semester,
                c.name        AS course_name,
                e.status      AS status
            FROM course c
            JOIN enrollment e ON c.course_id = e.course_id
            JOIN student s ON e.student_id = s.student_id
            WHERE c.lecturer_id = ?
            ORDER BY s.full_name, c.name
        """;

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, lecturerId);

            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    Object[] row = new Object[]{
                        r.getString("student_name"),
                        r.getString("nim"),
                        r.getInt("sks"),
                        r.getInt("semester"),
                        r.getString("course_name"),
                        r.getString("status")
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