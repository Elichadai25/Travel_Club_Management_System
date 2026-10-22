/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Impl;

import dao.TripDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Trip;
import service.TripService;

/**
 *
 * @trip elichadai
 */
public class TripServiceImpl extends UnicastRemoteObject implements TripService{

    // call dao to persist data
    TripDao dao = new TripDao();
    
    public TripServiceImpl() throws RemoteException{
        
    }
    
    @Override
    public Trip registerTripRecord(Trip theTrip) throws RemoteException {
        return dao.registerTrip(theTrip);
    }

    @Override
    public Trip updateTripRecord(Trip theTrip) throws RemoteException {
        return dao.updateTrip(theTrip);
    }

    @Override
    public Trip deleteTripRecord(Trip theTrip) throws RemoteException {
        return dao.deleteTrip(theTrip);
    }

    @Override
    public Trip findTripRecordById(Trip theTrip) throws RemoteException {
        return dao.findTripById(theTrip);
    }

    @Override
    public List<Trip> findAllTripRecords() throws RemoteException {
        return dao.findAllTrips();
    }
    
}
