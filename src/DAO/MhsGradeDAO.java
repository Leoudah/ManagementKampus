/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.koneksiDB;
import Model.student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MhsGradeDAO {

    private koneksiDB db = new koneksiDB();

    public List<Object[]> getGradesByStudent(student student) {
        List<Object[]> list = new ArrayList<>();
        int studentId = student.getStudentId();

        String sql = """
            SELECT
                c.name        AS course_name,
                e.semester_id AS semester,
                e.grade       AS grade
            FROM enrollment e
            JOIN course c ON c.course_id = e.course_id
            WHERE e.student_id = ?
            ORDER BY e.semester_id, c.name
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("course_name"),
                        rs.getInt("semester"),
                        rs.getString("grade")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
