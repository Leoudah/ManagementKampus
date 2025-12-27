package Service;

import DAO.StudentDAO;
import DAO.UserDAO;

public class StudentAccountService {

    private StudentDAO studentDAO = new StudentDAO();
    private UserDAO userDAO = new UserDAO();

    public boolean deactivateByStudentId(int studentId) {

        int userId = studentDAO.findUserIdByStudentId(studentId);
        if (userId == -1) {
            throw new RuntimeException("Student tidak ditemukan");
        }

        return userDAO.updateStatus(userId, "INACTIVE");
    }

    public boolean activateByStudentId(int studentId) {

        int userId = studentDAO.findUserIdByStudentId(studentId);
        if (userId == -1) {
            throw new RuntimeException("Student tidak ditemukan");
        }

        return userDAO.updateStatus(userId, "ACTIVE");
    }
}
