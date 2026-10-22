/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patient.model;

/**
 *
 * @author USER
 */


public class Patient {

    private String patientID;
    private String fullName;
    private int age;
    private String gender;
    private String diagnosis;
    private double consultationFee;
    private double discount;
    private String registrationDate;

    // Getter & Setter

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
   

    public void setAge(int age) {
        this.age = age;
    }
     public int getAge() {
        return age;
    }
 public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
    this.gender = gender;
    }
   
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

   

   public void setRegistrationDate(String registrationDate) {
      this.registrationDate = registrationDate;
}

   public String getRegistrationDate() {
      return registrationDate;
}
    private double invoice;

    public double getInvoice() {
       return invoice;
    }

   public void setInvoice(double invoice) {
      this.invoice = invoice;
}

  
}