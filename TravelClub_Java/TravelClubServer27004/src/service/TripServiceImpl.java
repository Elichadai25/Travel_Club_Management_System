/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import model.Trip;

public abstract class TripServiceImpl
        extends UnicastRemoteObject
        implements TripService {

    public TripServiceImpl() throws Exception {
        super();
    }

    public List<Trip> findAllTrips() {

        return new ArrayList<>();
    }
}