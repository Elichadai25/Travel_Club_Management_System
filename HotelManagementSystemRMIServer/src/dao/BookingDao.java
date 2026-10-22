/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Booking;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class BookingDao {
    //CRUD Operation
    public Booking createBooking(Booking bookingObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Booking theBooking = (Booking)ss.get(Booking.class, bookingObj.getId());
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.save(bookingObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return bookingObj;
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Booking createUpdate(Booking bookingObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.update(bookingObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return bookingObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Booking deleteBooking(Booking bookingObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.delete(bookingObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return bookingObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Booking findByIdBooking(Booking bookingObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Booking theBooking = (Booking)ss.get(Booking.class, bookingObj.getId());
            
            ss.close();
            Booking thebooking = null;
            
            
            return thebooking;
            
        }catch(HibernateException ex){
            return null;
        }
        }
    public List<Booking> findAll(){
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Booking> booking = ss.createQuery("SELECT booking FROM Booking  booking").list();
            ss.close();
            List<Booking> bookings = null;
            return bookings;
        }catch(HibernateException ex){
            return null;
        }
    }

    private void getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
