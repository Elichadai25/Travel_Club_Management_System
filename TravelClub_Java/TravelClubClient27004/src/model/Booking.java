package model;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    public static final long serialVersionUID = 1L;

    private int bookingId;
    private Date bookingDate;
    private EBookingStatus status = EBookingStatus.PENDING;
    private int numSeats = 1;
    private double totalAmount;
    private User user;
    private Trip trip;
    private Payment payment;

    public Booking() {}

    public Booking(Date bookingDate, int numSeats, User user, Trip trip) {
        this.bookingDate = bookingDate;
        this.numSeats = numSeats;
        this.user = user;
        this.trip = trip;
        this.totalAmount = (trip != null) ? trip.getPrice() * numSeats : 0.0;
        this.status = EBookingStatus.PENDING;
    }

    public int getBookingId() { 
        return bookingId; 
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public Date getBookingDate() { 
        return bookingDate; 
    }
    public void setBookingDate(Date bookingDate) { 
        
        this.bookingDate = bookingDate;
    }
    public EBookingStatus getStatus() { 
        return status; 
    }
    public void setStatus(EBookingStatus status) { 
        this.status = status; 
    }
    public int getNumSeats() {
        return numSeats; 
    }
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
    public double getTotalAmount() { 
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public User getUser() {
        
       return user;
    }
    public void setUser(User user) { 
        this.user = user;
    }
    public Trip getTrip() {
        return trip; 
    }
    public void setTrip(Trip trip) {
        this.trip = trip; 
    }
    public Payment getPayment() {
        return payment; 
    }
    public void setPayment(Payment payment) { 
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Booking#" + bookingId + " [" + (trip != null ? trip.getDestination() : "?") + "] " + status;
    }
}
