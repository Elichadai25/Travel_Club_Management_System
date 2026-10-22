package dao;

import java.util.Collections;
import java.util.List;
import model.Author;
import org.hibernate.*;

/**
 *
 * @author jeremie
 */
public class AuthorDao {
    // CRUD operations
    public Author registerAuthor(Author authorObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(authorObj);
            tr.commit();
            ss.close();
            return authorObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    public Author updateAuthor(Author authorObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(authorObj);
            tr.commit();
            ss.close();
            return authorObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Author deleteAuthor(Author authorObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(authorObj);
            tr.commit();
            ss.close();
            return authorObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Author findAuthorById(Author authorObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Author found = (Author)ss.get(Author.class, authorObj.getId());
            ss.close();
            return found;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Author> findAllAuthors(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Author> authors = ss.createQuery("SELECT author FROM Author author").list();
            ss.close();
            return authors;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
}
