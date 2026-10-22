package com.marriott.view;

import com.marriott.dao.*;
import com.marriott.model.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===== HOTEL SYSTEM =====");
            System.out.println("1. Register Client");
            System.out.println("2. Update Client");
            System.out.println("3. Delete Client");
            System.out.println("4. Display Clients");
            System.out.println("5. Find Client");
            System.out.println("6. Add Room");
            System.out.println("7. Display Rooms");
            System.out.println("8. Book Room");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    input.nextLine();
                    System.out.print("Name: ");
                    String name = input.nextLine();

                    System.out.print("Phone (10 digits): ");
                    String phone = input.nextLine();
                    if (!phone.matches("\\d{10}")) {
                        System.out.println("Invalid phone!");
                        break;
                    }

                    System.out.print("Email: ");
                    String email = input.nextLine();

                    System.out.print("Age: ");
                    int age = input.nextInt();

                    Client c = new Client(name, phone, email, age);
                    new ClientDao().registerClient(c);
                    System.out.println("Saved!");
                    break;

                case 2:
                    System.out.print("ID: ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Name: ");
                    String uname = input.nextLine();

                    System.out.print("Phone: ");
                    String uphone = input.nextLine();
                    if (!uphone.matches("\\d{10}")) {
                        System.out.println("Invalid phone!");
                        break;
                    }

                    System.out.print("Email: ");
                    String uemail = input.nextLine();

                    System.out.print("Age: ");
                    int uage = input.nextInt();

                    Client uc = new Client(id, uname, uphone, uemail, uage);
                    new ClientDao().updateClient(uc);
                    System.out.println("Updated!");
                    break;

                case 3:
                    System.out.print("ID: ");
                    int did = input.nextInt();
                    new ClientDao().deleteClient(did);
                    System.out.println("Deleted!");
                    break;

                case 4:
                    new ClientDao().displayClients();
                    break;

                case 5:
                    System.out.print("ID: ");
                    int fid = input.nextInt();
                    new ClientDao().findClientById(fid);
                    break;

                case 6:
                    input.nextLine();
                    System.out.print("Room Number: ");
                    String rn = input.nextLine();

                    System.out.print("Type: ");
                    String type = input.nextLine();

                    System.out.print("Price: ");
                    String priceInput = input.next();
                    double price = Double.parseDouble(priceInput);

                    Room r = new Room(rn, type, price);
                    new RoomDao().addRoom(r);
                    System.out.println("Room Added!");
                    break;

                case 7:
                    new RoomDao().displayRooms();
                    break;

                case 8:
                    System.out.print("Client ID: ");
                    int cid = input.nextInt();

                    System.out.print("Room ID: ");
                    int rid = input.nextInt();

                    System.out.print("Booking Date (YYYY-MM-DD): ");
                    String date = input.next();

                    Booking b = new Booking(cid, rid, date);
                    int saved = new BookingDao().bookRoom(b);

                    System.out.println(saved > 0 ? "Booked!" : "Not Booked!");
                    break;

                case 0:
                    running = false;
                    System.out.println("Bye!");
                    break;

                default:
                    System.out.println("Wrong choice!");
            }
        }
    }
}