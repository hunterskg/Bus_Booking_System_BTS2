/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import linkedlist.BusBST;
import linkedlist.BusNode;

/**
 *
 * @author FPT SHOP
 */
public class Validation {

    public boolean checkString(String input) {
        return !input.isEmpty();
    }

    public boolean checkDouble(String input) {

        try {
            Double.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public boolean checkIntPosition(String input) {
        try {
            if (Integer.parseInt(input) >= 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean checkInt(String input) {
        try {
            if (Integer.parseInt(input) > 0) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public boolean checkBcode(String input, BusBST busList) {
        if (!checkString(input)) {
            System.out.println("Code cannot be empty");
            return false;
        }

        // 🔹 Dùng `searchByCode()` để kiểm tra `bcode` có tồn tại không
        if (busList.searchByCode(input) != null) {
            System.err.println("Error: Bus code already exists.");
            return false;
        }

        return true;
    }

}
