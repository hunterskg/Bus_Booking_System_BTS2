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

    private PassNode root;

    private final String filePath = "Passengers.txt";

    public PassNode getRoot() {
        return root;
    }

    // Node class for the linked list
    public class PassNode {

        public Passenger info;
        public PassNode right, left;

        public PassNode(Passenger passenger) {
            this.info = passenger;
            this.left = this.right = null;
        }
    }

    class PassQueue {

        private Node head, tail;

        class Node {
            public PassNode info;
            public Node next;

            public Node(PassNode x, Node p) {
                info = x;
                next = p;
            }

            public Node(PassNode x) {
                this(x, null);
            }
        }

        public PassQueue() {
            head = tail = null;
        }

        public boolean isEmpty() {
            return (head == null);
        }

        public PassNode front() throws Exception {
            if (isEmpty()) {
                throw new Exception("Queue is empty.");
            }
            return head.info;
        }

        public PassNode dequeue() throws Exception {
            if (isEmpty()) {
                throw new Exception("Queue is empty.");
            }
            PassNode x = head.info;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            return x;
        }

        public void enqueue(PassNode x) {
            if (isEmpty()) {
                head = tail = new Node(x);
            } else {
                tail.next = new Node(x);
                tail = tail.next;
            }
        }

        public void visit(Node p) {
            System.out.print(p.info + " ");
        }

        public void traverse() {
            Node p = head;
            while (p != null) {
                visit(p);
                p = p.next;
            }
            System.out.println();
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void visit(PassNode p) {
        System.out.println(p.info.toString());
    }

    // 2.1 Load data from file
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
                    insert(new Passenger(pcode, name, phone));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in file: " + e.getMessage());
        }
    }

    // 2.2 Input & add to the end
    public void insert(Passenger pass) {
        if (root == null) {
            root = new PassNode(pass);
            return;
        }

        PassNode newPass = null, p = root;

        while (p != null) {

            if (p.info.getPcode().equalsIgnoreCase(pass.getPcode())) {
                System.out.println("The passenger code " + pass.getPcode() + " already exists, no insertion.");
                return;
            }

            newPass = p;
            if (pass.getPcode().compareToIgnoreCase(p.info.getPcode()) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        if (pass.getPcode().compareToIgnoreCase(newPass.info.getPcode()) < 0) {
            newPass.left = new PassNode(pass);
        } else {
            newPass.right = new PassNode(pass);
        }

    }

    // 2.3 Display data
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(PassNode p) {

        if (p == null) {
            return;
        }

        inOrder(p.left);
        visit(p);
        inOrder(p.right);
    }

    private void inOrderSave(PassNode node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return;
        }
        inOrderSave(node.left, writer);
        writer.write(node.info.toString());
        writer.newLine();
        inOrderSave(node.right, writer);
    }

    // 2.4 Save passengers list to file
    public void savePassengersToFile() {
        try (BufferedWriter bwriter = new BufferedWriter(new FileWriter(filePath))) {
            inOrderSave(root, bwriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PassNode searchByPcode(PassNode from, String pcode) {
        if (from == null) {
            return null;
        }
        if (from.info.getPcode().equalsIgnoreCase(pcode)) {
            return from;
        }

        if (pcode.compareToIgnoreCase(from.info.getPcode()) < 0) {
            return searchByPcode(from.left, pcode);
        } else {
            return searchByPcode(from.right, pcode);
        }
    }

    // 2.5 Search by pcode
    public PassNode searchByPcode(String pcode) {
        PassNode foundPass = searchByPcode(root, pcode);
        visit(foundPass);
        return foundPass;
    }

    // 2.6 Delete by pcode
    public void deleteByCodeCopying(String pcode, BookingList bookingList) {
        // if tree is empty
        if (isEmpty()) {
            System.out.println("Bus BST is empty, no deletion.");
            return;
        }

        PassNode deleteNode = root;
        PassNode parentOfDeleteNode = null;

        // Find node need to delete
        while (deleteNode != null) {
            if (deleteNode.info.getPcode().equalsIgnoreCase(pcode)) {
                break; // Node found
            }

            // keep search
            parentOfDeleteNode = deleteNode;
            if (pcode.compareToIgnoreCase(deleteNode.info.getPcode()) < 0) {
                deleteNode = deleteNode.left;
            } else {
                deleteNode = deleteNode.right;
            }
        }

        // if pcode is not found
        if (deleteNode == null) {
            System.out.println("Passenger with code " + pcode + " does not exist, no deletion.");
            return;
        }

        // check if pcode is in booking
        if (bookingList.isBusBooked(pcode)) {
            System.err.println("Error: Cannot delete bus " + pcode + " because it has been booked.");
            return;
        }

        // Case 1: delete leaf Node
        if (deleteNode.left == null && deleteNode.right == null) {
            if (parentOfDeleteNode == null) {
                root = null; // Nếu xóa root
            } else if (parentOfDeleteNode.left == deleteNode) {
                parentOfDeleteNode.left = null;
            } else {
                parentOfDeleteNode.right = null;
            }
            return;
        }

        // Case 2: Node have only 1 left child
        if (deleteNode.left != null && deleteNode.right == null) {
            if (parentOfDeleteNode == null) {
                root = deleteNode.left; // Nếu xóa root
            } else if (parentOfDeleteNode.left == deleteNode) {
                parentOfDeleteNode.left = deleteNode.left;
            } else {
                parentOfDeleteNode.right = deleteNode.left;
            }
            deleteNode.left = null;
            return;
        }

        // Case 3: node onlu have 1 right child
        if (deleteNode.left == null && deleteNode.right != null) {
            if (parentOfDeleteNode == null) {
                root = deleteNode.right; // Nếu xóa root
            } else if (parentOfDeleteNode.left == deleteNode) {
                parentOfDeleteNode.left = deleteNode.right;
            } else {
                parentOfDeleteNode.right = deleteNode.right;
            }
            deleteNode.right = null;
            return;
        }

        // Case 4: Node have both children
        if (deleteNode.left != null && deleteNode.right != null) {
            PassNode parentReplaceNode = null;
            PassNode replaceNode = deleteNode.left;

            // Find biggest node on the left
            while (replaceNode.right != null) {
                parentReplaceNode = replaceNode;
                replaceNode = replaceNode.right;
            }

            // copy info from replace node to delete node
            deleteNode.info = replaceNode.info;

            // update linked
            if (parentReplaceNode == null) {
                deleteNode.left = replaceNode.left;
            } else {
                parentReplaceNode.right = replaceNode.left;
            }
            replaceNode.left = null;
        }
    }

    // 2.7 Search by name
    public void searchByName(String pname) {
        PassNode passSearch = searchByName(root, pname);
        visit(passSearch);
    }

    private PassNode searchByName(PassNode node, String pname) {
        if (node == null) {
            return null; // Pass not found
        }
        // compare pname
        if (node.info.getName().equalsIgnoreCase(pname)) {
            return node; // bus found
        }

        // keep find in left child
        PassNode leftResult = searchByName(node.left, pname);
        if (leftResult != null) {
            return leftResult; // return node if found in the left
        }
        // keep find in right child
        return searchByName(node.right, pname);
    }

    // 2.8 Search buses by pcode (Not Done)
    

    // For input Pass
    public boolean searchByPhone(String phone) {
        if (searchByPhone(root, phone) != null) {
            return true;
        }
        return false;
    }

    private PassNode searchByPhone(PassNode from, String phone) {
        if (from == null) {
            return null;
        }
        if (from.info.getPcode().equalsIgnoreCase(phone)) {
            return from;
        }

        if (phone.compareToIgnoreCase(from.info.getPcode()) < 0) {
            return searchByPcode(from.left, phone);
        } else {
            return searchByPcode(from.right, phone);
        }
    }

    public PassNode searchByPcodeForInput(String pcode) {
        PassNode foundPass = searchByPcode(root, pcode);
        return foundPass;
    }

    // Test data
    public void generateTestData() {
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            String pcode = "P" + (random.nextInt(900) + 100);
            String name = "Passenger_" + i;
            String phone = "09" + (random.nextInt(90000000) + 10000000);

            Passenger passenger = new Passenger(pcode, name, phone);
            insert(passenger);
        }
        System.out.println("Generated 10 random passengers.");
    }
}
