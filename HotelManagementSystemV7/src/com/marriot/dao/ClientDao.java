/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriot.dao;
import com.marriot.model.Client;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class ClientDao {
     // CRUD operation
    
    // CREATE:
    public Client registerClient(Client clientObj){
        try{
            // step 1: create session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: create transaction
            Transaction tr = ss.beginTransaction();
            // step3: perfom action
            ss.save(clientObj);
            // step4: commit transaction
            tr.commit();
            // step 5: close session
            ss.close();
            return clientObj;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<Client> findAllClients() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}