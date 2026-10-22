/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Impl;

import dao.PaymentDaoImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Payment;
import service.PaymentService;

/**
 *
 * @payment elichadai
 */
public class PaymentServiceImpl extends UnicastRemoteObject implements PaymentService{

    // call dao to persist data
    PaymentDaoImpl dao = new PaymentDaoImpl();
    
    public PaymentServiceImpl() throws RemoteException{
        
    }
    
    @Override
    public Payment registerPaymentRecord(Payment thePayment) throws RemoteException {
        return dao.registerPayment(thePayment);
    }

    @Override
    public Payment updatePaymentRecord(Payment thePayment) throws RemoteException {
        return dao.updatePayment(thePayment);
    }

    @Override
    public Payment deletePaymentRecord(Payment thePayment) throws RemoteException {
        return dao.deletePayment(thePayment);
    }

    @Override
    public Payment findPaymentRecordById(Payment thePayment) throws RemoteException {
        return dao.findPaymentById(thePayment);
    }

    public List<Payment> findAllPaymentRecords() throws RemoteException {
        return dao.findAllPayments();
    }

    @Override
    public List<Payment> findAllPaymentsRecords() throws RemoteException {
        return dao.findAllPayments();
    }
    
}
