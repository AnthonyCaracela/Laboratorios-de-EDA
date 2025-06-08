// Nodo genérico
class Node<E> {
    private E data;
    private Node<E> next;
    public Node(E data) { this.data = data; this.next = null; }
    public E getData() { return data; }
    public Node<E> getNext() { return next; }
    public void setNext(Node<E> next) { this.next = next; }
}

// Queue genérico mínimo
class Queue<E> {
    private Node<E> head, tail;

    public Queue() { head = tail = null; }

    public void enqueue(E value) {
        Node<E> n = new Node<>(value);
        if (head == null) head = tail = n;
        else { tail.setNext(n); tail = n; }
    }

    public E dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue vacía");
        E v = head.getData();
        head = head.getNext();
        if (head == null) tail = null;
        return v;
    }

    public boolean isEmpty() { return head == null; }

    public void printQueue() {
        System.out.print("Queue [front→back]: ");
        for (Node<E> p = head; p != null; p = p.getNext()) {
            System.out.print(p.getData() + " ");
        }
        System.out.println();
    }
}

// Clase principal de prueba
public class ColaDiez {
    public static void main(String[] args) {
        Queue<Integer> q = new Queue<>();
        for (int i = 1; i <= 10; i++) q.enqueue(i);
        q.printQueue();
        System.out.print("Dequeue all: ");
        while (!q.isEmpty()) {
            System.out.print(q.dequeue() + " ");
        }
    }
}