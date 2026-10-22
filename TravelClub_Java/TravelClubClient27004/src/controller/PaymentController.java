package controller;

import model.Payment;
import rmi.ServiceConnector;
import java.util.Collections;
import java.util.List;

public class PaymentController {

    public static Payment create(Payment payment) {
        try { return ServiceConnector.getService().createPayment(payment); }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    public static Payment findById(int id) {
        try { 
            return ServiceConnector.getService().findPaymentById(id); }
        catch (Exception e) {
            e.printStackTrace(); return null; 
        }
    }

    public static List<Payment> findAll() {
        try { return ServiceConnector.getService().findAllPayments(); }
        catch (Exception e) { 
            e.printStackTrace(); return Collections.emptyList();
        }
    }

    public static Payment findByBooking(int bookingId) {
        try { return ServiceConnector.getService().findPaymentByBooking(bookingId); }
        catch (Exception e) {
            return null;
        }
    }

    public static double getTotalRevenue() {
        try {
            return ServiceConnector.getService().getTotalRevenue();
        }
        catch (Exception e) {
            e.printStackTrace(); return 0.0; 
        }
    }
}
