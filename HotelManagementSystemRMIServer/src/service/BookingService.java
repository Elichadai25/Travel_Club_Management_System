/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.;
import model.Booking;

/**
 *
 * @author USER
 */
public interface BookingService extends Remote{
    Booking createBookingRecord(Booking theBooking) throws RemoteException;
    Booking updateBookingRecord(Booking theBooking) throws RemoteException;
    
}
