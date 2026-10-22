package com.marriott.dao;

import com.marriott.model.Booking;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingDao {

    private String jdbcUrl = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String dbUsername = "postgres";
    private String dbPassword = "27004";

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
    }

    public int bookRoom(Booking b) {
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO booking (client_id, room_id, booking_date) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, b.getClientId());
            ps.setInt(2, b.getRoomId());

            // Safe date parsing (fix for Date.valueOf error)
            String dateStr = b.getBookingDate().trim();
            LocalDate date = LocalDate.parse(dateStr);
            ps.setDate(3, java.sql.Date.valueOf(date));

            int rows = ps.executeUpdate();
            con.close();
            return rows;

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use YYYY-MM-DD");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}