package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tripId;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String country;

    @Temporal(TemporalType.DATE)
    @Column(name = "departure_date", nullable = false)
    private Date departureDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date", nullable = false)
    private Date returnDate;

    @Column(nullable = false)
    private double price;

    @Column(name = "available_slots", nullable = false)
    private int availableSlots;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, CANCELLED, COMPLETED

    // One Trip -> Many Bookings
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    // Many-to-Many inverse side
    @ManyToMany(mappedBy = "wishlist", fetch = FetchType.LAZY)
    private List<User> interestedUsers = new ArrayList<>();

    public Trip() {}

    public Trip(String destination, String country, Date departureDate, Date returnDate,
                double price, int availableSlots, String description) {
        this.destination = destination;
        this.country = country;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.price = price;
        this.availableSlots = availableSlots;
        this.description = description;
    }

    public int getTripId() { return tripId; }
    public void setTripId(int tripId) { this.tripId = tripId; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Date getDepartureDate() { return departureDate; }
    public void setDepartureDate(Date departureDate) { this.departureDate = departureDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(int availableSlots) { this.availableSlots = availableSlots; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
    public List<User> getInterestedUsers() { return interestedUsers; }
    public void setInterestedUsers(List<User> interestedUsers) { this.interestedUsers = interestedUsers; }

    @Override
    public String toString() { return destination + ", " + country; }
}
