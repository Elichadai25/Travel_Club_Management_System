package controller;

import model.Booking;
import model.EBookingStatus;
import rmi.ServiceConnector;
import java.util.Collections;
import java.util.List;

public class BookingController {

    public static Booking create(Booking booking) {
        try { return ServiceConnector.getService().createBooking(booking); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public static boolean cancel(int id) {
        try {
            Booking b = ServiceConnector.getService().findBookingById(id);
            if (b == null) return false;
            b.setStatus(EBookingStatus.CANCELLED);  // FIX: use enum, not String
            return ServiceConnector.getService().updateBooking(b) != null;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static boolean delete(int id) {
        try { return ServiceConnector.getService().deleteBooking(id); }
        catch (Exception e) { e.printStackTrace(); return false; }
    }

    public static Booking findById(int id) {
        try { return ServiceConnector.getService().findBookingById(id); }
        catch (Exception e) { 
            e.printStackTrace(); return null; }
    }

    public static List<Booking> findAll() {
        try { return ServiceConnector.getService().findAllBookings(); }
        catch (Exception e) { 
            e.printStackTrace(); return Collections.emptyList(); 
        }
    }

    public static List<Booking> findByUser(int userId) {
        try { return ServiceConnector.getService().findBookingsByUser(userId); }
        catch (Exception e) { e.printStackTrace(); return Collections.emptyList(); }
    }

    public static List<Booking> findByStatus(EBookingStatus status) {
        try {
            List<Booking> all = ServiceConnector.getService().findAllBookings();
            all.removeIf(b -> b.getStatus() != status);  // FIX: compare enum to enum
            return all;
        } catch (Exception e) {
            e.printStackTrace(); return Collections.emptyList();
        }
    }
}
