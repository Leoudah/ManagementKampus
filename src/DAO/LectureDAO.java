/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.koneksiDB;
import Model.lecturer;

import java.sql.Connection;
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
                l.education_history,
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
                            rs.getString("education_history"),
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
}
