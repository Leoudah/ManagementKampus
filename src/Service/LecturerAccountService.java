package Service;

import DAO.LectureDAO;
import DAO.UserDAO;

public class LecturerAccountService {

    private LectureDAO studentDAO = new LectureDAO();
    private UserDAO userDAO = new UserDAO();

    public boolean deactivateByStudentId(int studentId) {

        int userId = studentDAO.findUserIdByLectureId(studentId);
        if (userId == -1) {
            throw new RuntimeException("Student tidak ditemukan");
        }

        return userDAO.updateStatus(userId, "INACTIVE");
    }

    public boolean activateByStudentId(int studentId) {

        int userId = studentDAO.findUserIdByLectureId(studentId);
        if (userId == -1) {
            throw new RuntimeException("Student tidak ditemukan");
        }

        return userDAO.updateStatus(userId, "ACTIVE");
    }
}
