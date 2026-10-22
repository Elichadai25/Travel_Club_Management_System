/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Booking;

/**
 *
 * @booking elichadai
 */
public interface BookingService extends Remote{
    // method signature/ definition
    Booking registerBookingRecord(Booking theBooking) throws RemoteException;
    Booking updateBookingRecord(Booking theBooking) throws RemoteException;
    Booking deleteBookingRecord(Booking theBooking) throws RemoteException;
    Booking findBookingRecordById(Booking theBooking) throws RemoteException;
    List<Booking> findAllBookingRecords() throws RemoteException;    
}
