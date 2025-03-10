package linkedlist;

import java.util.LinkedList;

public class BusQueue {

    LinkedList<BusNode> queue; // 🔹 Đổi `Node` thành `BusNode`

    public BusQueue() {
        this.queue = new LinkedList<>();
    }

    public void clear() {
        this.queue.clear();
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void enqueue(BusNode x) { // 🔹 Thay `Node` bằng `BusNode`
        this.queue.addLast(x);
    }

    public BusNode dequeue() { // 🔹 Đổi kiểu trả về thành `BusNode`
        if (isEmpty()) {
            return null;
        }
        return this.queue.removeFirst();
    }

    public BusNode front() { // 🔹 Đổi kiểu trả về thành `BusNode`
        if (isEmpty()) {
            return null;
        }
        return this.queue.getFirst();
    }
}
