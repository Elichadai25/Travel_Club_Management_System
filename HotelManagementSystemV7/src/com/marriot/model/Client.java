/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author USER
 */
@Entity
public class Client {
      @Id
    @Column(name="National_id")
    private String nationalId;
    private String names;
    @Column(name="Phone_number")        
    private String phoneNumber;
    private String email;
    private Integer age;
    
    // default constructor

    public Client() {
    }

    // constructor with args
    public Client(String nationalId, String names, String phoneNumber, String email, Integer age) {
        this.nationalId = nationalId;
        this.names = names;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
    }
    
    // getter and setters

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    
}


    

    
