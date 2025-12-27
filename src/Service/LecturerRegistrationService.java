package Service;

import DAO.LectureDAO;
import DAO.UserDAO;
import Database.koneksiDB;
import Model.lecturer;
import Utils.PasswordUtil;

import java.sql.*;

public class LecturerRegistrationService {

    private koneksiDB db = new koneksiDB();
    private LectureDAO lectureDAO = new LectureDAO();
    private UserDAO userDAO = new UserDAO();

    public boolean registerLecturer(lecturer l) {

        Connection con = null;

        try {
            con = db.connect();
            con.setAutoCommit(false);

            // 1. Generate username & email
            String lastName = l.getFullName().trim()
                    .toLowerCase()
                    .replaceAll("\\s+", " ")
                    .split(" ")[l.getFullName().split(" ").length - 1];

            String username = lastName + "." + l.getNip();
            String email = lastName + "." + l.getNip() + "@unud.ac.id";

            // 2. Insert user_account
            String userSql = """
                INSERT INTO user_account (username, password_hash, email, role, status)
                VALUES (?, ?, ?, 'LECTURER', 'ACTIVE')
            """;

            int userId;

            try (PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, PasswordUtil.hashPassword("dosenUnud123"));
                ps.setString(3, email);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("Gagal membuat akun dosen");
                }
                userId = rs.getInt(1);
            }

            // 3. Insert lecturer
            String lecturerSql = """
                INSERT INTO lecturer (
                    user_id, program_id, nidn, full_name, nip,
                    gender, position_title, highest_education,
                    expertise, phone
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            try (PreparedStatement ps = con.prepareStatement(lecturerSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, l.getProgramId());
                ps.setInt(3, l.getNidn());
                ps.setString(4, l.getFullName());
                ps.setInt(5, l.getNip());
                ps.setString(6, l.getGender());
                ps.setString(7, l.getTitle());
                ps.setString(8, l.getEdukasiTertinggi());
                ps.setString(9, l.getBidangKeahlian());
                ps.setString(10, l.getTelepon());
                ps.executeUpdate();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
