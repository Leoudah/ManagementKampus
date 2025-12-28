package DAO;

import Database.koneksiDB;
import Model.Semester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SemesterDAO {

    private koneksiDB db = new koneksiDB();

    /* =========================
       FIND ALL
    ========================= */
    public List<Semester> findAll() {
        List<Semester> list = new ArrayList<>();

        String sql = """
            SELECT semester_id, year, term, start_date, end_date, status
            FROM semester
            ORDER BY year DESC, term
        """;

        try (Connection con = db.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Semester(
                        rs.getInt("semester_id"),
                        rs.getInt("year"),
                        rs.getString("term"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /* =========================
       FIND ACTIVE SEMESTER
    ========================= */
    public Semester findActive() {

        String sql = """
            SELECT semester_id, year, term, start_date, end_date, status
            FROM semester
            WHERE status = 'ACTIVE'
            LIMIT 1
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new Semester(
                        rs.getInt("semester_id"),
                        rs.getInt("year"),
                        rs.getString("term"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // tidak ada semester aktif
    }

    /* =========================
       CREATE SEMESTER
    ========================= */
    public boolean create(Semester s) {

        String sql = """
            INSERT INTO semester (year, term, start_date, end_date, status)
            VALUES (?, ?, ?, ?, 'PLANNED')
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getYear());
            ps.setString(2, s.getTerm());
            ps.setDate(3, s.getStartDate());
            ps.setDate(4, s.getEndDate());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /* =========================
       SET ACTIVE (TRANSACTIONAL)
    ========================= */
    public boolean setActive(int semesterId) {

        String closeAllSql = """
            UPDATE semester SET status = 'CLOSED'
            WHERE status = 'ACTIVE'
        """;

        String activateSql = """
            UPDATE semester SET status = 'ACTIVE'
            WHERE semester_id = ?
        """;

        Connection con = null;

        try {
            con = db.connect();
            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(closeAllSql);
                 PreparedStatement ps2 = con.prepareStatement(activateSql)) {

                ps1.executeUpdate();

                ps2.setInt(1, semesterId);
                if (ps2.executeUpdate() == 0) {
                    throw new SQLException("Semester tidak ditemukan");
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Gagal set semester aktif", e);

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
    }
}
