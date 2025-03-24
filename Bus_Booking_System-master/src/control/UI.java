/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.text.ParseException;
import linkedlist.*;
import object.*;

/**
 *
 * @author admin
 */
public class UI {

    Manager manage = new Manager();

    // Link list
    BookingList bookingList = new BookingList();
    BusBST busTree = new BusBST();
    PassengerBST passTree = new PassengerBST();

    public UI() throws ParseException {
        bookingList.loadBookingFromFile();
        busTree.loadBusesFromFile();
        passTree.loadPassengersFromFile();
        if (busTree.isEmpty()) {
            busTree.generateTestData();
        }

        if (passTree.isEmpty()) {
            passTree.generateTestData();
        }
    }

    private void showMenu() {
        System.out.println("\n||=============================||");
        System.out.println("||      BUS BOOKING SYSTEM     ||");
        System.out.println("||=============================||");
        System.out.println("|| 1. Manage Buses             ||");
        System.out.println("|| 2. Manage Passengers        ||");
        System.out.println("|| 3. Manage Bookings          ||");
        System.out.println("|| 4. Exit                     ||");
        System.out.println("||=============================||\n");
    }

    private int getChoiceMenu() {
        return manage.inputInt("-> Enter your choice: ", "(Please input number betwwen 1 and 4)", 1, 4);
    }

    private int getChoiceBusMenu() {
        return manage.inputInt("-> Enter your choice: ", "Please input number betwwen 1 and 11", 1, 11);
    }

    private int getChoicePassengersMenu() {
        return manage.inputInt("-> Enter your choice: ", "Please input number betwwen 1 and 7", 1, 7);
    }

    private int getChoiceBookingMenu() {
        return manage.inputInt("-> Enter your choice: ", "Please input number betwwen 1 and 5", 1, 5);
    }

    private void showBusMenu() {

        System.out.println("\n||===============================||");
        System.out.println("||        BUS MANAGEMENT         ||");
        System.out.println("||===============================||");
        System.out.println("|| 1. Add new bus                ||");
        System.out.println("|| 2. Display all buses          ||");
        System.out.println("|| 3. Search bus by code         ||");
        System.out.println("|| 4. Delete bus by copying      ||");
        System.out.println("|| 5. Delete bus by merging      ||");
        System.out.println("|| 6. Balance bus tree           ||");
        System.out.println("|| 7. Breadth first travel       ||");
        System.out.println("|| 8. Count number of bus        ||");
        System.out.println("|| 9. Search bus by name         ||");
        System.out.println("|| 10. Search booked by bus code ||");
        System.out.println("|| 11. Back to main menu         ||");
        System.out.println("||===============================||\n");
    }

    private void showPassengerMenu() {
        System.out.println("\n||===================================||");
        System.out.println("||        PASSENGER MANAGEMENT       ||");
        System.out.println("||===================================||");
        System.out.println("|| 1. Add new passenger              ||");
        System.out.println("|| 2. Display all passengers         ||");
        System.out.println("|| 3. Search passenger by code       ||");
        System.out.println("|| 4. Delete passenger by copying    ||");
        System.out.println("|| 5. Search passenger by name       ||");
        System.out.println("|| 6. Search buses by passenger code ||");
        System.out.println("|| 7. Back to main menu              ||");
        System.out.println("||===================================||\n");
    }

    private void showBookingMenu() {
        System.out.println("\n||=================================================||");
        System.out.println("||               BOOKING MANAGEMENT                ||");
        System.out.println("||=================================================||");
        System.out.println("|| 1. Book a bus                                   ||");
        System.out.println("|| 2. Display all bookings                         ||");
        System.out.println("|| 3. Sort bookings by bus code and passenger code ||");
        System.out.println("|| 4. Pay for booking                              ||");
        System.out.println("|| 5. Back to main menu                            ||");
        System.out.println("||=================================================||\n");
    }

