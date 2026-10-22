package service.Impl;

import dao.BookingDao;
import dao.NotificationDao;
import dao.PaymentDaoImpl;
import dao.TripDao;
import dao.UserDao;
import model.Booking;
import model.Notification;
import model.Payment;
import model.Trip;
import model.User;
import rmi.ITravelClubService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TravelClubServiceImpl extends UnicastRemoteObject implements ITravelClubService {

    private final UserDao userDao = new UserDao();
    private final TripDao tripDao = new TripDao();
    private final BookingDao bookingDao = new BookingDao();
    private final PaymentDaoImpl paymentDao = new PaymentDaoImpl();
    private final NotificationDao notifDao = new NotificationDao();
    private final Map<String, String> otpStore = new HashMap<>();

    public TravelClubServiceImpl() throws RemoteException {
        super();
    }

    // ===== USER =====
    @Override
    public User registerUser(User user) throws RemoteException {
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) throws RemoteException {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(int id) throws RemoteException {
        return userDao.delete(id);
    }

    @Override
    public User findUserById(int id) throws RemoteException {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAllUsers() throws RemoteException {
        return userDao.findAll();
    }

    @Override
    public User login(String email, String password) throws RemoteException {
        return userDao.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean emailExists(String email) throws RemoteException {
        return userDao.emailExists(email);
    }

    @Override
    public boolean phoneExists(String phone) throws RemoteException {
        return userDao.phoneExists(phone);
    }

    // ===== TRIP =====
    @Override
    public Trip saveTrip(Trip trip) throws RemoteException {
        return tripDao.save(trip);
    }

    @Override
    public Trip updateTrip(Trip trip) throws RemoteException {
        return tripDao.update(trip);
    }

    @Override
    public boolean deleteTrip(int id) throws RemoteException {
        return tripDao.delete(id);
    }

    @Override
    public Trip findTripById(int id) throws RemoteException {
        return tripDao.findById(id);
    }

    @Override
    public List<Trip> findAllTrips() throws RemoteException {
        return tripDao.findAll();
    }

    @Override
    public List<Trip> searchTrips(String destination) throws RemoteException {
        return tripDao.findByDestination(destination);
    }

    @Override
    public List<Trip> findActiveTrips() throws RemoteException {
        return tripDao.findActiveTrips();
    }

    // ===== BOOKING =====
    @Override
    public Booking createBooking(Booking booking) throws RemoteException {
        Booking saved = bookingDao.save(booking);
        if (saved != null) {
            Trip trip = tripDao.findById(booking.getTrip().getTripId());
            if (trip != null) {
                trip.setAvailableSlots(trip.getAvailableSlots() - booking.getNumSeats());
                tripDao.update(trip);
            }
            Notification n = new Notification(
                "Your booking #" + saved.getBookingId() + " for " + booking.getTrip().getDestination() + " is confirmed!",
                "BOOKING", booking.getUser());
            notifDao.save(n);
        }
        return saved;
    }

    @Override
    public Booking updateBooking(Booking booking) throws RemoteException {
        return bookingDao.update(booking);
    }

    @Override
    public boolean deleteBooking(int id) throws RemoteException {
        Booking b = bookingDao.findById(id);
        if (b != null && "PENDING".equals(b.getStatus())) {
            Trip trip = tripDao.findById(b.getTrip().getTripId());
            if (trip != null) {
                trip.setAvailableSlots(trip.getAvailableSlots() + b.getNumSeats());
                tripDao.update(trip);
            }
        }
        return bookingDao.delete(id);
    }

    @Override
    public Booking findBookingById(int id) throws RemoteException {
        return bookingDao.findById(id);
    }

    @Override
    public List<Booking> findAllBookings() throws RemoteException {
        return bookingDao.findAll();
    }

    @Override
    public List<Booking> findBookingsByUser(int userId) throws RemoteException {
        return bookingDao.findByUserId(userId);
    }

    @Override
    public List<Booking> findBookingsByTrip(int tripId) throws RemoteException {
        return bookingDao.findByTripId(tripId);
    }

    // ===== PAYMENT =====
    @Override
    public Payment createPayment(Payment payment) throws RemoteException {
        Payment saved = paymentDao.save(payment);
        if (saved != null) {
            Booking b = bookingDao.findById(payment.getBooking().getBookingId());
            if (b != null) {
                b.setStatus("CONFIRMED");
                bookingDao.update(b);
                Notification n = new Notification(
                    "Payment of $" + payment.getAmount() + " received. Ref: " + payment.getTransactionRef(),
                    "PAYMENT", b.getUser());
                notifDao.save(n);
            }
        }
        return saved;
    }

    @Override
    public Payment updatePayment(Payment payment) throws RemoteException {
        return paymentDao.update(payment);
    }

    @Override
    public Payment findPaymentById(int id) throws RemoteException {
        return paymentDao.findById(id);
    }

    @Override
    public List<Payment> findAllPayments() throws RemoteException {
        return paymentDao.findAll();
    }

    @Override
    public Payment findPaymentByBooking(int bookingId) throws RemoteException {
        return paymentDao.findByBookingId(bookingId);
    }

    @Override
    public double getTotalRevenue() throws RemoteException {
        return paymentDao.getTotalRevenue();
    }

    // ===== NOTIFICATION =====
    @Override
    public Notification sendNotification(Notification notification) throws RemoteException {
        return notifDao.save(notification);
    }

    @Override
    public List<Notification> findNotificationsByUser(int userId) throws RemoteException {
        return notifDao.findByUserId(userId);
    }

    @Override
    public List<Notification> findUnreadNotifications(int userId) throws RemoteException {
        return notifDao.findUnreadByUserId(userId);
    }

    @Override
    public boolean markAsRead(int notificationId) throws RemoteException {
        Notification n = notifDao.findById(notificationId);
        if (n != null) { n.setRead(true); notifDao.update(n); return true; }
        return false;
    }

    // ===== OTP =====
    @Override
    public String generateOTP(String email) throws RemoteException {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(email, otp);
        System.out.println("[OTP] Generated for " + email + ": " + otp);
        return otp;
    }

    @Override
    public boolean verifyOTP(String email, String otp) throws RemoteException {
        String stored = otpStore.get(email);
        if (stored != null && stored.equals(otp)) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }
}
