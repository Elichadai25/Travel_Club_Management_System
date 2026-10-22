package dao;

import model.Booking;
import util.HibernateUtil;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BookingDao implements IBookingDao {
//Create Booking
    @Override
    public Booking save(Booking booking) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.save(booking);
            tx.commit();
            return booking;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
    //Update Booking

    @Override
    public Booking update(Booking booking) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.update(booking);
            tx.commit();
            return booking;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
    //Delete booking

    @Override
    public boolean delete(int id) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            Booking b = (Booking) s.get(Booking.class, id);
            if (b != null) { s.delete(b); tx.commit(); return true; }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return false;
        } finally { s.close(); }
    }
//Find Booking by Id
    @Override
    public Booking findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Booking) s.get(Booking.class, id);
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }
//Find All Booking
    @Override
    public List<Booking> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Booking ORDER BY bookingDate DESC").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

    //Find booking by User Id
    @Override
    public List<Booking> findByUserId(int userId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Booking WHERE user.userId = :uid")
                    .setParameter("uid", userId).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
    //Find Booking by Trip Id

    @Override
    public List<Booking> findByTripId(int tripId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Booking WHERE trip.tripId = :tid")
                    .setParameter("tid", tripId).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
    //Find Booking by Status

    @Override
    public List<Booking> findByStatus(String status) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Booking WHERE status = :st")
                    .setParameter("st", status).list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

}
