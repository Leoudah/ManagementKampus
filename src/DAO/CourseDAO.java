/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Database.koneksiDB;
import Model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // CREATE
    public void insert(Course c) throws SQLException {
        String sql = "INSERT INTO course " +
                "(program_id, lecturer_id, course_code, name, credits, semester_suggestion) " +
                "VALUES (?,?,?,?,?,?)";
        try (Connection con = new koneksiDB().connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getProgramId());
            if (c.getLecturerId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, c.getLecturerId());
            }
            ps.setString(3, c.getCourseCode());
            ps.setString(4, c.getName());
            ps.setInt(5, c.getCredits());
            ps.setInt(6, c.getSemesterSuggestion());

            ps.executeUpdate();
        }
    }

    // READ ALL
    public List<Object[]> getAllJoined() throws SQLException {
    List<Object[]> list = new ArrayList<>();

    String sql = "SELECT c.course_id AS id, " +
                 "       l.full_name AS lecturer_name, " +
                 "       sp.name AS program_name, " +
                 "       c.course_code AS code, " +
                 "       c.name AS course_name, " +
                 "       c.credits AS sks, " +
                 "       c.semester_suggestion AS semester " +
                 "FROM course c " +
                 "LEFT JOIN lecturer l ON c.lecturer_id = l.lecturer_id " +
                 "LEFT JOIN study_program sp ON c.program_id = sp.program_id";

    try (Connection con = new koneksiDB().connect();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Object[] row = new Object[] {
                rs.getInt("id"),
                rs.getString("lecturer_name"),
                rs.getString("program_name"),
                rs.getString("code"),
                rs.getString("course_name"),
                rs.getInt("sks"),
                rs.getInt("semester")
            };
            list.add(row);
        }
    }
    return list;
}


    // READ BY ID (opsional)
    public Course getById(int id) throws SQLException {
        String sql = "SELECT * FROM course WHERE course_id=?";
        try (Connection con = new koneksiDB().connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("course_id"),
                            rs.getInt("program_id"),
                            (Integer) rs.getObject("lecturer_id"),
                            rs.getString("course_code"),
                            rs.getString("name"),
                            rs.getInt("credits"),
                            rs.getInt("semester_suggestion")
                    );
                }
            }
        }
        return null;
    }

    // UPDATE
    public void update(Course c) throws SQLException {
        String sql = "UPDATE course SET " +
                "program_id=?, lecturer_id=?, course_code=?, name=?, " +
                "credits=?, semester_suggestion=? " +
                "WHERE course_id=?";
        try (Connection con = new koneksiDB().connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getProgramId());
            if (c.getLecturerId() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, c.getLecturerId());
            }
            ps.setString(3, c.getCourseCode());
            ps.setString(4, c.getName());
            ps.setInt(5, c.getCredits());
            ps.setInt(6, c.getSemesterSuggestion());
            ps.setInt(7, c.getCourseId());

            ps.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM course WHERE course_id=?";
        try (Connection con = new koneksiDB().connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
