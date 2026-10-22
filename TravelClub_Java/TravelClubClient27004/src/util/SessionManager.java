package util;

import model.User;

public class SessionManager {
    private static User currentUser;

    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static boolean isAdmin() { return currentUser != null && currentUser.getRole().name().equals("ADMIN"); }
    public static void logout() { currentUser = null; }
}
