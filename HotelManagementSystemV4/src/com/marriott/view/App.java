package com.marriott.view;

import com.marriott.dao.ClientDao;
import com.marriott.model.Client;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jeremie
 */
public class App {
    public static void main(String[] args) {
        // variable declaration
        boolean condition = true;
        String nationalId;
        String email;
        String names;
        String phoneNumber;
        int age;
        while(condition){
            System.out.println("=====================");
            System.out.println(" HOTEL MGT SYSTEM");
            System.out.println("=====================");
            System.out.println("1. Register client");
            System.out.println("2. Update client");
            System.out.println("3. Delete client");
            System.out.println("4. Display all Records");
            System.out.println("5. Find client by ID");
            System.out.println("0. Exit");
            System.out.println("------------");
            System.out.print("Choose: ");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    System.out.println("REGISTER CLIENT");
                    System.out.println("---------------");
                    System.out.print("Enter NID: ");
                    nationalId = input.next();
                    System.out.print("Enter Names: ");
                    names = input.next();
                    System.out.print("Enter Phone Number: ");
                    phoneNumber = input.next();
                    System.out.print("Enter Email: ");
                    email = input.next();
                    System.out.print("Enter Age: ");
                    age = input.nextInt();
                    // sending data to model
                    Client clientObj = new Client();
                    clientObj.setAge(age);
                    clientObj.setEmail(email);
                    clientObj.setNationalId(nationalId);
                    clientObj.setPhoneNumber(phoneNumber);
                    clientObj.setNames(names);
                    
                    // sending data in model to dao
                    ClientDao dao = new ClientDao();
                    int rowsAffected = dao.registerClient(clientObj);
                    if(rowsAffected > 0){
                        System.out.println("Data Saved successful");
                    }else{
                        System.out.println("Data not Saved!");
                    }
                    break;
                case 2:
                    break;
                case 4:
                    System.out.println("ALL RECORDS LIST");
                    System.out.println("------------------");
                    dao = new ClientDao();
                    List<Client> clientList = dao.findAllClients();
                    if(clientList.isEmpty()){
                        System.out.println("No Records Found");
                        break;
                    }
                    int counter = 0;
                    for(Client theClient : clientList){
                        counter ++;
                        System.out.println("Client "+counter);
                        System.out.println("--------");
                        System.out.println("NATIONAIL ID: "+theClient.getNationalId());
                        System.out.println("NAMES: "+theClient.getNames());
                        System.out.println("AGE: "+theClient.getAge());
                        System.out.println("PHONE NUMBER: "+theClient.getPhoneNumber());
                        System.out.println("EMAIL: "+theClient.getEmail());
                    }
                    break;
                case 0:
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong choice!!!");
            }
        }
    }
}

    

