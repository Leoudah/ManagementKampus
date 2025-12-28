package DAO;

import Database.koneksiDB;
import Model.user;
import Utils.PasswordUtil;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private koneksiDB db = new koneksiDB();

    public user login(String username, String password) {

        String sql = "SELECT * FROM user_account WHERE username=? AND password_hash=? AND status='ACTIVE'";

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.hashPassword(password));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new user(
                        rs.getInt("user_id"),
                        rs.getString("password_hash"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Username/password salah atau user tidak aktif
    }

    public boolean updateStatus(int userId, String status) {
        String sql = "UPDATE user_account SET status = ? WHERE user_id = ?";
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
