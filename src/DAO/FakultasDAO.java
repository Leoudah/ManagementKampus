package DAO;

import Database.koneksiDB;
import Model.fakultas;
import java.sql.*;
import java.util.*;

public class FakultasDAO {
    private koneksiDB db = new koneksiDB();

    public List<fakultas> getAll() {
        List<fakultas> list = new ArrayList<>();
        String sql = "SELECT * FROM fakultas ORDER BY name";

        try (Connection c = db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                list.add(new fakultas(
                    r.getInt("faculty_id"),
                    r.getString("code"),
                    r.getString("name"),
                    r.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(fakultas f) {
        String sql = "INSERT INTO fakultas(code, name, description) VALUES (?,?,?)";
        try (Connection c = db.connect();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, f.getFakultasCode());
            p.setString(2, f.getFakultasName());
            p.setString(3, f.getFakultasDesc());
            return p.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(fakultas f) {
        String sql = """
            UPDATE fakultas
            SET code=?, name=?, description=?
            WHERE faculty_id=?
        """;

        try (Connection c = db.connect();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, f.getFakultasCode());
            p.setString(2, f.getFakultasName());
            p.setString(3, f.getFakultasDesc());
            p.setInt(4, f.getFakultasId());
            return p.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM fakultas WHERE faculty_id=?";
        try (Connection c = db.connect();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            return p.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
