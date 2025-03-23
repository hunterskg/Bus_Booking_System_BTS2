/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package linkedlist;

import object.Bus;

/**
 *
 * @author FPT SHOP
 */
public class BusNode {

    public Bus info;
    public BusNode left, right;

    public BusNode(Bus bus) {
        this.info = bus;
        this.left = this.right = null;
    }
}
