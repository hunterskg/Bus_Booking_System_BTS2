package menu;

import control.Manager;
import control.*;
import java.text.ParseException;
import linkedlist.*;
import object.*;

public class Main {

    public static void main(String[] args) throws ParseException {

        //Link list
        BookingList bookingList = new BookingList();
        BusBST busTree = new BusBST();
        PassengerBST passTree = new PassengerBST();

        //input and display handler
        Manager manage = new Manager();
        UI ui = new UI();

        //load file
        bookingList.loadBookingFromFile();
        busTree.loadBusesFromFile();
        passTree.loadPassengersFromFile();

        if (busTree.isEmpty() && passTree.isEmpty() && bookingList.isEmpty()) {
            busTree.generateTestData();
            passTree.generateTestData();
        }

        while (true) {
            int menuChoice;
            ui.showMenu();
            menuChoice = ui.getChoiceMenu();
            switch (menuChoice) {
                case 1:
                    int busChoice;
                    do {
                        ui.showBusMenu();
                        busChoice = ui.getChoiceBusMenu();
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
                                busTree.searchByCode(bcodeSearch);
                                break;
                            case 4:
                                String bcodeDeleteCopying = manage.inputString("Please enter bcode to delete: ");
                                busTree.deleteByCodeCopying(bcodeDeleteCopying, bookingList);
                                //busList.saveBusesToFile();
                                break;
                            case 5:
                                String bcodeDeleteMerging = manage.inputString("Please enter bcode to delete: ");
                                busTree.deleteByCodeMerging(bcodeDeleteMerging, bookingList);
                                //busList.saveBusesToFile();
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
//                                String bcodeToSearch = manage.inputString("Please enter bus code to search bookings: ");
//                                busList.searchBookedByBcode(bcodeToSearch, bookingList, passList);
//                                break;
                        }
                    } while (busChoice != 11); // Return to main menu when user selects 0
                    break; // Exit case 1 and go back to main menu

                case 2:
                    int passengersChoice;
                    do {
                        ui.showPassengerMenu();
                        passengersChoice = ui.getChoicePassengersMenu();
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
                                passTree.searachByName(pnameToSearch);
                                break;
                            case 6:
                                String pcodeForBusSearch = manage.inputString("Please enter passenger code to search buses: ");
//                                passTree.searchBusesByPcode(pcodeForBusSearch, bookingList, busTree); (Not done)
                                break;
                        }
                    } while (passengersChoice != 7);
                    break;

                case 3:
                    int bookingChoice;
                    do {
                        ui.showBookingMenu();
                        bookingChoice = ui.getChoiceBookingMenu();
                        switch (bookingChoice) {
                            case 1:
                                Booking inputBooking = manage.inputBooking(bookingList, busTree, passTree);
                                bookingList.bookBus(inputBooking, busTree, passTree);
                                bookingList.saveBookingToFile();
                                //busList.saveBusesToFile();
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
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }
}