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
            SELECT courseId, programId, lecturerId, courseCode, name, credits, semesterSuggestion 
            FROM Course 
            ORDER BY semesterSuggestion, name
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("courseId"),
                    rs.getInt("programId"),
                    rs.getObject("lecturerId", Integer.class),
                    rs.getString("courseCode"),
                    rs.getString("name"),
                    rs.getInt("credits"),
                    rs.getInt("semesterSuggestion")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}