package dao;

import model.Payment;
import java.util.List;

public interface IPaymentDao extends IGenericDao<Payment> {
    Payment findByBookingId(int bookingId);
    List<Payment> findByStatus(String status);
    double getTotalRevenue();
}
