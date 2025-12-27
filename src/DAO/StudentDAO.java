package DAO;

import Database.koneksiDB;
import Model.student;
import Model.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date; // SQL Date

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
                        rs.getString("program_name"), // Alias dari sp.name
                        rs.getString("nim"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),     // java.sql.Date otomatis masuk ke java.util.Date
                        rs.getDate("admission_date"),
                        rs.getString("previous_school"),
                        rs.getString("status"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getInt("entry_year"),
                        rs.getString("phone"),
                        rs.getString("email"),        // Alias dari u.email
                        null                          // statusAccount (bisa diisi null atau rs.getString jika ada di SQL)
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Username/password salah atau user tidak aktif
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
                s.nim,
                s.full_name,
                s.status,
                s.entry_year,
                sp.name AS program_name,
                u.status AS status_account
            FROM student s
            JOIN user_account u ON s.user_id = u.user_id
            JOIN study_program sp ON s.program_id = sp.program_id
            ORDER BY s.nim
        """;

        try (Connection c = db.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                list.add(new student(
                    r.getInt("student_id"),
                    r.getInt("user_id"),
                    r.getString("nim"),
                    r.getString("full_name"),
                    r.getString("status"),
                    r.getInt("entry_year"),
                    r.getString("program_name"),
                    r.getString("status_account")
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

    
//    public student findByStudentId(int studentId) {
//
//        String sql = """
//            SELECT
//                s.student_id,
//                s.user_id,
//                s.program_id,
//                sp.name AS program_name,
//                s.nim,
//                s.full_name,
//                s.birth_date,
//                s.admission_date,
//                s.previous_school,
//                s.status,
//                s.address,
//                s.gender,
//                s.entry_year,
//                s.phone,
//                u.email
//            FROM student s
//            JOIN user_account u ON s.user_id = u.user_id
//            JOIN study_program sp ON s.program_id = sp.program_id
//            WHERE s.student_id = ?
//        """;
//
//        try (Connection con = db.connect();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, studentId);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToStudent(rs);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public boolean updateBiodata(student s) {
//        String sql = """
//            UPDATE student
//            SET full_name = ?,
//                birth_date = ?,
//                admission_date = ?,
//                previous_school = ?,
//                address = ?,
//                gender = ?,
//                phone = ?,
//                entry_year = ?
//            WHERE student_id = ?
//        """;
//
//        try (Connection con = db.connect();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, s.getFullName());
//
//            ps.setDate(2, new java.sql.Date(
//                s.getBirthDate().getTime()
//            ));
//
//            ps.setDate(3, new java.sql.Date(
//                s.getAdmissionDate().getTime()
//            ));
//
//            ps.setString(4, s.getPreviousSchool());
//            ps.setString(5, s.getAddress());
//            ps.setString(6, s.getGender());
//            ps.setString(7, s.getPhone());
//            ps.setInt(8, s.getEntryYear());
//            ps.setInt(9, s.getStudentId());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean updateStudentStatus(int studentId, String status) {
//
//        String sql = "UPDATE student SET status = ? WHERE student_id = ?";
//
//        try (Connection con = db.connect();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, status);
//            ps.setInt(2, studentId);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public boolean updateUserStatus(int userId, String status) {
//
//        String sql = "UPDATE user_account SET status = ? WHERE user_id = ?";
//
//        try (Connection con = db.connect();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, status);
//            ps.setInt(2, userId);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
////
////        s.setStudentId(rs.getInt("student_id"));
////        s.setUserId(rs.getInt("user_id"));
////        s.setProgramId(rs.getInt("program_id"));
////        s.setProgramName(rs.getString("program_name"));
////        s.setNim(rs.getString("nim"));
////        s.setFullName(rs.getString("full_name"));
////        java.sql.Date bd = rs.getDate("birth_date");
////        if (bd != null) {
////            s.setBirthDate(bd); // Langsung masukkan saja, tidak perlu dikonversi
////        }
////        java.sql.Date ad = rs.getDate("admission_date");
////        if (ad != null) {
////            s.setAdmissionDate(ad);
////        }
////        s.setPreviousSchool(rs.getString("previous_school"));
////        s.setStatus(rs.getString("status"));
////        s.setAddress(rs.getString("address"));
////        s.setGender(rs.getString("gender"));
////        s.setEntryYear(rs.getInt("entry_year"));
////        s.setPhone(rs.getString("phone"));
////        s.setEmail(rs.getString("email"));
//
//    
//    private String generateNim(Connection con, int programId, int entryYear) throws Exception {
//    String yearPart = String.valueOf(entryYear).substring(2); // 2024 -> 24
//
//    String facultySql = """
//        SELECT f.faculty_id
//        FROM study_program sp
//        JOIN faculty f ON sp.faculty_id = f.faculty_id
//        WHERE sp.program_id = ?
//    """;
//
//    int facultyId;
//    try (PreparedStatement ps = con.prepareStatement(facultySql)) {
//        ps.setInt(1, programId);
//        ResultSet rs = ps.executeQuery();
//        if (!rs.next()) {
//            throw new Exception("Program Studi tidak ditemukan");
//        }
//        facultyId = rs.getInt("faculty_id");
//    }
//
//    String countSql = """
//        SELECT COUNT(*) 
//        FROM student
//        WHERE program_id = ?
//          AND entry_year = ?
//    """;
//
//    int running;
//    try (PreparedStatement ps = con.prepareStatement(countSql)) {
//        ps.setInt(1, programId);
//        ps.setInt(2, entryYear);
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        running = rs.getInt(1) + 1;
//    }
//
//    return yearPart
//            + String.format("%02d", facultyId)
//            + String.format("%02d", programId)
//            + String.format("%03d", running);
//}
//
//    private String generateStudentEmail(String fullName, String nim) {
//    if (fullName == null || fullName.trim().isEmpty()) {
//        throw new IllegalArgumentException("Nama mahasiswa tidak boleh kosong");
//    }
//
//    String[] parts = fullName.trim().toLowerCase().split("\\s+");
//    String lastName = parts[parts.length - 1]
//            .replaceAll("[^a-z]", ""); // hanya huruf
//
//    return lastName + "." + nim + "@unud.ac.id";
//}
//
//    public boolean createStudent(student s) {
//
//        String insertUserSql = """
//            INSERT INTO user_account (username, email, password_hash, role, status)
//            VALUES (?, ?, ?, 'STUDENT', 'ACTIVE')
//        """;
//
//        String insertStudentSql = """
//            INSERT INTO student (
//                user_id, program_id, nim, full_name,
//                birth_date, admission_date, previous_school,
//                status, address, gender, entry_year, phone
//            )
//            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//        """;
//
//        Connection con = null;
//
//        try {
//            con = db.connect();
//            con.setAutoCommit(false);
//
//            // 1. Generate NIM & email
//            String nim = generateNim(con, s.getProgramId(), s.getEntryYear());
//            String email = generateStudentEmail(s.getFullName(), nim);
//
//            // 2. Generate default password (HASHING DI DAO)
//            String defaultPassword = "MahasiswaUnud123";
//            String salt = PasswordUtil.generateSalt();
//            String hash = PasswordUtil.hashPassword(defaultPassword, salt);
//            String passwordHash = salt + ":" + hash;
//
//            // 3. Insert user_account
//            int userId;
//            try (PreparedStatement ps = con.prepareStatement(
//                    insertUserSql,
//                    PreparedStatement.RETURN_GENERATED_KEYS
//            )) {
//                ps.setString(1, nim);          // username = NIM
//                ps.setString(2, email);        // email otomatis
//                ps.setString(3, passwordHash);
//
//                ps.executeUpdate();
//                ResultSet rs = ps.getGeneratedKeys();
//                if (!rs.next()) {
//                    throw new Exception("Gagal mendapatkan user_id");
//                }
//                userId = rs.getInt(1);
//            }
//
//            // 4. Insert student
//            try (PreparedStatement ps = con.prepareStatement(insertStudentSql)) {
//                ps.setInt(1, userId);
//                ps.setInt(2, s.getProgramId());
//                ps.setString(3, nim);
//                ps.setString(4, s.getFullName());
////                ps.setDate(5, new java.sql.Date(Student.getBirthDate().getTime()));
////                ps.setDate(6, new java.sql.Date(Student.getAdmissionDate().getTime()));
//                ps.setString(7, s.getPreviousSchool());
//                ps.setString(8, s.getStatus());
//                ps.setString(9, s.getAddress());
//                ps.setString(10, s.getGender());
//                ps.setInt(11, s.getEntryYear());
//                ps.setString(12, s.getPhone());
//
//                ps.executeUpdate();
//            }
//
//            con.commit();
//            return true;
//
//        } catch (Exception e) {
//            if (con != null) {
//                try {
//                    con.rollback();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            return false;
//
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public boolean deactivateStudent(int studentId) {
//
//    String getUserIdSql = """
//        SELECT user_id
//        FROM student
//        WHERE student_id = ?
//    """;
//
//    String updateStudentSql = """
//        UPDATE student
//        SET status = 'INACTIVE'
//        WHERE student_id = ?
//    """;
//
//    String updateUserSql = """
//        UPDATE user_account
//        SET status = 'INACTIVE'
//        WHERE user_id = ?
//    """;
//
//    try (Connection con = db.connect()) {
//        con.setAutoCommit(false);
//
//        int userId;
//
//        // 1. Ambil user_id dari student
//        try (PreparedStatement ps = con.prepareStatement(getUserIdSql)) {
//            ps.setInt(1, studentId);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new Exception("student tidak ditemukan");
//            }
//            userId = rs.getInt("user_id");
//        }
//
//        // 2. Update status student
//        try (PreparedStatement ps = con.prepareStatement(updateStudentSql)) {
//            ps.setInt(1, studentId);
//            ps.executeUpdate();
//        }
//
//        // 3. Update status user_account
//        try (PreparedStatement ps = con.prepareStatement(updateUserSql)) {
//            ps.setInt(1, userId);
//            ps.executeUpdate();
//        }
//
//        con.commit();
//        return true;
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return false;
//    }
//}

}
