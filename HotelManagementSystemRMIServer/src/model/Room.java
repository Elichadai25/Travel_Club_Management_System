/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author USER
 */
@Entity
    public class Room implements Serializable{
        public static final long SerialVersionUid = 1L;
    @Id
    private int id;
    @Column(name = "room_number", nullable = false)
    private String roomNumber;
    @Column(name = "room_type", nullable = false)
    private ERoomType roomType;
    private Double price;
    private ERoomStatus status;
    
    @ManyToOne
    @JoinColumn(name = "hotal_id")
    private Hotel hotel;
    
    @ManyToMany(mappedBy = "rooms")
    private Set<Booking> booking;

    public Room() {
    }

    public Room(int id, String roomNumber, ERoomType roomType, Double price, ERoomStatus status) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public ERoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(ERoomType roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ERoomStatus getStatus() {
        return status;
    }

    public void setStatus(ERoomStatus status) {
        this.status = status;
    }
    
    
}
