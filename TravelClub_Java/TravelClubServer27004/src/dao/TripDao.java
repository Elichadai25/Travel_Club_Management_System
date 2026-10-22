package dao;

import model.Trip;
import util.HibernateUtil;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TripDao implements ITripDao {
//Create Trip
    @Override
    public Trip save(Trip trip) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.save(trip);
            tx.commit();
            return trip;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
// Update Trip
    @Override
    public Trip update(Trip trip) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            s.update(trip);
            tx.commit();
            return trip;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return null;
        } finally { s.close(); }
    }
    //Delete Trip

    @Override
    public boolean delete(int id) {
        Transaction tx = null;
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            tx = s.beginTransaction();
            Trip t = (Trip) s.get(Trip.class, id);
            if (t != null) { s.delete(t); tx.commit(); return true; }
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace(); return false;
        } finally { s.close(); }
    }
    //Find Trip by Id

    @Override
    public Trip findById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Trip) s.get(Trip.class, id);
        } catch (Exception e) { e.printStackTrace(); return null; } finally { s.close(); }
    }
//Find All Trip
    @Override
    public List<Trip> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Trip ORDER BY departureDate").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
    
//Find trip by destination
    @Override
    public List<Trip> findByDestination(String destination) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Trip WHERE LOWER(destination) LIKE :d OR LOWER(country) LIKE :d")
                    .setParameter("d", "%" + destination.toLowerCase() + "%").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }
//Find active Trips
    @Override
    public List<Trip> findActiveTrips() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("FROM Trip WHERE status = 'ACTIVE' AND availableSlots > 0").list();
        } catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); } finally { s.close(); }
    }

}
