package controller;

import model.Trip;
import rmi.ServiceConnector;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class TripController {

    public static boolean save(Trip trip) {
        try { return ServiceConnector.getService().saveTrip(trip) != null; }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static Trip saveAndReturn(Trip trip) {
        try {
            return ServiceConnector.getService().saveTrip(trip);
        } catch (java.rmi.RemoteException e) {
            // Show the actual server-side error message
            String msg = e.getMessage();
            if (msg == null && e.getCause() != null) msg = e.getCause().getMessage();
            if (msg == null) msg = e.getClass().getSimpleName();
            JOptionPane.showMessageDialog(null,
                "Server error: " + msg,
                "Save Failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Connection error: " + e.getMessage() + "\nMake sure the server is running.",
                "Save Failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean update(Trip trip) {
        try { return ServiceConnector.getService().updateTrip(trip) != null; }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean delete(int id) {
        try { return ServiceConnector.getService().deleteTrip(id); }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static Trip findById(int id) {
        try { return ServiceConnector.getService().findTripById(id); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public static List<Trip> findAll() {
        try { return ServiceConnector.getService().findAllTrips(); }
        catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); }
    }

    public static List<Trip> search(String destination) {
        try { return ServiceConnector.getService().searchTrips(destination); }
        catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); }
    }

    public static List<Trip> findActiveTrips() {
        try { return ServiceConnector.getService().findActiveTrips(); }
        catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); }
    }
}
