package rmi;

import model.Booking;
import model.Trip;
import model.User;
import model.Payment;
import model.Notification;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITravelClubService extends Remote {
    // User operations
    User registerUser(User user) throws RemoteException;
    User updateUser(User user) throws RemoteException;
    boolean deleteUser(int id) throws RemoteException;
    User findUserById(int id) throws RemoteException;
    List<User> findAllUsers() throws RemoteException;
    User login(String email, String password) throws RemoteException;
    boolean emailExists(String email) throws RemoteException;
    boolean phoneExists(String phone) throws RemoteException;
    
    // Trip operations
    Trip saveTrip(Trip trip) throws RemoteException;
    Trip updateTrip(Trip trip) throws RemoteException;
    boolean deleteTrip(int id) throws RemoteException;
    Trip findTripById(int id) throws RemoteException;
    List<Trip> findAllTrips() throws RemoteException;
    List<Trip> searchTrips(String destination) throws RemoteException;
    List<Trip> findActiveTrips() throws RemoteException;
    
    // Booking operations
    Booking createBooking(Booking booking) throws RemoteException;
    Booking updateBooking(Booking booking) throws RemoteException;
    boolean deleteBooking(int id) throws RemoteException;
    Booking findBookingById(int id) throws RemoteException;
    List<Booking> findAllBookings() throws RemoteException;
    List<Booking> findBookingsByUser(int userId) throws RemoteException;
    List<Booking> findBookingsByTrip(int tripId) throws RemoteException;
    
    // Payment operations
    Payment createPayment(Payment payment) throws RemoteException;
    Payment updatePayment(Payment payment) throws RemoteException;
    Payment findPaymentById(int id) throws RemoteException;
    List<Payment> findAllPayments() throws RemoteException;
    Payment findPaymentByBooking(int bookingId) throws RemoteException;
    double getTotalRevenue() throws RemoteException;
    
    // Notification operations
    Notification sendNotification(Notification notification) throws RemoteException;
    List<Notification> findNotificationsByUser(int userId) throws RemoteException;
    List<Notification> findUnreadNotifications(int userId) throws RemoteException;
    boolean markAsRead(int notificationId) throws RemoteException;
    
    // OTP operations
    String generateOTP(String email) throws RemoteException;
    boolean verifyOTP(String email, String otp) throws RemoteException;
}
