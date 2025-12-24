package Model;

import Model.user;
import Model.lecture;
//import Model.student;

public class userSession {

    private static user currentUser;
    private static lecture currentLecture;

    public static void setUser(user u) {
        currentUser = u;
    }

    public static user getUser() {
        return currentUser;
    }

    public static void setLecture(lecture l) {
        currentLecture = l;
    }

    public static lecture getLecture() {
        return currentLecture;
    }

    public static void clear() {
        currentUser = null;
        currentLecture = null;
    }
}
