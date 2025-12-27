package DAO;

import Database.koneksiDB;
import Model.prodi;
import java.sql.*;
import java.util.*;

public class ProdiDAO {

    private koneksiDB db = new koneksiDB();

    public boolean insert(prodi p) {
        String sql = """
            INSERT INTO study_program (faculty_id, name, code, degree_level)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.getFacultyId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCode());
            ps.setString(4, p.getDegreeLevel());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(prodi p) {
        String sql = """
            UPDATE study_program
            SET faculty_id = ?, name = ?, code = ?, degree_level = ?
            WHERE program_id = ?
        """;

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.getFacultyId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getCode());
            ps.setString(4, p.getDegreeLevel());
            ps.setInt(5, p.getProgramId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int programId) {
        String sql = "DELETE FROM study_program WHERE program_id = ?";

        try (Connection c = db.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, programId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, Object>> getAllWithFakultas() {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = """
            SELECT 
                sp.program_id,
                sp.code,
                sp.name,
                sp.degree_level,
                f.name AS faculty_name
            FROM study_program sp
            JOIN fakultas f ON sp.faculty_id = f.faculty_id
            ORDER BY sp.program_id
        """;

        try (Connection c = db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", r.getInt("program_id"));
                row.put("code", r.getString("code"));
                row.put("name", r.getString("name"));
                row.put("degree", r.getString("degree_level"));
                row.put("faculty", r.getString("faculty_name"));
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> findAllProgramNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM study_program ORDER BY name";

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int findIdByName(String name) {
        String sql = "SELECT program_id FROM study_program WHERE name = ?";
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("program_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
