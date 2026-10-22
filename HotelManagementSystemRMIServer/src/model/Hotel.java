/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author USER
 */
@Entity
    public class Hotel implements Serializable{
        public static final long serialversionUid = 1L;
  @Id
    private int id;
    private String name;
    private String location;
    @Column(name = "reception_phone", nullable = false)
    private String receptionPhone;
    private String email;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    public Hotel(int id, String name, String location, String receptionPhone, String email, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.receptionPhone = receptionPhone;
        this.email = email;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReceptionPhone() {
        return receptionPhone;
    }

    public void setReceptionPhone(String receptionPhone) {
        this.receptionPhone = receptionPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    
}
