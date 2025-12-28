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

public class DoCourseDAO {

    private koneksiDB db = new koneksiDB();

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        
        String sql = """
            SELECT course_id, program_id, lecturer_id, course_code, name, credits, semester_suggestion 
            FROM Course 
            ORDER BY semester_suggestion, name
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getInt("program_id"),
                    rs.getObject("lecturer_id", Integer.class),
                    rs.getString("course_code"),
                    rs.getString("name"),
                    rs.getInt("credits"),
                    rs.getInt("semester_suggestion")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}