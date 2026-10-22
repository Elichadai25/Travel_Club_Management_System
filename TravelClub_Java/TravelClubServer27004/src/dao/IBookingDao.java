package dao;

import model.Booking;
import java.util.List;

public interface IBookingDao extends IGenericDao<Booking> {
    List<Booking> findByUserId(int userId);
    List<Booking> findByTripId(int tripId);
    List<Booking> findByStatus(String status);
}
