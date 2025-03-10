/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package linkedlist;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import object.Bus;

/**
 *
 * @author FPT SHOP
 */
public class BusBST {

    private BusNode root;

    public BusBST() {
        root = null;
    }

    public void clear() {
        this.root = null;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public void visit(Node p) {
        System.out.print(p.info + " ");
    }
    String filePath = "Buses.txt";

    //1.1 Load Buslist from file
    public void loadBusesFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
            String readedFile;
            while ((readedFile = bReader.readLine()) != null) {
                String[] parts = readedFile.split(";");
                if (parts.length == 8) {
                    String bcode = parts[0].split(": ")[1].trim();
                    String bnum = parts[1].split(": ")[1].trim();
                    String dstation = parts[2].split(": ")[1].trim();
                    String astation = parts[3].split(": ")[1].trim();
                    double dtime = Double.parseDouble(parts[4].split(": ")[1].trim());
                    int seat = Integer.parseInt(parts[5].split(": ")[1].trim());
                    int booked = Integer.parseInt(parts[6].split(": ")[1].trim());
                    double atime = Double.parseDouble(parts[7].split(": ")[1].trim());

                    insert(new Bus(bcode, bnum, dstation, astation, dtime, seat, booked, atime));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    //1.2 Input and add to the end
    public void insert(Bus bus) {
        if (root == null) {
            root = new BusNode(bus);
            return;
        }

        BusNode newBus = null, p = root;

        while (p != null) {
            if (p.info.getBcode().equalsIgnoreCase(bus.getBcode())) {
                System.out.println("The bus code " + bus.getBcode() + " already exists, no insertion.");
                return;
            }
            newBus = p;
            if (bus.getBcode().compareToIgnoreCase(p.info.getBcode()) < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }

        if (bus.getBcode().compareToIgnoreCase(newBus.info.getBcode()) < 0) {
            newBus.left = new BusNode(bus);
        } else {
            newBus.right = new BusNode(bus);
        }
    }

    //1.3 Display data
    public void postOrder() {
        rotate(root);
    }

    private void rotate(BusNode p) {
        if (p == null) {
            return;
        }
        rotate(p.left);
        rotate(p.right);
        System.out.println(p.info);
    }

    //1.4 Save bus list to file
//    public void saveBusesToFile() {
//        try (BufferedWriter bwriter = new BufferedWriter(new FileWriter(filePath))) {
//            BusNode temp = head;
//            while (temp != null) {
//                bwriter.write(temp.info.toString());  // Write the booking data
//                bwriter.newLine();  // Move to the next line
//                temp = temp.next;  // Move to the next node
//            }
//        } catch (Exception e) {
//        }
//    }
    //1.5 Search by bcode
    public BusNode searchByCode(String code) {
        return search(root, code);
    }

    private BusNode search(BusNode from, String code) {
        if (from == null) {
            return null; // Không tìm thấy
        }
        if (from.info.getBcode().equalsIgnoreCase(code)) {
            return from; // Tìm thấy bus có `bcode` trùng khớp
        }
        if (code.compareToIgnoreCase(from.info.getBcode()) < 0) {
            return search(from.left, code); // Tìm ở cây con trái
        } else {
            return search(from.right, code); // Tìm ở cây con phải
        }
    }

    //1.6 Delete by bcode
    public void deleteByCodeCopying(String code, BookingBST bookingList) {
        // Kiểm tra nếu cây rỗng
        if (isEmpty()) {
            System.out.println("Bus BST is empty, no deletion.");
            return;
        }

        BusNode deleteNode = root;
        BusNode parentOfDeleteNode = null;

        // Tìm node cần xóa
        while (deleteNode != null) {
            if (deleteNode.info.getBcode().equalsIgnoreCase(code)) {
                break; // Tìm thấy node cần xóa
            }

            // Tiếp tục tìm kiếm theo quy tắc BST
            parentOfDeleteNode = deleteNode;
            if (code.compareToIgnoreCase(deleteNode.info.getBcode()) < 0) {
                deleteNode = deleteNode.left;
            } else {
                deleteNode = deleteNode.right;
            }
        }

        // Kiểm tra nếu không tìm thấy `bcode`
        if (deleteNode == null) {
            System.out.println("Bus with code " + code + " does not exist, no deletion.");
            return;
        }

        // Kiểm tra nếu bus có booking
        if (bookingList.isBusBooked(code)) {
            System.err.println("Error: Cannot delete bus " + code + " because it has been booked.");
            return;
        }

        // Trường hợp 1: Xóa node là lá (không có con)
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

        // Trường hợp 2: Node có 1 con (chỉ có con trái)
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

        // Trường hợp 3: Node có 1 con (chỉ có con phải)
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

        // Trường hợp 4: Node có 2 con → Tìm node lớn nhất bên trái (`maxLeft`)
        if (deleteNode.left != null && deleteNode.right != null) {
            BusNode parentReplaceNode = null;
            BusNode replaceNode = deleteNode.left;

            // Tìm node lớn nhất bên trái (`maxLeft`)
            while (replaceNode.right != null) {
                parentReplaceNode = replaceNode;
                replaceNode = replaceNode.right;
            }

            // Sao chép dữ liệu từ `replaceNode` vào `deleteNode`
            deleteNode.info = replaceNode.info;

            // Cập nhật liên kết để xóa `replaceNode`
            if (parentReplaceNode == null) {
                deleteNode.left = replaceNode.left;
            } else {
                parentReplaceNode.right = replaceNode.left;
            }
            replaceNode.left = null;
        }
    }

    //1.7 Delete by bcodeby merging
    public void deleteByCodeMerging(String code, BookingBST bookingList) {
        // 🟢 1️⃣ Kiểm tra nếu BST rỗng
        if (isEmpty()) {
            System.out.println("Bus BST is empty, no deletion.");
            return;
        }

        BusNode deleteNode = root;
        BusNode parentOfDeleteNode = null;

        // 🟢 2️⃣ Tìm node cần xóa
        while (deleteNode != null) {
            if (deleteNode.info.getBcode().equalsIgnoreCase(code)) {
                break; // Tìm thấy node cần xóa
            }
            parentOfDeleteNode = deleteNode;
            if (code.compareToIgnoreCase(deleteNode.info.getBcode()) < 0) {
                deleteNode = deleteNode.left;
            } else {
                deleteNode = deleteNode.right;
            }
        }

        // 🟢 3️⃣ Kiểm tra nếu không tìm thấy `bcode`
        if (deleteNode == null) {
            System.out.println("Bus with code " + code + " does not exist, no deletion.");
            return;
        }

        // 🟢 4️⃣ Kiểm tra nếu bus có booking
        if (bookingList.isBusBooked(code)) {
            System.err.println("Error: Cannot delete bus " + code + " because it has been booked.");
            return;
        }

        // 🟢 5️⃣ Trường hợp 1: Xóa node là lá (không có con)
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

        // 🟢 6️⃣ Trường hợp 2: Node có 1 con (chỉ có con trái)
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

        // Trường hợp 3: Node có 1 con (chỉ có con phải)
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

        // 🟢 8️⃣ Trường hợp 4: Node có 2 con → Merge cây con trái với cây con phải
        if (deleteNode.left != null && deleteNode.right != null) {
            BusNode rightOfDeleteNode = deleteNode.right;
            BusNode replaceNode = deleteNode.left;

            // Tìm node lớn nhất trong cây con trái (`maxLeft`)
            while (replaceNode.right != null) {
                replaceNode = replaceNode.right;
            }

            // Gán cây con phải (`rightOfDeleteNode`) vào node lớn nhất bên trái
            replaceNode.right = rightOfDeleteNode;
            deleteNode.right = null;

            // Cập nhật liên kết để xóa `deleteNode`
            if (parentOfDeleteNode == null) {
                root = deleteNode.left;
            } else {
                if (parentOfDeleteNode.left == deleteNode) {
                    parentOfDeleteNode.left = deleteNode.left;
                } else {
                    parentOfDeleteNode.right = deleteNode.left;
                }
            }
            deleteNode.left = null;
        }
    }

    //1.8 Simply balancing
    public void simplyBalancing() {
        ArrayList<Bus> busList = new ArrayList<>();

        // 🟢 1️⃣ Chuyển dữ liệu từ BST sang danh sách theo In-Order
        inOrderToArray(busList, this.root);

        // 🟢 2️⃣ Xóa cây BST hiện tại
        this.clear();

        // 🟢 3️⃣ Xây dựng lại cây BST theo thứ tự cân bằng
        balance(busList, 0, busList.size() - 1);
    }

    private void inOrderToArray(ArrayList<Bus> busList, BusNode node) {
        if (node == null) {
            return;
        }

        inOrderToArray(busList, node.left);  // Duyệt cây con trái
        busList.add(node.info);              // Thêm vào danh sách
        inOrderToArray(busList, node.right); // Duyệt cây con phải
    }

    private void balance(ArrayList<Bus> busList, int from, int to) {
        if (from > to) {
            return;
        }

        int middle = (from + to) / 2;
        Bus bus = busList.get(middle);

        this.insert(bus); // Chèn bus vào cây BST

        balance(busList, from, middle - 1); // Đệ quy chèn nửa trái
        balance(busList, middle + 1, to);   // Đệ quy chèn nửa phải
    }

    //1.9 Display databy breadth-first traversal
    public void breadthFirstTraversal() {
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }

        BusQueue queue = new BusQueue(); // Sử dụng `MyQueue` thay vì `Queue`
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            BusNode current = queue.dequeue(); // Lấy phần tử đầu tiên trong hàng đợi

            System.out.println(current.info); // In ra thông tin bus

            if (current.left != null) {
                queue.enqueue(current.left); // Thêm con trái vào hàng đợi
            }
            if (current.right != null) {
                queue.enqueue(current.right); // Thêm con phải vào hàng đợi
            }
        }
    }

    //1.10 Count the number of buses
    public int countBuses() {
        return countBuses(root);
    }

    private int countBuses(BusNode node) {
        if (node == null) {
            return 0; // Nếu node rỗng, không có bus nào
        }
        return 1 + countBuses(node.left) + countBuses(node.right);
    }

    //1.11 Search by name
    public BusNode searchByName(String name) {
        return searchByName(root, name);
    }

    private BusNode searchByName(BusNode node, String name) {
        if (node == null) {
            return null; // Không tìm thấy bus
        }
        // So sánh tên bus (bnum) không phân biệt hoa thường
        if (node.info.getBnum().equalsIgnoreCase(name)) {
            return node; // Tìm thấy bus
        }

        // Tiếp tục tìm kiếm ở cây con trái
        BusNode leftResult = searchByName(node.left, name);
        if (leftResult != null) {
            return leftResult; // Nếu tìm thấy trong cây trái, trả về ngay
        }
        // Tiếp tục tìm kiếm ở cây con phải
        return searchByName(node.right, name);
    }

    //1.12 Search bookings by bus code
//    public void searchBookedByBcode(String bcode, BookingBST bookingList, PassengerBST passengerList) {
//        // 🔹 1️⃣ Tìm bus theo `bcode`
//        BusNode busNode = searchByCode(bcode);
//        if (busNode == null) {
//            System.err.println("Bus with code " + bcode + " not found.");
//            return;
//        }
//
//        // 🔹 2️⃣ Hiển thị thông tin bus
//        System.out.println("\n===== Bus Details =====");
//        System.out.println(busNode.info);
//
//        // 🔹 3️⃣ Liệt kê hành khách đã đặt vé
//        System.out.println("\n===== Passengers Who Booked This Bus =====");
//        boolean foundPassenger = false;
//        BookingBST.BookingNode bookingNode = bookingList.getRoot();
//
//        while (bookingNode != null) {
//            if (bookingNode.info.getBcode().equalsIgnoreCase(bcode)) {
//                // 🔹 Tìm hành khách theo `pcode`
//                PassengerBST.PassengerNode passengerNode = passengerList.searchByPcode(bookingNode.info.getPcode());
//                if (passengerNode != null) {
//                    System.out.println(passengerNode.info);
//                    foundPassenger = true;
//                }
//            }
//            bookingNode = bookingNode.next; // Duyệt tiếp danh sách booking
//        }
//
//        if (!foundPassenger) {
//            System.out.println("No passengers have booked this bus.");
//        }
//    }

    // TESTING
    public void generateTestData() {
        String[] departingStations = {"City A", "City C", "City E", "City G", "City I"};
        String[] arrivingStations = {"City B", "City D", "City F", "City H", "City J"};
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            String bcode = "B" + (random.nextInt(900) + 100);
            String bnum = "Bus-" + (random.nextInt(900) + 100);
            String dstation = departingStations[i - 1];
            String astation = arrivingStations[i - 1];
            double dtime = 6.0 + random.nextInt(12);
            int seat = 40 + random.nextInt(20);
            int booked = random.nextInt(seat);
            double atime = dtime + 2 + random.nextDouble() * 3;

            Bus bus = new Bus(bcode, bnum, dstation, astation, dtime, seat, booked, atime);
            insert(bus);
        }
        System.out.println("Generated 5 random buses.");
    }

}
