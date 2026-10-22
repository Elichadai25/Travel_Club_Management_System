/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Client;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author USER
 */
public class ClientDao {
    //CRUD Operation
    public Client createClient(Client clientObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Client theClient = (Client)ss.get(Client.class, clientObj.getId());
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.save(clientObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return clientObj;
            
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public Client createUpdate(Client clientObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.update(clientObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return clientObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Client deleteClient(Client clientObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            // step 2: creat transaction CUD
            Transaction tr = ss.beginTransaction();
            //step 3: perfom action
            ss.delete(clientObj);
            //step 4: commit transaction
            tr.commit();
            //ss.beginTransaction().commit();
            //step 5:close session
            ss.close();
            
            return clientObj;
            
        }catch(HibernateException ex){
            return null;
        }
    }
    public Client findByIdClient(Client clientObj){
        try{
            //step 1: create Session
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Client theClient = (Client)ss.get(Client.class, clientObj.getId());
            
            ss.close();
            Client theclient = null;
            
            
            return theclient;
            
        }catch(HibernateException ex){
            return null;
        }
        }
    public List<Client> findAll(){
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Client> client = ss.createQuery("SELECT client FROM Client  client").list();
            ss.close();
            List<Client> clients = null;
            return clients;
        }catch(HibernateException ex){
            return null;
        }
    }

    private void getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
