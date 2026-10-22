/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Payment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class PaymentDao {
    //CRUD Operation
    public Payment createPayment(Payment paymentObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Payment thePayment = (Payment)ss.get(Payment.class, paymentObj.getId());
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.save(paymentObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return paymentObj;
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Payment createUpdate(Payment paymentObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.update(paymentObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return paymentObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Payment deletePayment(Payment paymentObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.delete(paymentObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return paymentObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Payment findByIdPayment(Payment paymentObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Payment thePayment = (Payment)ss.get(Payment.class, paymentObj.getId());
            
            ss.close();
            Payment thepayment = null;
            
            
            return thepayment;
            
        }catch(HibernateException ex){
            return null;
        }
        }
    public List<Payment> findAll(){
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Payment> payment = ss.createQuery("SELECT payment FROM Payment  payment").list();
            ss.close();
            List<Payment> payments = null;
            return payments;
        }catch(HibernateException ex){
            return null;
        }
    }

    private void getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
