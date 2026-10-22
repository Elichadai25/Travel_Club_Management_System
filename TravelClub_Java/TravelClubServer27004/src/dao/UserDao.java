package dao;

import model.User;
import util.HibernateUtil;
import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao implements IUserDao {
// Create User
    @Override
    public User save(User user) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.save(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
//update User
    @Override
    public User update(User user) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.update(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
//Delete user
    @Override
    public boolean delete(int id) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            User u = (User) s.get(User.class, id);
            if (u != null) { s.delete(u); tx.commit(); return true; }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return false;
        } finally { s.close(); }
    }
//Find user by Id
    @Override
    public User findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (User) s.get(User.class, id);
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }

    //Find All Users
    @Override
    public List<User> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM User").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
//Find User by Email and password
    @Override
    public User findByEmailAndPassword(String email, String password) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query q = s.createQuery("FROM User WHERE email = :e AND password = :p");
            q.setParameter("e", email);
            q.setParameter("p", password);
            return (User) q.uniqueResult();
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }

    @Override
    public boolean emailExists(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = (Long) s.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :e")
                    .setParameter("e", email).uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) { e.printStackTrace(); return false; } finally { s.close(); }
    }

    @Override
    public boolean phoneExists(String phone) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = (Long) s.createQuery("SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :p")
                    .setParameter("p", phone).uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) { e.printStackTrace(); return false; } finally { s.close(); }
    }

    @Override
    public User findByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Query q = s.createQuery("FROM User WHERE email = :e");
            q.setParameter("e", email);
            return (User) q.uniqueResult();
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }
}
