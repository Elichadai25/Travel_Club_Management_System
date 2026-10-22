/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class RoomDao {
    //CRUD Operation
    public Room createRoom(Room roomObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Room theRoom = (Room)ss.get(Room.class, roomObj.getId());
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.save(roomObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return roomObj;
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Room createUpdate(Room roomObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.update(roomObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return roomObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Room deleteRoom(Room roomObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.delete(roomObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return roomObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Room findByIdRoom(Room roomObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Room theRoom = (Room)ss.get(Room.class, roomObj.getId());
            
            ss.close();
            Room theroom = null;
            
            
            return theroom;
            
        }catch(HibernateException ex){
            return null;
        }
        }
    public List<Room> findAll(){
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Room> room = ss.createQuery("SELECT room FROM Room  room").list();
            ss.close();
            List<Room> rooms = null;
            return rooms;
        }catch(HibernateException ex){
            return null;
        }
    }

    private void getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
