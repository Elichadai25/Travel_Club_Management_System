package dao;

import java.util.Collections;
import java.util.List;
import model.Member;
import org.hibernate.*;

/**
 *
 * @author jeremie
 */
public class MemberDao {
    // CRUD operations
    public Member registerMember(Member memberObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(memberObj);
            tr.commit();
            ss.close();
            return memberObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    public Member updateMember(Member memberObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(memberObj);
            tr.commit();
            ss.close();
            return memberObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Member deleteMember(Member memberObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(memberObj);
            tr.commit();
            ss.close();
            return memberObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Member findMemberById(Member memberObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Member found = (Member)ss.get(Member.class, memberObj.getId());
            ss.close();
            return found;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Member> findAllMembers(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Member> members = ss.createQuery("SELECT member FROM Member member").list();
            ss.close();
            return members;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
}
