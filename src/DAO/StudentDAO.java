package DAO;

import Database.koneksiDB;
import Model.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    private koneksiDB db = new koneksiDB();

    public student readStudent(int user_id) {

        String sql = """
            SELECT
                s.student_id,
                s.user_id,
                sp.name,
                s.nim,
                s.full_name,
                s.birth_date,
                s.admission_date,
                s.previous_school,
                s.status,
                s.address,
                s.gender,
                s.entry_year,
                s.phone,
                u.email
            FROM student s
            LEFT JOIN user_account u ON s.user_id = u.user_id
            LEFT JOIN study_program sp ON s.program_id = sp.program_id
            WHERE s.user_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new student(
                            rs.getInt("student_id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("nim"),
                            rs.getString("full_name"),
                            rs.getDate("birth_date"),
                            rs.getDate("admission_date"),
                            rs.getString("previous_school"),
                            rs.getString("status"),
                            rs.getString("address"),
                            rs.getString("gender"),
                            rs.getInt("entry_year"),
                            rs.getString("phone")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // user_id tidak ditemukan
    }
}
