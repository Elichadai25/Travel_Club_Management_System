package dao;

import model.Notification;
import util.HibernateUtil;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NotificationDao implements INotificationDao {
//Create Notification
    public Notification save(Notification n) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.save(n);
            tx.commit();
            return n;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
// Update Notification
    public Notification update(Notification n) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.update(n);
            tx.commit();
            return n;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
//Delete  Notification
    public boolean delete(int id) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            Notification n = (Notification) s.get(Notification.class, id);
            if (n != null) { s.delete(n); tx.commit(); return true; }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return false;
        } finally { s.close(); }
    }
// Find  Notification ById
    public Notification findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Notification) s.get(Notification.class, id);
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }
// FindAll Notification
    public List<Notification> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Notification ORDER BY sentAt DESC").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
//Find Notification ByuserId
    public List<Notification> findByUserId(int userId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            if (userId == 0) {
                return s.createQuery("FROM Notification ORDER BY sentAt DESC").list();
            }
            return s.createQuery("FROM Notification WHERE user.userId = :uid ORDER BY sentAt DESC")
                    .setParameter("uid", userId).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
// Find UnreadByUser Id
    public List<Notification> findUnreadByUserId(int userId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Notification WHERE user.userId = :uid AND read = false")
                    .setParameter("uid", userId).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

}
