/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.marriot.model.Client;

public class ClientDao {

    private String url = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String user = "postgres";
    private String password = "1234";

    // -------------------- INSERT --------------------
    public int insertClient(Client clientObj) {
        int row = 0;
        try {
            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO client(national_id, names, phone_number, email, age) VALUES(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, Integer.parseInt(clientObj.getNationalId()));
            pst.setString(2, clientObj.getNames());
            pst.setString(3, clientObj.getPhoneNumber());
            pst.setString(4, clientObj.getEmail());
            pst.setInt(5, clientObj.getAge());

            row = pst.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    // -------------------- UPDATE --------------------
   public int updateClient(Client clientObj) {
    int row = 0;
    try {
        Connection con = DriverManager.getConnection(url, user, password);

        String sql = "UPDATE client SET names=?, phone_number=?, email=?, age=? WHERE national_id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, clientObj.getNames());
        pst.setString(2, clientObj.getPhoneNumber());
        pst.setString(3, clientObj.getEmail());
        pst.setInt(4, clientObj.getAge());

        // ✅ THIS MUST BE STRING
        pst.setString(5, clientObj.getNationalId());

        row = pst.executeUpdate();
        con.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
    return row;
}

    // -------------------- DELETE (OBJECT) --------------------
    public int deleteClient(Client clientObj) {
        int row = 0;
        try {
            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "DELETE FROM client WHERE national_id=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, Integer.parseInt(clientObj.getNationalId()));

            row = pst.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    // -------------------- REGISTER (USED BY GUI) --------------------
    public int registerClient(Client client) {
        return insertClient(client); // reuse insert method
    }

    // -------------------- DELETE BY ID (USED BY GUI) --------------------
    public int deleteClient(String id) {
        int row = 0;
        try {
            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "DELETE FROM client WHERE national_id=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, id);

            row = pst.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
}