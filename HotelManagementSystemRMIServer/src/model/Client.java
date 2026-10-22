/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author USER
 */
@Entity
    public class Client implements Serializable{
        public static final long serialversionUid = 1L;
    @Id
     private int id;
    @Column(name = "first_name", nullable = false)
     private String firstName;
    @Column(name = "last_name",nullable = false)
     private String lastname;
    @Column(name = "national_id", unique=true, nullable = false)
     private String nationalId;
     private int age;
     @Column(name = "phone_number",nullable = false)
     private String PhoneNumber;
     @Column(name = "email",unique=true,nullable = false)
     private String email;
     private String address;
     
     @OneToMany(mappedBy = "client")
     private List<Booking> booking;
     

    public Client(int id, String firstName, String lastname, String nationalId, int age, String PhoneNumber, String email, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.nationalId = nationalId;
        this.age = age;
        this.PhoneNumber = PhoneNumber;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
      
     
    
}
