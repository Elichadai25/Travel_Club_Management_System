/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Trip;

/**
 *
 * @trip elichadai
 */
public interface TripService extends Remote{
    // method signature/ definition
    Trip registerTripRecord(Trip theTrip) throws RemoteException;
    Trip updateTripRecord(Trip theTrip) throws RemoteException;
    Trip deleteTripRecord(Trip theTrip) throws RemoteException;
    Trip findTripRecordById(Trip theTrip) throws RemoteException;
    List<Trip> findAllTripRecords() throws RemoteException;    
}
