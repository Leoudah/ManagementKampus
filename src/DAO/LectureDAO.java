package DAO;

import Database.koneksiDB;
import Model.lecturer;

import java.sql.*;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LectureDAO {

    private koneksiDB db = new koneksiDB();

    public lecturer readLecture(int user_id) {

        String sql = """
            SELECT 
                l.lecturer_id,
                l.user_id,
                l.program_id,                          
                sp.name,
                l.nidn,
                l.full_name,
                l.nip,
                l.gender,
                l.position_title,
                l.highest_education,
                l.expertise,
                l.phone,
                u.email
            FROM lecturer l
            LEFT JOIN user_account u ON l.user_id = u.user_id
            LEFT JOIN study_program sp ON l.program_id = sp.program_id
            WHERE l.user_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, user_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new lecturer(
                            rs.getInt("lecturer_id"),
                            rs.getInt("user_id"),
                            rs.getInt("program_id"),
                            rs.getString("name"),     // prodi
                            rs.getInt("nidn"),
                            rs.getString("full_name"),
                            rs.getInt("nip"),
                            rs.getString("gender"),
                            rs.getString("position_title"),
                            rs.getString("highest_education"),
                            rs.getString("expertise"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Username/password salah atau user tidak aktif
    }

    public boolean create(lecturer lecturer) {

        String sql = """
            INSERT INTO lecturer (
                user_id,
                program_id,
                nidn,
                full_name,
                nip,
                gender,
                position_title,
                highest_education,
                expretise,
                phone
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lecturer.getUserId());
            ps.setInt(2, lecturer.getProgramId());
            ps.setInt(3, lecturer.getNidn());
            ps.setString(4, lecturer.getFullName());

            // Konversi Date (INI WAJIB)
            ps.setInt(5, lecturer.getNip());
            ps.setString(6, lecturer.getGender());

            ps.setString(7, lecturer.getTitle());
            ps.setString(8, lecturer.getEdukasiTertinggi());
            ps.setString(9, lecturer.getBidangKeahlian());
            ps.setString(10, lecturer.getTelepon());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<lecturer> findAll() {
        List<lecturer> list = new ArrayList<>();

        String sql = """
            SELECT
                s.lecturer_id,
                s.user_id,
                s.program_id,
                sp.name AS program_name,
                s.nidn,
                s.full_name,
                s.nip,
                s.gender,
                s.position_title,
                s.highest_education,
                s.expertise,
                s.phone,
                u.email
            FROM lecturer s
            JOIN user_account u ON s.user_id = u.user_id
            JOIN study_program sp ON s.program_id = sp.program_id
            ORDER BY s.full_name
        """;

        try (Connection c = db.connect();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new lecturer(
                        rs.getInt("lecturer_id"),
                        rs.getInt("user_id"),
                        rs.getInt("program_id"),
                        rs.getString("program_name"),
                        rs.getInt("nidn"),
                        rs.getString("full_name"),
                        rs.getInt("nip"),
                        rs.getString("gender"),
                        rs.getString("position_title"),
                        rs.getString("highest_education"),
                        rs.getString("expertise"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
