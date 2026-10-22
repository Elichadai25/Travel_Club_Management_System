package dao;

import java.util.Collections;
import java.util.List;
import model.Borrowing;
import org.hibernate.*;

/**
 *
 * @author jeremie
 */
public class BorrowingDao {
    // CRUD operations
    public Borrowing registerBorrowing(Borrowing borrowingObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(borrowingObj);
            tr.commit();
            ss.close();
            return borrowingObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    public Borrowing updateBorrowing(Borrowing borrowingObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(borrowingObj);
            tr.commit();
            ss.close();
            return borrowingObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Borrowing deleteBorrowing(Borrowing borrowingObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(borrowingObj);
            tr.commit();
            ss.close();
            return borrowingObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Borrowing findBorrowingById(Borrowing borrowingObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Borrowing found = (Borrowing)ss.get(Borrowing.class, borrowingObj.getId());
            ss.close();
            return found;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Borrowing> findAllBorrowings(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Borrowing> borrowings = ss.createQuery("SELECT borrowing FROM Borrowing borrowing").list();
            ss.close();
            return borrowings;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
}
