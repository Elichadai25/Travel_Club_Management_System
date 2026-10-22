/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Payment;

/**
 *
 * @payment elichadai
 */
public interface PaymentService extends Remote{
    // method signature/ definition
    Payment registerPaymentRecord(Payment thePayment) throws RemoteException;
    Payment updatePaymentRecord(Payment thePayment) throws RemoteException;
    Payment deletePaymentRecord(Payment thePayment) throws RemoteException;
    Payment findPaymentRecordById(Payment thePayment) throws RemoteException;
    List<Payment> findAllPaymentsRecords() throws RemoteException;    
}
