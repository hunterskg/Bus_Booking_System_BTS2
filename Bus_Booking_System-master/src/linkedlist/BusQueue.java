package linkedlist;

/**
 *
 * @author FPT SHOP
 */
public class BusQueue {

    protected Node head, tail;

    public class Node {
        public BusNode info;
        public Node next;

        public Node(BusNode x, Node p) {
            info = x;
            next = p;
        }

        public Node(BusNode x) {
            this(x, null);
        }
    }

    public BusQueue() {
        head = tail = null;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public BusNode front() throws Exception {
        if (isEmpty()) {
            throw new Exception("Queue is empty.");
        }
        return head.info;
    }

    public BusNode dequeue() throws Exception {
        if (isEmpty()) {
            throw new Exception("Queue is empty.");
        }
        BusNode x = head.info;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return x;
    }

    public void enqueue(BusNode x) {
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
