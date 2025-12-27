package Service;

import DAO.StudentDAO;
import DAO.UserDAO;
import Model.student;
import Model.user;

import java.security.MessageDigest;

public class StudentRegistrationService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean registerStudent(student studentInput,
                                   String facultyCode,
                                   String programCode,
                                   String lastName) {

        // 1. Hitung urutan mahasiswa
        int count = studentDAO.countByProgramId(studentInput.getProgramId());
        int sequence = count + 1;

        // 2. Generate NIM
        String nim = generateNim(
                studentInput.getEntryYear(),
                facultyCode,
                programCode,
                sequence
        );

        // 3. Generate email & username
        String email = generateEmail(lastName, nim);
        String username = generateUsername(lastName, nim);

        // 4. Hash password default
        String passwordHash = hashPassword("mahasiswaUnud123");

        // 5. Insert user_account
        user user = new user(0, username, passwordHash, email, "STUDENT");
        int userId = userDAO.create(user);

        if (userId <= 0) {
            return false; // stop total
        }

        // 6. Lengkapi data student
        studentInput.setUserId(userId);
        studentInput.setNim(nim);
        studentInput.setStatus("ACTIVE");

        // 7. Insert student
        return studentDAO.create(studentInput);
    }

    // ===== Helper methods =====

    private String generateNim(int entryYear,
                               String facultyCode,
                               String programCode,
                               int sequence) {

        String year = String.valueOf(entryYear).substring(2);
        String order = String.format("%03d", sequence);

        return year + facultyCode + programCode + order;
    }

    private String generateEmail(String lastName, String nim) {
        return lastName.toLowerCase() + "." + nim + "@unud.ac.id";
    }

    private String generateUsername(String lastName, String nim) {
        return lastName.toLowerCase() + "." + nim;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Password hashing failed");
        }
    }
}
