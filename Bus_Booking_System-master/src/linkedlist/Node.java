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
public class Node {

    public Bus info;
    public Node next;

    public Node() {
    }

     public Node(Bus bus) {
        this.info = bus;
        this.next = null; 
    }

    public Node(Bus bus, Node next) {
        this.info = bus;
        this.next = next;
    }
}
