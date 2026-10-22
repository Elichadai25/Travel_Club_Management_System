/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.implementation;

/**
 *
 * @author USER
 */
public class BookingServiceImpl extends UnicastRemoteObject implements BookingService{
   //Initialize DAO
    BookingDao dao = new BookingDao();
    public BookingServiceImpl throws RemoteException{
}
}
    @Override
    public Booking createBookingRecord(Booking theBook)
    
}
