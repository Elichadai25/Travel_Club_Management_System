package model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    public static final long serialVersionUID = 1L;

    private int paymentId;
    private double amount;
    private Date paymentDate;
    private String status = "PENDING";
    private EPaymentMode paymentMode;
    private String transactionRef;
    private Booking booking;

    public Payment() {}

    public Payment(double amount, Date paymentDate, EPaymentMode paymentMode, String transactionRef, Booking booking) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        this.transactionRef = transactionRef;
        this.booking = booking;
    }

    public Payment(double amount, Date date, EPaymentMode ePaymentMode, Booking booking) {
        this.amount = amount;
        this.paymentDate = date;
        this.paymentMode = ePaymentMode;
        this.booking = booking;
        this.transactionRef = "TXN-" + System.currentTimeMillis();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public EPaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(EPaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}