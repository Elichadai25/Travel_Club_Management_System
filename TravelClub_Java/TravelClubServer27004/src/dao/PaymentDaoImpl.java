package dao;

import model.Payment;
import util.HibernateUtil;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PaymentDaoImpl implements IPaymentDao {

    @Override
    public Payment save(Payment payment) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.save(payment);
            tx.commit();
            return payment;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }

    @Override
    public Payment update(Payment payment) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.update(payment);
            tx.commit();
            return payment;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }

    @Override
    public boolean delete(int id) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            Payment p = (Payment) s.get(Payment.class, id);
            if (p != null) { s.delete(p); tx.commit(); return true; }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return false;
        } finally { s.close(); }
    }

    @Override
    public Payment findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Payment) s.get(Payment.class, id);
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }

    @Override
    public List<Payment> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Payment ORDER BY paymentDate DESC").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

    @Override
    public Payment findByBookingId(int bookingId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Payment) s.createQuery("FROM Payment WHERE booking.bookingId = :bid")
                    .setParameter("bid", bookingId).uniqueResult();
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }

    @Override
    public List<Payment> findByStatus(String status) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Payment WHERE status = :st")
                    .setParameter("st", status).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

    @Override
    public double getTotalRevenue() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            Double total = (Double) s.createQuery(
                    "SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'").uniqueResult();
            return total != null ? total : 0.0;
        } catch (Exception e) { e.printStackTrace(); return 0.0; } finally { s.close(); }
    }
}