    private void processBusMenu() throws Exception {
        int busChoice;
        do {
            showBusMenu();
            busChoice = getChoiceBusMenu();
            switch (busChoice) {
                case 1:
                    Bus busAddLast = manage.inputBus(busTree);
                    busTree.insert(busAddLast);
                    busTree.saveBusesToFile();
                    break;
                case 2:
                    busTree.postOrder();
                    break;
                case 3:
                    String bcodeSearch = manage.inputString("Please enter bcode to search: ");
                    BusNode node = busTree.searchByCode(bcodeSearch);
                    if (node == null) {
                        System.out.println("Not found");
                        break;
                    }
                    busTree.visit(node);
                    break;
                case 4:
                    String bcodeDeleteCopying = manage.inputString("Please enter bcode to delete: ");
                    busTree.deleteByCodeCopying(bcodeDeleteCopying, bookingList);
                    busTree.saveBusesToFile();
                    break;
                case 5:
                    String bcodeDeleteMerging = manage.inputString("Please enter bcode to delete: ");
                    busTree.deleteByCodeMerging(bcodeDeleteMerging, bookingList);
                    busTree.saveBusesToFile();
                    break;
                case 6:
                    busTree.simplyBalancing();
                    break;
                case 7:
                    busTree.breadthFirstTraversal();
                    break;
                case 8:
                    busTree.countBuses();
                    break;
                case 9:
                    String bNameToSearch = manage.inputString("Please enter bus name to search: ");
                    busTree.searchByName(bNameToSearch);
                    break;
                case 10:
                    String bcodeToSearch = manage.inputString("Please enter bus code to search bookings: ");
                    busTree.searchBookedByBcode(bcodeToSearch, bookingList, passTree);
                    break;
            }
        } while (busChoice != 11);
    }

    private void processPassMenu() {
        int passengersChoice;
        do {
            showPassengerMenu();
            passengersChoice = getChoicePassengersMenu();
            switch (passengersChoice) {
                case 1:
                    Passenger inputPassenger = manage.inputPassenger(passTree);
                    passTree.insert(inputPassenger);
                    passTree.savePassengersToFile();
                    break;
                case 2:
                    passTree.inOrder();
                    passTree.savePassengersToFile();
                    break;
                case 3:
                    String pcodeToSearch = manage.inputString("Please enter passenger code to search: ");
                    passTree.searchByPcode(pcodeToSearch);
                    break;
                case 4:
                    String pcodeToDelete = manage.inputString("Please enter passenger code to delete: ");
                    passTree.deleteByCodeCopying(pcodeToDelete, bookingList);
                    passTree.savePassengersToFile();
                    break;
                case 5:
                    String pnameToSearch = manage.inputString("Please enter passenger name to search: ");
                    passTree.searchByName(pnameToSearch);
                    break;
                case 6:
                    String pcodeForBusSearch = manage.inputString("Please enter passenger code to search buses: ");
                    bookingList.searchBusesByPcode(pcodeForBusSearch, passTree, busTree);
                    break;
            }
        } while (passengersChoice != 7);
    }

    private void processBookingMenu() {
        int bookingChoice;
        do {
            showBookingMenu();
            bookingChoice = getChoiceBookingMenu();
            switch (bookingChoice) {
                case 1:
                    Booking inputBooking = manage.inputBooking(bookingList, busTree, passTree);
                    bookingList.bookBus(inputBooking, busTree, passTree);
                    bookingList.saveBookingToFile();
                    busTree.saveBusesToFile();
                    break;
                case 2:
                    bookingList.traverse();
                    break;
                case 3:
                    bookingList.sortByBcodeAndPcode();
                    bookingList.saveBookingToFile();
                    break;
                case 4:
                    String bcodeForBooking = manage.inputString("Please enter bus code for booking: ");
                    String pcodeForBooking = manage.inputString("Please enter passenger code for booking: ");
                    bookingList.payBooking(bcodeForBooking, pcodeForBooking);
                    bookingList.saveBookingToFile();
                    break;
            }
        } while (bookingChoice != 5);
    }

    public void processMainMenu() throws Exception {
        while (true) {
            int menuChoice;
            showMenu();
            menuChoice = getChoiceMenu();
            switch (menuChoice) {
                case 1:
                    processBusMenu();
                    break;
                case 2:
                    processPassMenu();
                    break;
                case 3:
                    processBookingMenu();
                    break;
                case 4:
                    busTree.saveBusesToFile();
                    passTree.savePassengersToFile();
                    bookingList.saveBookingToFile();
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }

}
