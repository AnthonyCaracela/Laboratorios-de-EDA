// -------------------------------------------------------------------------
// Punto 6: Lista doblemente enlazada genérica con nodos propios y menú
// -------------------------------------------------------------------------
import java.util.Scanner;

public class Punto6DoublyLinkedListMenu<E> {
    static class Node<E> {
        E data;
        Node<E> prev, next;
        Node(E data) {
            this.data = data;
            this.prev = this.next = null;
        }
    }

    private Node<E> head, tail;

    // 1) Inserta al final
    public void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // 2) Inserta al inicio
    public void addFirst(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // 3) Elimina la primera ocurrencia por clave (equals)
    public boolean deleteByKey(E key) {
        if (head == null) return false;
        if (head.data.equals(key)) {
            // caso lista de un solo nodo o eliminar cabeza
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return true;
        }
        Node<E> curr = head.next;
        while (curr != null && !curr.data.equals(key)) {
            curr = curr.next;
        }
        if (curr != null) {
            if (curr == tail) {  
                tail = tail.prev;
                tail.next = null;
            } else {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
            }
            return true;
        }
        return false;
    }

    // 4) Elimina en posición idx (0-based)
    public boolean deleteAtPosition(int idx) {
        if (idx < 0 || head == null) return false;
        if (idx == 0) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return true;
        }
        Node<E> curr = head;
        int i = 0;
        while (curr != null && i < idx) {
            curr = curr.next;
            i++;
        }
        if (curr == null) return false;
        if (curr == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
        return true;
    }

    // 5) Retorna el tamaño
    public int size() {
        int count = 0;
        Node<E> curr = head;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    // 6) Elimina y retorna el primero
    public E removeFirst() {
        if (head == null) return null;
        E val = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        return val;
    }

    // 7) Elimina y retorna el último
    public E removeLast() {
        if (head == null) return null;
        E val = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        return val;
    }

    // 8) Recorre de adelante hacia atrás
    public void printForward() {
        System.out.print("Forward: ");
        Node<E> curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    // 9) Recorre de atrás hacia adelante
    public void printBackward() {
        System.out.print("Backward: ");
        Node<E> curr = tail;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.prev;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Punto6DoublyLinkedListMenu<Integer> dll = new Punto6DoublyLinkedListMenu<>();
        Scanner sc = new Scanner(System.in);

        // Inicializar con enteros del 1 al 10
        for (int i = 1; i <= 10; i++) {
            dll.addLast(i);
        }

        int op;
        do {
            System.out.println("\n1.AddFirst  2.AddLast  3.DeleteByKey  4.DeleteAtPosition  5.RemoveFirst");
            System.out.println("6.RemoveLast  7.Size  8.PrintForward  9.PrintBackward  0.Exit");
            System.out.print("Select option: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Value? ");
                    dll.addFirst(sc.nextInt());
                    break;
                case 2:
                    System.out.print("Value? ");
                    dll.addLast(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Key? ");
                    boolean removed = dll.deleteByKey(sc.nextInt());
                    System.out.println("Removed: " + removed);
                    break;
                case 4:
                    System.out.print("Index? ");
                    boolean removedAt = dll.deleteAtPosition(sc.nextInt());
                    System.out.println("Removed at position: " + removedAt);
                    break;
                case 5:
                    Integer first = dll.removeFirst();
                    System.out.println("Removed first: " + first);
                    break;
                case 6:
                    Integer last = dll.removeLast();
                    System.out.println("Removed last: " + last);
                    break;
                case 7:
                    System.out.println("Size: " + dll.size());
                    break;
                case 8:
                    dll.printForward();
                    break;
                case 9:
                    dll.printBackward();
                    break;
                case 0:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (op != 0);

        sc.close();
    }
}

