/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 *
 * @author USER
 */
@Entity
    public class Payment implements Serializable{
        public static final long serialversionUid = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private String id;
    @Column(name = "payment_date", nullable = false)
     private LocalDate paymentDate;
     private boolean paid;
     @Column(name = "payment_mode", nullable = false)
     private EPaymentMode paymentMode;
     @Column(name = "total_amount", nullable = false)
      private Double totalAmount;
     
     @OneToOne
     @JoinColumn(name = "booking_id")
     private Booking booking;

    public Payment(String id, LocalDate paymentDate, boolean paid, EPaymentMode paymentMode, Double totalAmount) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.paid = paid;
        this.paymentMode = paymentMode;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public EPaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(EPaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
     
      
}
