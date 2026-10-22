package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EUserRole role = EUserRole.MEMBER;

    @Column(nullable = false)
    private boolean active = true;

    // One User -> Many Bookings
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    // Many-to-Many: User <-> Trip (as favorites/wishlist)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_wishlist",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Trip> wishlist = new ArrayList<>();

    public User() {}

    public User(String email, String password, String fullName, String phoneNumber, EUserRole role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getUserId() { 
        return userId; }
    public void setUserId(int userId) { 
        this.userId = userId; }
    public String getEmail() { 
        return email; }
    public void setEmail(String email) {
        this.email = email; }
    public String getPassword() { 
        return password; }
    public void setPassword(String password) { 
        this.password = password; }
    public String getFullName() {
        return fullName; }
    public void setFullName(String fullName) { 
        this.fullName = fullName; }
    public String getPhoneNumber() { 
        return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; }
    public EUserRole getRole() { 
        return role; }
    public void setRole(EUserRole role) { 
        this.role = role; }
    public boolean isActive() { 
        return active; }
    public void setActive(boolean active) { 
        this.active = active; }
    public List<Booking> getBookings() { 
        return bookings; }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings; }
    public List<Trip> getWishlist() {
        return wishlist; }
    public void setWishlist(List<Trip> wishlist) {
        this.wishlist = wishlist; }

    @Override
    public String toString() { 
        return fullName + " (" + email + ")"; }
}
