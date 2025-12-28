package DAO;

import Database.koneksiDB;
import Model.lecturer;
import Utils.PasswordUtil;

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

public boolean createWithAutoUser(lecturer l) {

    String insertUserSql = """
        INSERT INTO user_account (username, password_hash, email, role, status)
        VALUES (?, ?, ?, ?, ?)
    """;

    String insertLecturerSql = """
        INSERT INTO lecturer (
            user_id,
            program_id,
            nidn,
            full_name,
            nip,
            gender,
            position_title,
            highest_education,
            expertise,
            phone
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    Connection con = null;

    try {
        con = db.connect();
        con.setAutoCommit(false);

        int userId;

        /* =========================
           1. INSERT USER ACCOUNT
        ========================= */
        try (PreparedStatement psUser =
                     con.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {

            String fullName = l.getFullName().trim();
            String lastName = fullName
                    .toLowerCase()
                    .replaceAll("\\s+", " ")
                    .split(" ")[fullName.split(" ").length - 1];

            String nipStr = String.valueOf(l.getNip());

            String username = lastName + "." + nipStr;
            String email = username + "@unud.ac.id";

            String defaultPassword = "lecturer123";
            String hashedPassword = PasswordUtil.hashPassword(defaultPassword);

            psUser.setString(1, username);
            psUser.setString(2, hashedPassword);
            psUser.setString(3, email);
            psUser.setString(4, "LECTURER");
            psUser.setString(5, "ACTIVE");

            if (psUser.executeUpdate() == 0) {
                throw new SQLException("Gagal insert user_account");
            }

            try (ResultSet rs = psUser.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new SQLException("Gagal mengambil user_id");
                }
                userId = rs.getInt(1);
            }
        }

        /* =========================
           2. INSERT LECTURER
        ========================= */
        try (PreparedStatement psLecturer =
                     con.prepareStatement(insertLecturerSql)) {

            psLecturer.setInt(1, userId);
            psLecturer.setInt(2, l.getProgramId());
            psLecturer.setInt(3, l.getNidn());
            psLecturer.setString(4, l.getFullName());
            psLecturer.setInt(5, l.getNip());
            psLecturer.setString(6, l.getGender());
            psLecturer.setString(7, l.getTitle());
            psLecturer.setString(8, l.getEdukasiTertinggi());
            psLecturer.setString(9, l.getBidangKeahlian());
            psLecturer.setString(10, l.getTelepon());

            if (psLecturer.executeUpdate() == 0) {
                throw new SQLException("Gagal insert lecturer");
            }
        }

        con.commit();
        return true;

    } catch (Exception e) {
        try {
            if (con != null) con.rollback();
        } catch (SQLException ignored) {}

        throw new RuntimeException("Gagal membuat lecturer + user: " + e.getMessage(), e);

    } finally {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException ignored) {}
    }
}

    public int findUserIdByLectureId(int lectureId) {
        String sql = "SELECT user_id FROM lecturer WHERE lecturer_id = ?";
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, lectureId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
