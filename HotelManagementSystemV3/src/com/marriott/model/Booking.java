/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriott.model;

/**
 *
 * @author USER
 */

public class Booking {

    private int id;
    private int clientId;
    private int roomId;
    private String bookingDate;

    public Booking() {}

    public Booking(int clientId, int roomId, String bookingDate) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.bookingDate = bookingDate;
    }

    public int getClientId() { return clientId; }
    public int getRoomId() { return roomId; }
    public String getBookingDate() { return bookingDate; }
}
    
