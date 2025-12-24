package Session;

import Model.user;

public class UserSession {
    private static user currentUser;

    public static void setUser(user u) {
        currentUser = u;
        System.out.println("User login: " + u.getUsername());
    }

    public static user getUser() {
        return currentUser;
    }

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
    }
}
