/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriott.dao;

import com.marriott.model.Client;
import java.sql.*;

public class ClientDao {

    private String jdbcUrl = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String dbUsername = "postgres";
    private String dbPassword = "27004";

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
    }

    // CREATE
    public int registerClient(Client c) {
        try {
            if (!c.getPhoneNumber().matches("\\d{10}")) {
                System.out.println("Phone must be 10 digits!");
                return 0;
            }

            Connection con = getConnection();
            String sql = "INSERT INTO client (name, phone_number, email, age) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getPhoneNumber());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getAge());

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // UPDATE
    public int updateClient(Client c) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE client SET name=?, phone_number=?, email=?, age=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getPhoneNumber());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getAge());
            ps.setInt(5, c.getId());

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // DELETE
    public int deleteClient(int id) {
        try {
            Connection con = getConnection();
            String sql = "DELETE FROM client WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // DISPLAY
    public void displayClients() {
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM client";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Phone: " + rs.getString("phone_number"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("----------------------");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FIND BY ID
    public void findClientById(int id) {
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM client WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Phone: " + rs.getString("phone_number"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Age: " + rs.getInt("age"));
            } else {
                System.out.println("Client not found!");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}