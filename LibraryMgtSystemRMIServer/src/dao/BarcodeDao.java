package dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.Barcode;
import org.hibernate.*;

/**
 *
 * @author jeremie
 */
public class BarcodeDao {
    // CRUD operations
    public Barcode registerBarcode(Barcode barcodeObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.save(barcodeObj);
            tr.commit();
            ss.close();
            return barcodeObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    public Barcode updateBarcode(Barcode barcodeObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.update(barcodeObj);
            tr.commit();
            ss.close();
            return barcodeObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Barcode deleteBarcode(Barcode barcodeObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss.beginTransaction();
            ss.delete(barcodeObj);
            tr.commit();
            ss.close();
            return barcodeObj;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Barcode findBarcodeById(Barcode barcodeObj){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Barcode found = (Barcode)ss.get(Barcode.class, barcodeObj.getId());
            ss.close();
            return found;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Barcode> findAllBarcodes(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<Barcode> barcodes = ss.createQuery("SELECT barcode FROM Barcode barcode").list();
            ss.close();
            return barcodes;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Collections.EMPTY_LIST;
    }
    
    public Barcode getNextBarcode(){
        try{
            Session ss = HibernateUtil.getSessionFactory().openSession();
            Random rand = new Random();
            Barcode theBarcode = new Barcode();
            theBarcode.setGenerateDate(LocalDate.now());
            LocalDate today = LocalDate.now();
            theBarcode.setCode("Book-"+today.getYear()+"-"+rand.nextInt(1000));
            ss.save(theBarcode);
            ss.beginTransaction().commit();
            ss.close();
            return theBarcode;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return null;
    }
}
