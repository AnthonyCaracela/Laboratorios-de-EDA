import java.util.Scanner;

// Nodo genérico
class Node<E> {
    E data; Node<E> next;
    Node(E data) { this.data = data; this.next = null; }
}

// Queue genérico completo
class Queue<E> {
    private Node<E> head, tail;
    public Queue() { head = tail = null; }

    public boolean isEmpty() { return head == null; }
    public boolean isFull() { return false; } // dinámica
    public void enqueue(E value) {
        Node<E> n = new Node<>(value);
        if (head == null) head = tail = n;
        else { tail.next = n; tail = n; }
    }
    public E dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue vacía");
        E v = head.data;
        head = head.next;
        if (head == null) tail = null;
        return v;
    }
    public E front() {
        if (isEmpty()) throw new IllegalStateException("Queue vacía");
        return head.data;
    }
    public E back() {
        if (isEmpty()) throw new IllegalStateException("Queue vacía");
        return tail.data;
    }
    public void destroyQueue() { head = tail = null; }
    public void printQueue() {
        System.out.print("Queue [front→back]: ");
        for (Node<E> p = head; p != null; p = p.next) {
            System.out.print(p.data + " ");
        }
        System.out.println();
    }
}

// Clase principal con menú
public class PrincipalQueue {
    public static void main(String[] args) {
        Queue<Integer> q = new Queue<>();
        // Precarga 1–10
        for (int i = 1; i <= 10; i++) q.enqueue(i);
        Scanner sc = new Scanner(System.in);
        int opc, val;
        do {
            System.out.println("\n1) Enqueue  2) Dequeue  3) Front  4) Back  5) Print  6) isEmpty  7) isFull  8) Destroy  9) Salir");
            System.out.print("Opción: ");
            opc = sc.nextInt();
            switch (opc) {
                case 1:
                    System.out.print("Valor: "); val = sc.nextInt();
                    q.enqueue(val);
                    break;
                case 2:
                    System.out.println("dequeue() → " + q.dequeue());
                    break;
                case 3:
                    System.out.println("front() → " + q.front());
                    break;
                case 4:
                    System.out.println("back() → " + q.back());
                    break;
                case 5:
                    q.printQueue();
                    break;
                case 6:
                    System.out.println("isEmpty() → " + q.isEmpty());
                    break;
                case 7:
                    System.out.println("isFull() → " + q.isFull());
                    break;
                case 8:
                    q.destroyQueue();
                    System.out.println("Queue vaciada");
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opc != 9);
        sc.close();
    }
}