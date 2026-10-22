package controller;

import model.Notification;
import rmi.ServiceConnector;
import java.util.Collections;
import java.util.List;

public class NotificationController {

    public static List<Notification> findAll() {
        try {
            return ServiceConnector.getService().findAllNotifications();
        } catch (Exception e) {
            // Fallback: userId=0 returns all on server side
            try { return ServiceConnector.getService().findNotificationsByUser(0); }
            catch (Exception ex) {
                ex.printStackTrace(); return Collections.emptyList(); }
        }
    }

    public static List<Notification> findByUser(int userId) {
        try { return ServiceConnector.getService().findNotificationsByUser(userId); }
        catch (Exception e) {
            e.printStackTrace(); return Collections.emptyList(); 
        }
    }

    public static boolean markAsRead(int id) {
        try { 
            return ServiceConnector.getService().markAsRead(id);
        }
        catch (Exception e) { 
            e.printStackTrace(); return false; 
        }
    }
}
