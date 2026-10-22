package model;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {
    public static final long serialVersionUID = 1L;

    private int tripId;
    private String destination;
    private String country;
    private Date departureDate;
    private Date returnDate;
    private double price;
    private int availableSlots;
    private String description;
    private String status = "ACTIVE";

    public Trip() {}

    public Trip(int tripId, String destination, String country, Date departureDate, Date returnDate, double price, int availableSlots, String description) {
        this.tripId = tripId;
        this.destination = destination;
        this.country = country;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.price = price;
        this.availableSlots = availableSlots;
        this.description = description;
    }

    public Trip(String trim, String trim0, Date dep, Date ret, double price, int slots, String trim1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    @Override
    public String toString() {
        return destination + ", " + country;
    }
}
