package menu;

import control.*;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {

        UI ui = new UI();

        while (true) {
            int menuChoice;
            ui.showMenu();
            menuChoice = ui.getChoiceMenu();
            switch (menuChoice) {
                case 1:
                    ui.processBusMenu();
                    break;

                case 2:
                    ui.processPassMenu();
                    break;

                case 3:
                    ui.processBookingMenu();
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }
}
