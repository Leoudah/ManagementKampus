package Session;

import Model.user;
import Model.lecturer;
import Model.student;

public class UserSession {
    private static user currentUser;
    private static lecturer currentLecturer;
    private static student currentStudent;

    //USER LOGIN
    public static void setUser(user u) {
        currentUser = u;
        System.out.println("User login: " + u.getUsername());
    }

    public static user getUser() {
        return currentUser;
    }
    
    //DATA DOSEN/LECTURER
    public static void setLecturer(lecturer l) {
        currentLecturer = l;
    }

    public static lecturer getLecturer() {
        return currentLecturer;
    }
    
    public static void setStudent(student s) {
        currentStudent = s;
    }

    public static student getStudent() {
        return currentStudent;
    }
    
    //PENGECEKAN LOGIN & ROLE
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null &&
               "ADMIN".equalsIgnoreCase(currentUser.getRole().trim());
    }

    public static boolean isLecturer() {
        return currentUser != null &&
               "LECTURER".equalsIgnoreCase(currentUser.getRole().trim());
    }
    
    public static boolean isStudent() {
        return currentUser != null &&
               "STUDENT".equalsIgnoreCase(currentUser.getRole().trim());
    }


    public static void clear() {
        currentUser = null;
        currentLecturer = null;
    }
}
