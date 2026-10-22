/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import model.Trip;
import service.Impl.BookingServiceImpl;
import service.TripServiceImpl;
import service.Impl.UserServiceImpl;

/**
 *
 * @author Elichadai
 */
public class Server {
    public static void main(String[] args) {
        try{
            // step 1: configure server properties
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            // create registry
            Registry theRegistry = LocateRegistry.createRegistry(5000);
            theRegistry.rebind("user-service", (Remote) new UserServiceImpl());
            theRegistry.rebind("payment-service", (Remote) new paymentServiceImpl());
            theRegistry.rebind("booking-service", (Remote) new BookingServiceImpl());
            theRegistry.rebind("trip-service", new TripServiceImpl() {
                @Override
                public Trip registerTripRecord(Trip theTrip) throws RemoteException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Trip updateTripRecord(Trip theTrip) throws RemoteException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Trip deleteTripRecord(Trip theTrip) throws RemoteException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Trip findTripRecordById(Trip theTrip) throws RemoteException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public List<Trip> findAllTripRecords() throws RemoteException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            theRegistry.rebind("notification-service", new NotificationServiceImpl());
            

            System.out.println("Server is running on port 5000");
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

    

