package controller;

import model.User;
import rmi.ServiceConnector;
import java.util.Collections;
import java.util.List;

public class UserController {

    public static boolean save(User user) {
        try { return ServiceConnector.getService().registerUser(user) != null; }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean update(User user) {
        try {
            return ServiceConnector.getService().updateUser(user) != null; }
        catch (Exception e) { 
            e.printStackTrace(); return false;
        }
    }

    public static boolean delete(int id) {
        try {
            return ServiceConnector.getService().deleteUser(id);
        }
        catch (Exception e) {
            e.printStackTrace(); return false;
        }
    }

    public static User findById(int id) {
        try {
            return ServiceConnector.getService().findUserById(id);
        }
        catch (Exception e) {
            e.printStackTrace(); return null; 
        }
    }

    public static List<User> findAll() {
        try { 
            return ServiceConnector.getService().findAllUsers(); 
        }
        catch (Exception e) {
            e.printStackTrace(); return Collections.emptyList();
        }
    }
}
