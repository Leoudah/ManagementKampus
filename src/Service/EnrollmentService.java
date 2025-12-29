package Service;

import DAO.EnrollmentDAO;

public class EnrollmentService {

    private EnrollmentDAO dao = new EnrollmentDAO();

    public boolean enrollCourse(int studentId, int courseId, int semesterId) {
        // cek boleh enroll
        if (!dao.canEnrollAgain(studentId, courseId)) {
            throw new RuntimeException("Mahasiswa sudah lulus course ini, tidak bisa enroll ulang");
        }

        return dao.enroll(studentId, courseId, semesterId);
    }
}
