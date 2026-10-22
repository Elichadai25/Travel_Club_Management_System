/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author USER
 */
@Entity
    public class Booking implements Serializable{
        public static final long serialversionUid = 1L;
    @Id
     private int id;
    @Column(name = "start_date", nullable = false)
     private LocalDate startdate;
    @Column(name = "end_date", nullable = false)
     private Localdate endDate;
     private String description;
     @Column(name = "total_adults", nullable = false)
     private int totalAdults;
     @Column(name = "total_children", nullable = false)
     private int totalChildren;
     
     @OneToOne(mappedBy = "booking")
     private paymentn payment;
     @ManyToOne
     @JoinColumn(name = "client_id")
     private Client client;
     @ManyToMany
     @JoinTable(
     name = "room_booked",
             joinColumns = @JoinColumn(name = "booking_id"),
             inverseJoinColumns = @JoinColumn(name = "room_id"))
     private Set<Room> rooms;
// no-arg constructor

    public Booking() {
    }

    public Booking(int id) {
        this.id = id;
    }

    public Booking(int id, LocalDate startdate, Localdate endDate, String description, int totalAdults, int totalChildren) {
        this.id = id;
        this.startdate = startdate;
        this.endDate = endDate;
        this.description = description;
        this.totalAdults = totalAdults;
        this.totalChildren = totalChildren;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public Localdate getEndDate() {
        return endDate;
    }

    public void setEndDate(Localdate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }  

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAdults() {
        return totalAdults;
    }

    public void setTotalAdults(int totalAdults) {
        this.totalAdults = totalAdults;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }
    
     
   
}
