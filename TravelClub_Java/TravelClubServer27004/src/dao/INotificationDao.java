package dao;

import model.Notification;
import java.util.List;

public interface INotificationDao extends IGenericDao<Notification> {
    List<Notification> findByUserId(int userId);
    List<Notification> findUnreadByUserId(int userId);
}
