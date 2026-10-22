/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patient.dao;
import com.patient.model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class InvoiceDao {
    



    private final String url = "jdbc:postgresql://localhost:5432/PMIS_db";
    private final String user = "postgres";
    private final String pass = "27004";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // INSERT
    public int insertInvoice(Invoice i) {
        int result = 0;

        try {
            Connection con = getConnection();

            String sql = "INSERT INTO invoice (patient_id, total_amount, payment_status, invoice_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, i.getPatientId());
            ps.setDouble(2, i.getTotalAmount());
            ps.setString(3, i.getPaymentStatus());
            ps.setString(4, i.getInvoiceDate());

            result = ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // READ
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();

        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM invoice";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice i = new Invoice();

                i.setInvoiceId(rs.getInt("invoice_id"));
                i.setPatientId(rs.getString("patient_id"));
                i.setTotalAmount(rs.getDouble("total_amount"));
                i.setPaymentStatus(rs.getString("payment_status"));
                i.setInvoiceDate(rs.getString("invoice_date"));

                list.add(i);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
