package dao;

import java.util.Collections;
import java.util.List;
import model.Book;
import org.hibernate.*;

/**
 *
 * @author jeremie
 */
public class BookDao {
    // CRUD operations
    public Book registerBook(Book bookObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(bookObj);
            tr.commit();
            ss.close();
            return bookObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    public Book updateBook(Book bookObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(bookObj);
            tr.commit();
            ss.close();
            return bookObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Book deleteBook(Book bookObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(bookObj);
            tr.commit();
            ss.close();
            return bookObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Book findBookById(Book bookObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Book found = (Book)ss.get(Book.class, bookObj.getId());
            ss.close();
            return found;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Book> findAllBooks(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Book> books = ss.createQuery("SELECT book FROM Book book").list();
            ss.close();
            return books;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
}
