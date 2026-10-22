/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.TripService;
import service.Impl.TripServiceImpl;

/**
 *
 * @author USER
 */
public class Service {

    public static void main(String[] args) {

        try {

            System.out.println("Starting RMI Server...");

            Registry registry =
                    LocateRegistry.createRegistry(5000);

            TripService service =
                    new TripServiceImpl();

            registry.rebind("trip-service", service);

            System.out.println("Trip Service Registered");

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
    

