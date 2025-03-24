/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package linkedlist;

import java.io.*;
import java.text.*;
import java.util.Date;
import object.Booking;
import object.Bus;

/**
 *
 * @author FPT SHOP
 */
public class BookingList {

    Bus bus = new Bus();
    String filePath = "Booking.txt";

    private Node head;
    private Node tail;

    Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    // Node class for the linked list
    public class Node {

        public Booking info;
        public Node next;

        public Node(Booking booking) {
            this.info = booking;
            this.next = null;
        }
    }

    public boolean isAlreadyBooked(String bcode, String pcode) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getBcode().equalsIgnoreCase(bcode) && temp.info.getPcode().equalsIgnoreCase(pcode)) {
                return true;  // Booking already exists
            }
            temp = temp.next;
        }
        return false;
    }

    //3.1 Load data from file
    public void loadBookingFromFile() throws ParseException {

        File file = new File(filePath); // Create a File object for the path

        if (!file.exists()) { // Check if the file exists
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            String readedFile;
            while ((readedFile = bReader.readLine()) != null) {

                String[] parts = readedFile.split(";");
                if (parts.length == 5) {

                    String bcode = parts[0].split(":")[1].trim();
                    String pcode = parts[1].split(":")[1].trim();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date odate = dateFormat.parse(parts[2].split(":")[1].trim());
                    int paid = Integer.parseInt(parts[3].split(":")[1].trim());
                    int seat = Integer.parseInt(parts[4].split(":")[1].trim());
                    addLast(new Booking(bcode, pcode, odate, paid, seat));
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in file: " + e.getMessage());
        }
    }
    //3.2 add booking to the end of the booking list

    public void addLast(Booking booking) {
        Node newNode = new Node(booking);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    //3.3 display
    public void traverse() {
        if (isEmpty()) {
            System.out.println("The list is empty");
        }

        Node q = head;
        while (q != null) {
            System.out.println(q.info);
            q = q.next;
        }
    }

    //I think it's done Duy Khanh
    public void bookBus(Booking bookedBus, BusBST busList, PassengerBST passengerList) {
        if (bookedBus == null) {
            return;
        }
        BusNode foundBus = busList.searchByCode(bookedBus.getBcode());
        if (foundBus == null) {
            System.err.println("Error: Bus does not exist.");
            return;
        }

        PassengerBST.PassNode foundPassenger = passengerList.searchByPcode(bookedBus.getPcode());
        if (foundPassenger == null) {
            System.err.println("Error: Passenger not found.");
            return;
        }

        int availableSeats = foundBus.info.getSeat() - foundBus.info.getBooked();
        if (bookedBus.getSeat() > availableSeats) {
            System.err.println("Error: Not enough available seats.");
            return;
        }

        foundBus.info.setBooked(foundBus.info.getBooked() + bookedBus.getSeat());
        addLast(bookedBus);
        System.out.println("Booking successful.");
    }

    //3.3 Save booking list to file
    public void saveBookingToFile() {
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

    //3.5 Sortby bcode + pcode
    public boolean shouldSwap(Node a, Node b) {
        return a.info.getBcode().compareToIgnoreCase(b.info.getBcode()) > 0 || (a.info.getBcode().equalsIgnoreCase(b.info.getBcode()) && a.info.getPcode().compareToIgnoreCase(b.info.getPcode()) > 0);
    }

    private void swap(Node a, Node b) {
        Booking temp = a.info;
        a.info = b.info;
        b.info = temp;
    }

    public void sortByBcodeAndPcode() {

        for (Node i = head; i != null; i = i.next) {
            for (Node j = i.next; j != null; j = j.next) {
                if (shouldSwap(i, j)) {
                    swap(i, j);
                }
            }
        }
        traverse();

    }

    //3.6 Pay booking by bcode + pcode
    public void payBooking(String bcode, String pcode) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getBcode().equalsIgnoreCase(bcode) && temp.info.getPcode().equalsIgnoreCase(pcode)) {
                if (temp.info.getPaid() == 0) {
                    temp.info.setPaid(1);
                    System.out.println("Your seat have been paid");
                } else {
                    System.out.println("Your seat have alreay paid");
                }
            }
            if (!temp.info.getBcode().equalsIgnoreCase(bcode) || !temp.info.getPcode().equalsIgnoreCase(pcode)) {
                System.err.println("Bus or Passenger may not exist");
                break;
            }
            temp = temp.next;
        }

    }

    //1.12 Searchbookedby bcode (input bcode to be searched, then return  the busdata or not found; Then list all passengerswho bookedthe bus)
    //I think it's Done Duy Khanh
    public void searchBookedByBcode(String bcode, BusBST busList, PassengerBST passengerList) {
        BusNode bus = busList.searchByCode(bcode);

        if (bus == null) {
            System.err.println("Bus with code " + bcode + " not found.");
            return;
        }

        System.out.println("\n===== Bus Details =====");
        System.out.println(bus.info);

        System.out.println("\n===== Passengers Who Booked This Bus =====");
        boolean foundPassenger = false;
        Node temp = head;

        while (temp != null) {
            if (temp.info.getBcode().equalsIgnoreCase(bcode)) {
                PassengerBST.PassNode passengerNode = passengerList.searchByPcode(temp.info.getPcode());
                if (passengerNode != null) {
                    System.out.println(passengerNode.info);
                    foundPassenger = true;
                }
            }
            temp = temp.next;
        }

        if (!foundPassenger) {
            System.out.println("No passengers have booked this bus.");
        }
    }

    //2.8. Search busesbypcode
    //I think it's Done Duy Khanh
    public void searchBusesByPcode(String pcode, PassengerBST passengerList, BusBST busList) {
        PassengerBST.PassNode passengerNode = passengerList.searchByPcode(pcode);

        if (passengerNode == null) {
            System.err.println("Passenger with code " + pcode + " not found.");
            return;
        }

        System.out.println("\n===== Passenger Details =====");
        System.out.println(passengerNode.info);

        System.out.println("\n===== Buses Booked by This Passenger =====");
        boolean foundBooking = false;
        Node temp = head;

        while (temp != null) {
            if (temp.info.getPcode().equalsIgnoreCase(pcode)) {
                BusNode busNode = busList.searchByCode(temp.info.getBcode());
                if (busNode != null) {
                    System.out.println(busNode.info);
                    foundBooking = true;
                }
            }
            temp = temp.next;
        }

        if (!foundBooking) {
            System.out.println("No buses have been booked by this passenger.");
        }
    }

    // check if have been booked
    public boolean isBusBooked(String bcode) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getBcode().equalsIgnoreCase(bcode)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public boolean isPassengerBooked(String pcode) {
        Node temp = head;
        while (temp != null) {
            if (temp.info.getPcode().equalsIgnoreCase(pcode)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

}
