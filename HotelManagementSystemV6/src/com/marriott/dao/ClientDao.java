/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marriott.dao;

import com.marriott.model.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jeremie
 */
public class ClientDao {
    // JDBC Properties
    private String jdbcUrl = "jdbc:postgresql://localhost:5432/java_thursday_2026";
    private String dbUsername = "postgres";
    private String dbPasswd = "postgres";
    
    // CRUD Operation
    // CREATE --> INSERT
    public Integer registerClient(Client clientObj){
        // step 0: surround with try and catch
        try{
            // step1: create connection
            Connection con = DriverManager.getConnection(jdbcUrl , dbUsername , dbPasswd);
            // step 2: prepare the statement
            String sql = "INSERT INTO client (id , name , phone_number, email , age) VALUES (? , ? , ? , ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, clientObj.getNationalId());
            pst.setString(2 , clientObj.getNames());
            pst.setString(3, clientObj.getPhoneNumber());
            pst.setString(4, clientObj.getEmail());
            pst.setInt(5, clientObj.getAge());
            // step3: execute the statement
            int rowsAffected = pst.executeUpdate();
            // step4: close connection
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
            Connection con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPasswd);
            // prepare statements
            String sql = "SELECT * FROM client";
            PreparedStatement pst = con.prepareStatement(sql);
            
            // execution
            ResultSet rs = pst.executeQuery();
            List<Client> clientList = new ArrayList<>();
            while(rs.next()){
                Client clientObj = new Client();
                clientObj.setAge(rs.getInt("age"));
                clientObj.setNames(rs.getString("name"));
                clientObj.setNationalId(rs.getString("id"));
                clientObj.setPhoneNumber(rs.getString("phone_number"));
                clientObj.setEmail(rs.getString("email"));
                
                clientList.add(clientObj);
            }
            
            // close connection
            con.close();
            return clientList;
            
        }catch(Exception ex){
            ex.printStackTrace();
//            return null;
//            return new ArrayList<>();
            return Collections.EMPTY_LIST;
        }
    }
}
