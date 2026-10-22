/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Impl;

import dao.BookingDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Booking;
import service.BookingService;

/**
 *
 * @booking elichadai
 */
public class BookingServiceImpl extends UnicastRemoteObject implements BookingService{

    // call dao to persist data
    BookingDao dao = new BookingDao();
    
    public BookingServiceImpl() throws RemoteException{
        
    }
    
    @Override
    public Booking registerBookingRecord(Booking theBooking) throws RemoteException {
        return dao.registerBooking(theBooking);
    }

    @Override
    public Booking updateBookingRecord(Booking theBooking) throws RemoteException {
        return dao.updateBooking(theBooking);
    }

    @Override
    public Booking deleteBookingRecord(Booking theBooking) throws RemoteException {
        return dao.deleteBooking(theBooking);
    }

    @Override
    public Booking findBookingRecordById(Booking theBooking) throws RemoteException {
        return dao.findBookingById(theBooking);
    }

    @Override
    public List<Booking> findAllBookingRecords() throws RemoteException {
        return dao.findAllBookings();
    }
    
}
