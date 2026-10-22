/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Hotel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class HotelDao {
    //CRUD Operation
    public Hotel createHotel(Hotel hotelObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Hotel theHotel = (Hotel)ss.get(Hotel.class, hotelObj.getId());
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.save(hotelObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return hotelObj;
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Hotel createUpdate(Hotel hotelObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.update(hotelObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return hotelObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Hotel deleteHotel(Hotel hotelObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.delete(hotelObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return hotelObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Hotel findByIdHotel(Hotel hotelObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Hotel theHotel = (Hotel)ss.get(Hotel.class, hotelObj.getId());
            
            ss.close();
            Hotel thehotel = null;
            
            
            return thehotel;
            
        }catch(HibernateException ex){
            return null;
        }
        }
    public List<Hotel> findAll(){
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Hotel> hotel = ss.createQuery("SELECT hotel FROM Hotel  hotel").list();
            ss.close();
            List<Hotel> hotels = null;
            return hotels;
        }catch(HibernateException ex){
            return null;
        }
    }

    private void getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
