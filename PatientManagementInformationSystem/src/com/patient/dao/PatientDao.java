/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patient.dao;
import com.patient.model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author USER
 */
public class PatientDao {
    // JDBC Properties
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/pmis_db";
    private final String dbUsername = "postgres";
    private final String dbPasswd = "27004";
    
    // CRUD Operation
    // CREATE --> INSERT
     public int insertPatient(Patient p) {
        int result = 0;

        try {
            try (Connection con = getConnection()) {
                String sql = "INSERT INTO patient "
        + "(patient_id, full_name, age,gender, diagnosis, consultation_fee, discount, invoice, registration_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

         PreparedStatement ps = con.prepareStatement(sql);

         ps.setString(1, p.getPatientID());
         ps.setString(2, p.getFullName());
         ps.setInt(3, p.getAge());
         ps.setString(4, p.getGender());
         ps.setString(5, p.getDiagnosis());
         ps.setDouble(6, p.getConsultationFee());
         ps.setDouble(7, p.getDiscount());
         ps.setDouble(8, p.getInvoice()); 
         ps.setString(9, p.getRegistrationDate());
                result = ps.executeUpdate();
            }

        } catch (SQLException e) {
        }

        return result;
    }

    // READ 
    public List<Patient> findAllPatients() {
        List<Patient> patientList = new ArrayList<>();

        try {
            try (Connection con = getConnection()) {
                String sql = "SELECT * FROM patient";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    Patient p = new Patient();
                    
                    p.setPatientID(rs.getString("patient_id"));
                    p.setFullName(rs.getString("full_name"));
                    p.setAge(rs.getInt("age"));
                    p.getGender(rs.getString("gender"));
                    p.setDiagnosis(rs.getString("diagnosis"));
                    p.setConsultationFee(rs.getDouble("consultation_fee"));
                    p.setDiscount(rs.getDouble("discount"));
                    p.setInvoice(rs.getDouble("invoice"));
                    p.setRegistrationDate(rs.getString("registration_date"));
                    
                    patientList.add(p);
                }
            }

        } catch (SQLException e) {
        }

        return patientList;
    }

   public Connection getConnection() {
    Connection con = null;
    try {
        con = java.sql.DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/pmis_db",
            "postgres",
            "27004"
        );
        System.out.println("Connected successfully!");
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
    return con;
}
 
    
}