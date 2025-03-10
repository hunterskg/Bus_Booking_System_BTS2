/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package linkedlist;

import java.io.*;
import java.util.Random;
import object.Passenger;

/**
 *
 * @author FPT SHOP
 */
public class PassengerBST {

    private Node head;
    private Node tail;

    private final String filePath = "Passengers.txt";

    // Node class for the linked list
    public class Node {

        public Passenger info;
        public Node next;

        public Node(Passenger passenger) {
            this.info = passenger;
            this.next = null;
        }
    }

    public Node searchByPcode(String pcode) {
        Node p = head;
        while (p != null) {
            if (p.info.getPcode().equalsIgnoreCase(pcode)) {
                return p;
            }
            p = p.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    //2.1 Load data from file
    public void loadPassengersFromFile() {

        File file = new File(filePath); // Create a File object for the path

        if (!file.exists()) { // Check if the file exists
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            String readedFile;
            while ((readedFile = bReader.readLine()) != null) {

                String[] readedFileParts = readedFile.split(";");

                if (readedFileParts.length == 3) {
                    String pcode = readedFileParts[0].split(":")[1].trim();
                    String name = readedFileParts[1].split(":")[1].trim();
                    String phone = readedFileParts[2].split(":")[1].trim();
                    addLast(new Passenger(pcode, name, phone));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in file: " + e.getMessage());
        }
    }

    //2.2 Input & add to the end    
    public void addLast(Passenger passenger) {
        Node newNode = new Node(passenger);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    //2.3 Display data
    public void traverse() {
        Node q = head;
        while (q != null) {
            System.out.println(q.info);
            q = q.next;
        }
    }

    //2.4 Save passengers list to file
    public void savePassengersToFile() {
        try (BufferedWriter bwriter = new BufferedWriter(new FileWriter(filePath))) {
            Node temp = head;
            while (temp != null) {
                bwriter.write(temp.info.toString());  // Write the booking data
                bwriter.newLine();  // Move to the next line
                temp = temp.next;  // Move to the next node
            }
        } catch (Exception e) {
        }
    }

    //2.5 Search by pcode
    public void searchByPcodeResult(String pcode) {
        Node p = searchByPcode(pcode);
        if (searchByPcode(pcode) != null) {
            System.out.println(p.info);
        } else {
            System.out.println("Passenger not found");
        }
    }

    //2.6 Delete by pcode
    public void deleteByPcode(String pcode, BookingBST bookingList) {

        if (head == null) {
            System.out.println("Passenger list is empty.");
            return;
        }

        // Check if passenger have booked
        if (bookingList.isPassengerBooked(pcode)) {
            System.err.println("Error: Cannot delete passenger " + pcode + " because they have booked.");
            return;
        }

        Node prev = null;
        Node current = head;

        while (current != null && !current.info.getPcode().equalsIgnoreCase(pcode)) {
            prev = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("Passenger with code " + pcode + " not found.");
            return;
        }

        if (prev == null) {
            head = current.next; // Delete head
        } else {
            prev.next = current.next; // Delete middle or tail node
        }

        if (current == tail) {
            tail = prev; // Update tail if last node was deleted
        }

        System.out.println("Passenger with code " + pcode + " have been deleted.");
    }

    //2.7 Search by name
    public void searchByName(String name) {
        Node temp = head;
        boolean found = false;
        while (temp != null) {
            if (temp.info.getName().equalsIgnoreCase(name)) {
                System.out.println(temp.info);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) {
            System.out.println("Not found");
        }
    }

    //2.8 Search buses by pcode
    public void searchBusesByPcode(String pcode, BookingBST bookingList, BusBST busList) {
        Node passengerNode = searchByPcode(pcode);
        if (passengerNode == null) {
            System.err.println("Passenger with code " + pcode + " not found.");
            return;
        }
        System.out.println("\n===== Passenger Details =====");
        System.out.println(passengerNode.info);

        System.out.println("\n===== Buses Booked by This Passenger =====");
        boolean foundBooking = false;
        BookingBST.Node bookingNode = bookingList.getHead();
        while (bookingNode != null) {
            if (bookingNode.info.getPcode().equalsIgnoreCase(pcode)) {

                linkedlist.BusNode busNode = busList.searchByCode(bookingNode.info.getBcode());
                if (busNode != null) {
                    System.out.println(busNode.info);
                    foundBooking = true;
                }
            }
            bookingNode = bookingNode.next;
        }

        if (!foundBooking) {
            System.out.println("No buses have been booked by this passenger.");
        }
    }

    public boolean searchByPhone(String phone) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getPhone().equals(phone)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    //Test data
    public void generateTestData() {
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            String pcode = "P" + (random.nextInt(900)+100);
            String name = "Passenger_" + i;
            String phone = "09" + (random.nextInt(90000000) + 10000000);

            Passenger passenger = new Passenger(pcode, name, phone);
            addLast(passenger);
        }
        System.out.println("Generated 5 random passengers.");
    }
}
