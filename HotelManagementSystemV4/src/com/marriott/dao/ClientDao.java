package com.marriott.dao;

import com.marriott.model.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientDao {

    private String jdbcUrl = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String dbUsername = "postgres";
    private String dbPassword = "27004";   // FIXED

    // CREATE
    public int registerClient(Client clientObj){
        try{
            Connection con = DriverManager
                    .getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO client (id, name, phone_number, email, age) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, clientObj.getNationalId());
            pst.setString(2, clientObj.getNames());
            pst.setString(3, clientObj.getPhoneNumber());
            pst.setString(4, clientObj.getEmail());
            pst.setInt(5, clientObj.getAge());

            int rowsAffected = pst.executeUpdate();
            con.close();

            return rowsAffected;

        }catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    // READ
    public List<Client> findAllClients(){
        try{
            Connection con = DriverManager
                    .getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "SELECT * FROM client";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            List<Client> clientList = new ArrayList<Client>();

            while(rs.next()){
                Client clientObj = new Client();

                clientObj.setNationalId(rs.getString("id"));
                clientObj.setNames(rs.getString("name"));
                clientObj.setPhoneNumber(rs.getString("phone_number"));  // FIXED
                clientObj.setEmail(rs.getString("email"));
                clientObj.setAge(rs.getInt("age"));

                clientList.add(clientObj);
            }

            con.close();
            return clientList;

        }catch(Exception ex){
            ex.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }
}