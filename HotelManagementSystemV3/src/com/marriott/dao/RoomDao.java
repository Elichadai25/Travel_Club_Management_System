/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriott.dao;

import com.marriott.model.Room;
import java.sql.*;

public class RoomDao {

    private String jdbcUrl = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String dbUsername = "postgres";
    private String dbPassword = "27004";

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
    }

    // CREATE
    public int addRoom(Room r) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO room (room_number, type, price) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, r.getRoomNumber());
            ps.setString(2, r.getType());
            ps.setDouble(3, r.getPrice());

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // UPDATE
    public int updateRoom(Room r) {
        try {
            Connection con = getConnection();
            String sql = "UPDATE room SET room_number=?, type=?, price=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, r.getRoomNumber());
            ps.setString(2, r.getType());
            ps.setDouble(3, r.getPrice());
            ps.setInt(4, r.getId());

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // DELETE
    public int deleteRoom(int id) {
        try {
            Connection con = getConnection();
            String sql = "DELETE FROM room WHERE id=?";
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
    public void displayRooms() {
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM room";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Room: " + rs.getString("room_number"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("----------------------");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
