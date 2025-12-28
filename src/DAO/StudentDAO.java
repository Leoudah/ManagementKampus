package DAO;

import Database.koneksiDB;
import Model.student;
import Model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private koneksiDB db = new koneksiDB();

    public student findByStudentId(int userId) {
        String sql = """
            SELECT
                s.student_id,
                s.user_id,
                s.program_id,
                sp.name AS program_name,
                s.nim,
                s.full_name,
                s.birth_date,
                s.admission_date,
                s.previous_school,
                s.status,
                s.address,
                s.gender,
                s.entry_year,
                s.phone,
                u.email
            FROM student s
            JOIN user_account u ON s.user_id = u.user_id
            JOIN study_program sp ON s.program_id = sp.program_id
            WHERE s.user_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new student(
                        rs.getInt("student_id"),
                        rs.getInt("user_id"),
                        rs.getInt("program_id"),
                        rs.getString("program_name"), 
                        rs.getString("nim"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),     
                        rs.getDate("admission_date"),
                        rs.getString("previous_school"),
                        rs.getString("status"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getInt("entry_year"),
                        rs.getString("phone"),
                        rs.getString("email"),        
                        null                          
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; 
    }
    

    public int countByProgramId(int programId) {

        String sql = """
            SELECT COUNT(*) 
            FROM student
            WHERE program_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, programId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // default aman
    }
    
    public boolean create(student student) {

        String sql = """
            INSERT INTO student (
                user_id,
                program_id,
                nim,
                full_name,
                birth_date,
                admission_date,
                previous_school,
                status,
                address,
                gender,
                entry_year,
                phone
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getUserId());
            ps.setInt(2, student.getProgramId());
            ps.setString(3, student.getNim());
            ps.setString(4, student.getFullName());

            // Konversi Date (INI WAJIB)
            ps.setDate(5, new java.sql.Date(student.getBirthDate().getTime()));
            ps.setDate(6, new java.sql.Date(student.getAdmissionDate().getTime()));

            ps.setString(7, student.getPreviousSchool());
            ps.setString(8, student.getStatus());
            ps.setString(9, student.getAddress());
            ps.setString(10, student.getGender());
            ps.setInt(11, student.getEntryYear());
            ps.setString(12, student.getPhone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
   
    public List<student> findAll() {
        List<student> list = new ArrayList<>();

        String sql = """
            SELECT
                s.student_id,
                s.user_id,
                s.program_id,
                sp.name AS program_name,
                s.nim,
                s.full_name,
                s.birth_date,
                s.admission_date,
                s.previous_school,
                s.status,
                s.address,
                s.gender,
                s.entry_year,
                s.phone,
                u.email
            FROM student s
            JOIN user_account u ON s.user_id = u.user_id
            JOIN study_program sp ON s.program_id = sp.program_id
            ORDER BY s.nim
        """;

        try (Connection c = db.connect();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new student(
                        rs.getInt("student_id"),
                        rs.getInt("user_id"),
                        rs.getInt("program_id"),
                        rs.getString("program_name"), 
                        rs.getString("nim"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),     
                        rs.getDate("admission_date"),
                        rs.getString("previous_school"),
                        rs.getString("status"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getInt("entry_year"),
                        rs.getString("phone"),
                        rs.getString("email"),       
                        null                          
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean createWithAutoUser(student s) {

        Connection con = null;

        try {
            con = db.connect();
            con.setAutoCommit(false); // TRANSAKSI DIMULAI

            // 1. Generate NIM
            int count = countByProgramId(s.getProgramId()) + 1;
            String nim = generateNim(s.getEntryYear(), s.getProgramId(), count);
            s.setNim(nim);

            // 2. Generate email & username
            String lastName = s.getFullName().trim()
                    .toLowerCase()
                    .replaceAll("\\s+", " ")
                    .split(" ")[s.getFullName().split(" ").length - 1];

            String email = lastName + "." + nim + "@unud.ac.id";
            String username = lastName + "." + nim;

            // 3. Insert user_account
            String userSql = """
                INSERT INTO user_account (username, password_hash, email, role, status)
                VALUES (?, ?, ?, 'STUDENT', 'ACTIVE')
            """;

            int userId;

            try (PreparedStatement ps = con.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, hashPassword("mahasiswaUnud123"));
                ps.setString(3, email);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("Gagal membuat user_account");
                }
                userId = rs.getInt(1);
            }

            // 4. Insert student
            String studentSql = """
                INSERT INTO student (
                    user_id, program_id, nim, full_name,
                    birth_date, admission_date, previous_school,
                    status, address, gender, entry_year, phone
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            try (PreparedStatement ps = con.prepareStatement(studentSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, s.getProgramId());
                ps.setString(3, nim);
                ps.setString(4, s.getFullName());
                ps.setDate(5, new java.sql.Date(s.getBirthDate().getTime()));
                ps.setDate(6, new java.sql.Date(s.getAdmissionDate().getTime()));
                ps.setString(7, s.getPreviousSchool());
                ps.setString(8, s.getStatus());
                ps.setString(9, s.getAddress());
                ps.setString(10, s.getGender());
                ps.setInt(11, s.getEntryYear());
                ps.setString(12, s.getPhone());
                ps.executeUpdate();
            }

            con.commit(); // ✔ SEMUA BERHASIL
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

   
    public boolean update(student student) {
        String sql = """
            UPDATE student SET
                program_id = ?,
                nim = ?,
                full_name = ?,
                birth_date = ?,
                admission_date = ?,
                previous_school = ?,
                status = ?,
                address = ?,
                gender = ?,
                entry_year = ?,
                phone = ?
            WHERE student_id = ?
        """;

        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, student.getProgramId());
            ps.setString(2, student.getNim());
            ps.setString(3, student.getFullName());
            
            
            if (student.getBirthDate() != null) {
                ps.setDate(4, new java.sql.Date(student.getBirthDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            if (student.getAdmissionDate() != null) {
                ps.setDate(5, new java.sql.Date(student.getAdmissionDate().getTime()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            
            ps.setString(6, student.getPreviousSchool());
            ps.setString(7, student.getStatus());
            ps.setString(8, student.getAddress());
            ps.setString(9, student.getGender());
            ps.setInt(10, student.getEntryYear());
            ps.setString(11, student.getPhone());
            ps.setInt(12, student.getStudentId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
   
    public boolean updateContactInfo(int studentId, String phone, String email, String address) {
        Connection con = null;
        
        try {
            con = db.connect();
            con.setAutoCommit(false); // Mulai transaksi
            
            // 1. Update phone dan address di tabel student
            String updateStudentSql = """
                UPDATE student 
                SET phone = ?, address = ?
                WHERE student_id = ?
            """;
            
            try (PreparedStatement ps1 = con.prepareStatement(updateStudentSql)) {
                ps1.setString(1, phone);
                ps1.setString(2, address);
                ps1.setInt(3, studentId);
                ps1.executeUpdate();
            }
            
           
            int userId = findUserIdByStudentId(studentId);
            
            if (userId == -1) {
                con.rollback();
                return false;
            }
            
            String updateUserSql = """
                UPDATE user_account
                SET email = ?
                WHERE user_id = ?
            """;
            
            try (PreparedStatement ps2 = con.prepareStatement(updateUserSql)) {
                ps2.setString(1, email);
                ps2.setInt(2, userId);
                ps2.executeUpdate();
            }
            
            con.commit(); // Commit transaksi
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
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

    private String generateNim(int entryYear, int programId, int sequence) {
        return String.format(
            "%02d%02d%03d",
            entryYear % 100,
            programId,
            sequence
        );
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode()); // minimal
    }

    public int findUserIdByStudentId(int studentId) {
        String sql = "SELECT user_id FROM student WHERE student_id = ?";
        try (Connection con = db.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
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