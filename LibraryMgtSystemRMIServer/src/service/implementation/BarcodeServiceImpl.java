/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.implementation;

import dao.BarcodeDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Barcode;
import service.BarcodeService;

/**
 *
 * @author jeremie
 */
public class BarcodeServiceImpl extends UnicastRemoteObject implements BarcodeService{

    // call dao to persist data
    BarcodeDao dao = new BarcodeDao();
    
    public BarcodeServiceImpl() throws RemoteException{
        
    }
    
    @Override
    public Barcode registerBarcodeRecord(Barcode theBarcode) throws RemoteException {
        return dao.registerBarcode(theBarcode);
    }

    @Override
    public Barcode updateBarcodeRecord(Barcode theBarcode) throws RemoteException {
        return dao.updateBarcode(theBarcode);
    }

    @Override
    public Barcode deleteBarcodeRecord(Barcode theBarcode) throws RemoteException {
        return dao.deleteBarcode(theBarcode);
    }

    @Override
    public Barcode findBarcodeRecordById(Barcode theBarcode) throws RemoteException {
        return dao.findBarcodeById(theBarcode);
    }

    @Override
    public List<Barcode> findAllBarcodeRecords() throws RemoteException {
        return dao.findAllBarcodes();
    }

    @Override
    public Barcode getNextBarcode() throws RemoteException {
        return dao.getNextBarcode();    
    }
    
}
