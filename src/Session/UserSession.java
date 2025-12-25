package Session;

import Model.user;
import Model.lecturer;

public class UserSession {
    private static user currentUser;
    private static lecturer currentLecturer;

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


    public static void clear() {
        currentUser = null;
        currentLecturer = null;
    }
}
